package com.ociapi.OCIAPI.controllers.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AddHighImportanceRequest(
        @JsonProperty("Username")
        int username,
        @JsonProperty("Password")
        String password,
        @JsonProperty("CompanyId")
        String companyId,
        @JsonProperty("CompanyBu")
        String companyBu,
        @JsonProperty("HighImportanceReceive")
        boolean highImportanceReceive,
        @JsonProperty("SoftwareId")
        String softwareId,
        @JsonProperty("File")
        String file
) {
}

