//package com.ociapi.OCIAPI.client.impl;
//
//import com.ociapi.OCIAPI.client.InvoiceService;
//import com.ociapi.OCIAPI.config.props.EInvoiceClientProps;
//import com.ociapi.OCIAPI.controllers.requests.AddSenderRequest;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.*;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestClient;
//import org.springframework.web.client.RestClientResponseException;
//
//import java.net.URI;
//import java.nio.charset.StandardCharsets;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.util.Objects;
//import java.util.stream.Stream;
//
//@Service
//@RequiredArgsConstructor
//public class InvoiceServiceImpl implements InvoiceService {
//
//    private final EInvoiceClientProps eInvoiceClientProps;
//    private final RestClient restClient;
//
//    public String sendInvoice(String xmlFilePath) throws Exception {
//        var xmlContent = Files.readString(Path.of(xmlFilePath));
//        var escapedXml = getEscapedXml(xmlContent);
//        var jsonPayload = "{ \"xmlPayload\": \"" + escapedXml + "\" }";
//
//        // 4. Postavljanje HTTP headera
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        // 5. Kreiranje zahtjeva
//        RequestEntity<String> request = RequestEntity
//                .post(new URI(eInvoiceClientProps.getSendEDocumentsUrl()))
//                .headers(headers)
//                .body(jsonPayload);
//
//        try {
//            var response = Stream.ofNullable(restClient.method(HttpMethod.POST)
//                    .uri(eInvoiceClientProps.getSendEDocumentsUrl())
//                    .headers(httpHeaders -> httpHeaders.addAll(headers))
//                    .body(Objects.requireNonNull(request.getBody()))
//                    .retrieve()
//                    .toEntity(AddSenderRequest.class));
//
//            // 7. Dobivanje ElectronicID-a iz odgovora
//            String responseBody = response.getBody();
//            return extractElectronicId(responseBody);
//        } catch (RestClientResponseException e) {
//            throw new RuntimeException("Error while sending invoice: " + e.getResponseBodyAsString(), e);
//        }
//    }
//
//    private static String getEscapedXml(String xmlContent) {
//        return xmlContent
//                .replace("<", "&lt;")
//                .replace(">", "&gt;")
//                .replace("&", "&amp;")
//                .replace("\"", "&quot;")
//                .replace("'", "&apos;");
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
//    public String queryOutbox(String electronicId) throws Exception {
//        // 8. Slanje zahtjeva za provjeru statusa dokumenta pomoću ElectronicID-a
//        String queryUrl = eInvoiceClientProps.getSendEDocumentsUrl() + "?electronicId=" + electronicId;
//        RequestEntity<Void> request = RequestEntity
//                .get(new URI(queryUrl))
//                .build();
//
//        try {
//            ResponseEntity<String> response = restClient.method(HttpMethod.GET)
//                    .uri(queryUrl)
//                    .retrieve()
//                    .toEntity(String.class);
//            return response.getBody();
//        } catch (RestClientResponseException e) {
//            throw new RuntimeException("Error while querying outbox: " + e.getResponseBodyAsString(), e);
//        }
//    }
//}
