package com.ociapi.OCIAPI.controllers;

import com.ociapi.OCIAPI.client.InvoiceService;
import com.ociapi.OCIAPI.controllers.mappers.SenderMapperService;
import com.ociapi.OCIAPI.controllers.requests.AddSenderRequest;
import com.ociapi.OCIAPI.controllers.responses.SenderResponse;
import com.ociapi.OCIAPI.controllers.responses.SendersResponse;
import com.ociapi.OCIAPI.services.SenderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/apis/v2")
public class SenderController {

    private final SenderService senderService;
    private final SenderMapperService senderMapperService;
    private final InvoiceService invoiceService;

    @GetMapping("/senders")
    @ResponseStatus(HttpStatus.OK)
    public SendersResponse getAllSenders() {
        log.info("Fetched all senders...");
        return senderMapperService.toSendersResponse(senderService.getAll());
    }

    @GetMapping("/senders/{id}")
    @ResponseStatus(HttpStatus.OK)
    public SenderResponse fetchSenderById(@PathVariable Long id) {
        log.info("Fetching sender with id {}... ", id);
        var sender = senderService.getOneById(id);
        var senderResponse = senderMapperService.toSenderResponse(sender);
        log.info("Fetched sender for id {} with body {}", id, senderResponse);
        return senderResponse;
    }

    @PostMapping("/senders")
    @ResponseStatus(HttpStatus.CREATED)
    public SenderResponse saveSender(@RequestBody AddSenderRequest addSenderRequest) throws IOException {
        log.info("Saving addSenderRequest with body {}... ", addSenderRequest);
        var json = invoiceService.process();
        var createdSender = senderMapperService.toAddSender(addSenderRequest, json);
        var savedSender = senderService.create(createdSender);
        var sender = invoiceService.getRestClient(savedSender);
        var senderResponse = senderMapperService.toSenderResponse(sender);
        log.info("Saved sender with companyId {} with body {}", savedSender.getCompanyId(), senderResponse);
        return senderResponse;
    }

    @DeleteMapping("/senders/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSenderById(@PathVariable Long id) {
        senderService.deleteById(id);
    }
}
