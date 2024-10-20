package com.ociapi.OCIAPI.controllers.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record SenderResponse(
        @JsonProperty("ElectronicId")
        int electronicId,
        @JsonProperty("DocumentNr")
        String documentNr,
        @JsonProperty("DocumentTypeId")
        int documentTypeId,
        @JsonProperty("DocumentTypeName")
        String documentTypeName,
        @JsonProperty("StatusId")
        int statusId,
        @JsonProperty("StatusName")
        String statusName,
        @JsonProperty("RecipientBusinessNumber")
        String recipientBusinessNumber,
        @JsonProperty("RecipientBusinessUnit")
        String recipientBusinessUnit,
        @JsonProperty("RecipientBusinessName")
        String recipientBusinessName,
        @JsonProperty("Created")
        LocalDateTime created,
        @JsonProperty("Sent")
        LocalDateTime sent,
        @JsonProperty("Modified")
        LocalDateTime modified,
        @JsonProperty("Delivered")
        LocalDateTime delivered
) {
}
