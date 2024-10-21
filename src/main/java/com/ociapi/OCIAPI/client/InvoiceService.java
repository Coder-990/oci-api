package com.ociapi.OCIAPI.client;

import com.ociapi.OCIAPI.repositories.model.Sender;

public interface InvoiceService {
    Sender createSenderFromJson(Sender sender);

    String process();
}
