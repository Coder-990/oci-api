package com.ociapi.OCIAPI.client.impl;

import com.ociapi.OCIAPI.client.InvoiceService;
import com.ociapi.OCIAPI.config.props.EInvoiceClientProps;
import com.ociapi.OCIAPI.exceptions.PlatformException;
import com.ociapi.OCIAPI.repositories.model.Sender;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {

    private static final Logger log = LoggerFactory.getLogger(InvoiceServiceImpl.class);
    private final EInvoiceClientProps eInvoiceClientProps;
    private final RestClient restClient;

    public void getSenderFromRestClient(Sender sender) {
        this.createSenderFromRestClient(sender);
    }

    private void createSenderFromRestClient(Sender sender) {
        try {
            Object senderResponse = restClient
                    .post()
                    .uri(eInvoiceClientProps.getSendEDocumentsUrl())
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(sender)
                    .retrieve()
                    .onStatus(HttpStatusCode::isError, ((request, response) -> {
                        throw new PlatformException("e-invoice api error", new Exception(
                                new String(response.getBody().readAllBytes(), StandardCharsets.UTF_8)));
                    }))
                    .toEntity(Object.class)
                    .getBody();
            log.info("Created sender: {}", senderResponse);
//            return senderResponse;
        } catch (Exception e) {
            throw new PlatformException("Failed to send Request sender from service: " + e.getMessage(), e);
        }
    }
}
