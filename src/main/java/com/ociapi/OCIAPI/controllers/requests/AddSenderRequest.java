package com.ociapi.OCIAPI.controllers.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record AddSenderRequest(
        @JsonProperty("Username")
        int username,
        @JsonProperty("Password")
        String password,
        @JsonProperty("CompanyId")
        String companyId,
        @JsonProperty("CompanyBu")
        String companyBu,
        @JsonProperty("SoftwareId")
        String softwareId,
        @JsonProperty("File")
        String file
) {
}
