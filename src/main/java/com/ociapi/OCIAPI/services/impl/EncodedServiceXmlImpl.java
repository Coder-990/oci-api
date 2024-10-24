package com.ociapi.OCIAPI.services.impl;

import com.ociapi.OCIAPI.services.EncodedServiceXml;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class EncodedServiceXmlImpl implements EncodedServiceXml {

    @Override
    public String runEncodeProcessXml() {
        String jsonPayload;
        String escapedXml = "";
        try {
            escapedXml = loadXml();
        } catch (IOException e) {
            e.printStackTrace();
        }
        jsonPayload = createJsonPayload(escapedXml);
//        System.out.println("Generated JSON Payload: " + jsonPayload);
        return jsonPayload;
    }

    private String loadXml() throws IOException {
        String xmlFilePath = "dat/EUnorm_avansni_racun_TIP_2.xml";
        String xmlContent = Files.readString(Path.of(xmlFilePath));
        return extractPdfFromXml(xmlContent);
    }

    private String extractPdfFromXml(String xmlContent) throws IOException {
        String base64PdfContent = getPdfContent(xmlContent);
        byte[] pdfBytes = Base64.getDecoder().decode(base64PdfContent);
        String pdfOutputPath = "output/file.pdf";
        try (FileOutputStream fos = new FileOutputStream(pdfOutputPath)) {
            fos.write(pdfBytes);
            System.out.println("PDF successfully extracted to: " + pdfOutputPath);
        }

        return prepareEscapedXmlWithEncodedPdf(xmlContent);
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

    private String prepareEscapedXmlWithEncodedPdf(String xmlContent) throws IOException {
        String encodedPdf = encodePdfToBase64();

        return xmlContent.replace("<cbc:EmbeddedDocumentBinaryObject></cbc:EmbeddedDocumentBinaryObject>",
                "<cbc:EmbeddedDocumentBinaryObject>" + encodedPdf + "</cbc:EmbeddedDocumentBinaryObject>");
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
}
