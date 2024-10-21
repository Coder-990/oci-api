package com.ociapi.OCIAPI.controllers.mappers.impl;

import com.ociapi.OCIAPI.client.InvoiceService;
import com.ociapi.OCIAPI.controllers.mappers.SenderMapperService;
import com.ociapi.OCIAPI.controllers.requests.AddSenderRequest;
import com.ociapi.OCIAPI.controllers.responses.SenderResponse;
import com.ociapi.OCIAPI.controllers.responses.SendersResponse;
import com.ociapi.OCIAPI.repositories.model.Sender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SenderMapperServiceImpl implements SenderMapperService {

    private final InvoiceService invoiceService;

    @Override
    public Sender toAddSender(AddSenderRequest addSenderRequest, String json) throws IOException {
//        var file = invoiceService.prepareJsonWithEscapedXmlAndPdf();
//        var postFileData = invoiceService.getXmlFromService(addSenderRequest);
        return Sender.builder()
                .username(addSenderRequest.username())
                .password(addSenderRequest.password())
                .companyId(addSenderRequest.companyId())
                .companyBu(addSenderRequest.companyBu())
                .softwareId(addSenderRequest.softwareId())
                .file(json)
                .build();
    }

    @Override
    public SenderResponse toSenderResponse(Sender sender) {
        return SenderResponse.builder()
//                .electronicId(sender.getElectronicId())
//                .documentNr("123")
//                .documentTypeId(123)
//                .documentTypeName("DocTypeName")
//                .statusId(321)
//                .statusName("StatusName")
//                .recipientBusinessName("RecipientBusinessName")
//                .recipientBusinessUnit("RecipientBusinessUnit")
//                .recipientBusinessName("RecipientBusinessName")
//                .created(LocalDateTime.now().minusHours(1))
//                .sent(LocalDateTime.now())
//                .modified(LocalDateTime.now().plusYears(1))
//                .delivered(LocalDateTime.now().plusMinutes(30))
                .build();
    }

    @Override
    public SendersResponse toSendersResponse(List<Sender> senders) {
        var sendersResponse = SendersResponse.builder()
                .senders(senders.stream()
                        .map(this::toSenderResponse).toList())
                .build()
                .senders();
        return SendersResponse.builder()
                .senders(sendersResponse)
                .build();
    }
}
