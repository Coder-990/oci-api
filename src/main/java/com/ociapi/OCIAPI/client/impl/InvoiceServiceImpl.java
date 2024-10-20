package com.ociapi.OCIAPI.client.impl;

import com.ociapi.OCIAPI.client.InvoiceService;
import com.ociapi.OCIAPI.config.props.EInvoiceClientProps;
import com.ociapi.OCIAPI.controllers.requests.AddSenderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {

    private final EInvoiceClientProps eInvoiceClientProps;
    private final RestClient restClient;

//    public String sendInvoice(String xmlFilePath) throws Exception {
//        var xmlContent = Files.readString(Path.of(xmlFilePath));
//        var escapedXml = xmlContent.replace("<", "&lt;")
//                .replace(">", "&gt;")
//                .replace("&", "&amp;")
//                .replace("\"", "&quot;")
//                .replace("'", "&apos;");
//        var jsonPayload = "{ \"xmlPayload\": \"" + escapedXml + "\" }";
//        var headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        HttpEntity<String> request = new HttpEntity<>(jsonPayload, headers);
//        ResponseEntity<AddSenderRequest> response = restClient
//                .get()
//                .uri(eInvoiceClientProps.getSendEDocumentsUrl())
//        .accept(MediaType.APPLICATION_JSON)
//        .retrieve()
//                .toEntity(AddSenderRequest[].class)
//                .getBody();
//        // 6. Dobivanje ElectronicID-a iz odgovora
//        String responseBody = response.getBody();
//        // Pretpostavimo da je odgovor JSON formatiran i da sadrži "ElectronicID": 394167
//        // (u stvarnosti bi se ovdje koristila JSON biblioteka za parsiranje)
//        String electronicId = extractElectronicId(responseBody);
//
//        return electronicId;
//    }
//
//    private String extractElectronicId(String responseBody) {
//        // Ovdje je pojednostavljeno izdvajanje ElectronicID-a iz JSON odgovora
//        // Preporučuje se korištenje biblioteke kao što je Jackson ili Gson za parsiranje JSON-a
//        String key = "\"ElectronicID\": ";
//        int startIndex = responseBody.indexOf(key) + key.length();
//        int endIndex = responseBody.indexOf(",", startIndex);
//        return responseBody.substring(startIndex, endIndex).trim();
//    }
//
//    public String queryOutbox(String electronicId) {
//        // 7. Slanje zahtjeva za provjeru statusa dokumenta pomoću ElectronicID-a
//        String queryUrl = "https://legacy-mer.moj-eracun.hr/en/Manual/Stable/Api/query-outbox?electronicId=" + electronicId;
//        ResponseEntity<String> response = restTemplate.exchange(queryUrl, HttpMethod.GET, null, String.class);
//        return response.getBody();
//    }

}
