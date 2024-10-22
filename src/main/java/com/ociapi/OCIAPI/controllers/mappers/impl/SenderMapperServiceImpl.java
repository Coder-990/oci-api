package com.ociapi.OCIAPI.controllers.mappers.impl;

import com.ociapi.OCIAPI.controllers.mappers.SenderMapperService;
import com.ociapi.OCIAPI.controllers.requests.AddSenderRequest;
import com.ociapi.OCIAPI.controllers.responses.SenderResponse;
import com.ociapi.OCIAPI.controllers.responses.SendersResponse;
import com.ociapi.OCIAPI.repositories.model.Sender;
import com.ociapi.OCIAPI.services.DecodedServiceXml;
import com.ociapi.OCIAPI.services.EncodedServiceXml;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SenderMapperServiceImpl implements SenderMapperService {

    private final EncodedServiceXml encodedServiceXml;
    private final DecodedServiceXml decodedServiceXml;

    @Override
    public Sender toAddSender(AddSenderRequest addSenderRequest) {
        return Sender.builder()
                .username(addSenderRequest.username())
                .password(addSenderRequest.password())
                .companyId(addSenderRequest.companyId())
                .companyBu(addSenderRequest.companyBu())
                .softwareId(addSenderRequest.softwareId())
                .file(addSenderRequest.file())
                .build();
    }

    @Override
    public SenderResponse toSenderResponse(Sender sender) throws ParserConfigurationException, IOException, SAXException {
        String encodeProcessXml = encodedServiceXml.runEncodeProcessXml();
        var senderResponse = decodedServiceXml.runDecodeProcessXml(encodeProcessXml);
        return SenderResponse.builder()
                .electronicId(senderResponse.electronicId())
                .documentNr(senderResponse.documentNr())
                .documentTypeId(senderResponse.documentTypeId())
                .documentTypeName(senderResponse.documentTypeName())
                .statusId(senderResponse.statusId())
                .statusName(senderResponse.statusName())
                .recipientBusinessName(senderResponse.recipientBusinessName())
                .recipientBusinessUnit(senderResponse.recipientBusinessUnit())
                .recipientBusinessName(senderResponse.recipientBusinessName())
                .created(senderResponse.created())
                .sent(senderResponse.sent())
                .modified(senderResponse.modified())
                .delivered(senderResponse.delivered())
                .build();
    }


    @Override
    public SendersResponse toSendersResponse(List<Sender> senders) {
        var sendersResponse = SendersResponse.builder()
                .senders(senders.stream()
                        .map(sender -> {
                            try {
                                return toSenderResponse(sender);
                            } catch (ParserConfigurationException | IOException | SAXException e) {
                                throw new RuntimeException(e);
                            }
                        }).toList())
                .build()
                .senders();
        return SendersResponse.builder()
                .senders(sendersResponse)
                .build();
    }
}
