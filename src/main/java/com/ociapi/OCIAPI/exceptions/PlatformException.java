package com.ociapi.OCIAPI.exceptions;

import lombok.Getter;

@Getter
public class PlatformException extends RuntimeException {
    private final String displayMessage;
    private final String detailedMessage;

    public PlatformException(String displayMessage, Exception detailedMessage) {
        super(displayMessage + " : " + detailedMessage);
        this.displayMessage = displayMessage;
        this.detailedMessage = String.valueOf(detailedMessage);
    }
}
