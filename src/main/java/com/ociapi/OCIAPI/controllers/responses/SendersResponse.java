package com.ociapi.OCIAPI.controllers.responses;

import lombok.Builder;

import java.util.List;

@Builder
public record SendersResponse(
        List<SenderResponse> senders
) {
}
