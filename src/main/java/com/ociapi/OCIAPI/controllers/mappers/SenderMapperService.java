package com.ociapi.OCIAPI.controllers.mappers;

import com.ociapi.OCIAPI.controllers.requests.AddSenderRequest;
import com.ociapi.OCIAPI.controllers.responses.SenderResponse;
import com.ociapi.OCIAPI.controllers.responses.SendersResponse;
import com.ociapi.OCIAPI.repositories.model.Sender;

import java.util.List;

public interface SenderMapperService {
    Sender toAddSender(AddSenderRequest addSenderRequest);

    SenderResponse toSenderResponse(Sender sender);

    SendersResponse toSendersResponse(List<Sender> senders);
}
