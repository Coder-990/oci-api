package com.ociapi.OCIAPI.services;

import com.ociapi.OCIAPI.controllers.responses.SenderResponse;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public interface DecodedServiceXml {
    SenderResponse runDecodeProcessXml(String escapedXml) throws ParserConfigurationException, IOException, SAXException;
}
