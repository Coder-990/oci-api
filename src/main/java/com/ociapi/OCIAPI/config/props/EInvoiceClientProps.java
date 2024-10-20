package com.ociapi.OCIAPI.config.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "oci-api.clients.e-invoice-api")
public class EInvoiceClientProps {

    private String sendEDocumentsUrl;
}
