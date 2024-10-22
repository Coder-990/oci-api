package com.ociapi.OCIAPI.client.impl;

import com.ociapi.OCIAPI.client.InvoiceService;
import com.ociapi.OCIAPI.config.props.EInvoiceClientProps;
import com.ociapi.OCIAPI.exceptions.PlatformException;
import com.ociapi.OCIAPI.repositories.model.Sender;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {

    private final EInvoiceClientProps eInvoiceClientProps;
    private final RestClient restClient;

    public Sender createSenderFromRestClient(Sender sender) {
        try {
            return restClient
                    .post()
                    .uri(eInvoiceClientProps.getSendEDocumentsUrl())
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(sender)
                    .retrieve()
                    .toEntity(Sender.class)
                    .getBody();
        } catch (Exception e) {
            throw new PlatformException("Failed to send JSON from service: " + e.getMessage(), e);
        }
    }

    @Override
    public Sender getRestClient(Sender sender) {
        return this.createSenderFromRestClient(sender);
    }

    public String loadXml() throws IOException {
        String xmlFilePath = "dat/EUnorm_CreditNote.xml";
        String xmlContent = Files.readString(Path.of(xmlFilePath));
        return extractPdfFromXml(xmlContent);
    }

    public String extractPdfFromXml(String xmlContent) throws IOException {
        String base64PdfContent = getPdfContent(xmlContent);
        byte[] pdfBytes = Base64.getDecoder().decode(base64PdfContent);
        String pdfOutputPath = "output/file.pdf";
        try (FileOutputStream fos = new FileOutputStream(pdfOutputPath)) {
            fos.write(pdfBytes);
            System.out.println("PDF successfully extracted to: " + pdfOutputPath);
        }

        return prepareEscapedXmlWithEncodedPdf(xmlContent); // Poziva iduću metodu nakon što izvuče PDF
    }

    private String getPdfContent(String xmlContent) {
        String pdfTagStart = "<cbc:EmbeddedDocumentBinaryObject mimeCode=\"application/pdf\" filename=\"993-1-1.pdf\">";

        String pdfTagEnd = "</cbc:EmbeddedDocumentBinaryObject>";

        int startIndex = xmlContent.indexOf(pdfTagStart);
        int endIndex = xmlContent.indexOf(pdfTagEnd);

        if (startIndex == -1 || endIndex == -1) {
            throw new IllegalArgumentException("PDF content not found in XML.");
        }
        return xmlContent.substring(startIndex + pdfTagStart.length(), endIndex).trim();
    }

    public String prepareEscapedXmlWithEncodedPdf(String xmlContent) throws IOException {
        String encodedPdf = encodePdfToBase64();
        String xmlWithPdf = xmlContent.replace("<cbc:EmbeddedDocumentBinaryObject></cbc:EmbeddedDocumentBinaryObject>",
                "<cbc:EmbeddedDocumentBinaryObject>" + encodedPdf + "</cbc:EmbeddedDocumentBinaryObject>");

        return escapeXml(xmlWithPdf); // Poziva iduću metodu za escapiranje XML-a
    }

    private String encodePdfToBase64() throws IOException {
        File file = new File("output/file.pdf");
        byte[] pdfBytes = Files.readAllBytes(file.toPath());
        return Base64.getEncoder().encodeToString(pdfBytes);
    }

    private String escapeXml(String xmlContent) {
        return xmlContent.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&apos;");
    }

    private String createJsonPayload(String escapedXml) {
        return "{ \"xmlPayload\": \"" + escapedXml + "\" }";
    }

    @Override
    public String process() {
        String jsonPayload;
        String escapedXml = "";
        try {
            escapedXml = loadXml();
        } catch (IOException e) {
            e.printStackTrace();
        }
        jsonPayload = createJsonPayload(escapedXml);
        System.out.println("Generated JSON Payload: " + jsonPayload);
        return jsonPayload;
    }
}
