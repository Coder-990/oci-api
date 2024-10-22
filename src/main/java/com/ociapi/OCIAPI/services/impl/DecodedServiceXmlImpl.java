package com.ociapi.OCIAPI.services.impl;

import com.ociapi.OCIAPI.controllers.responses.SenderResponse;
import com.ociapi.OCIAPI.services.DecodedServiceXml;
import lombok.RequiredArgsConstructor;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.time.LocalDateTime;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class DecodedServiceXmlImpl implements DecodedServiceXml {

    @Override
    public SenderResponse runDecodeProcessXml(String escapedXml) throws ParserConfigurationException, IOException, SAXException {
        String decodedXml = decodeEscapedXml(escapedXml);
        return parseXmlToSenderResponse(decodedXml);
    }

    private String decodeEscapedXml(String escapedXml) {
        String escapedXmlPayload = escapedXml.replace("{ \"xmlPayload\": \"", "").replace("\" }", "");
        return StringEscapeUtils.unescapeXml(escapedXmlPayload);
    }

    private SenderResponse parseXmlToSenderResponse(String xml)
            throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new InputSource(new StringReader(xml)));

        String documentNr = getNodeValue(document, "cbc:ID");
        String recipientBusinessNumber = getNodeValue(document, "cbc:EndpointID");
        String recipientBusinessName = getNodeValue(document, "cbc:RegistrationName");
        String createdDate = getNodeValue(document, "cbc:IssueDate");

        return SenderResponse.builder()
                .electronicId(generateElectronicId(documentNr))
                .documentNr(documentNr)
                .documentTypeId(381)
                .documentTypeName("CreditNote")
                .statusId(1)
                .statusName("Sent")
                .recipientBusinessNumber(recipientBusinessNumber)
                .recipientBusinessUnit("Unit1")
                .recipientBusinessName(recipientBusinessName)
                .created(LocalDateTime.now())
                .sent(LocalDateTime.now())
                .modified(LocalDateTime.now())
                .delivered(LocalDateTime.now())
                .build();
    }

    private String getNodeValue(Document document, String tagName) {
        NodeList nodeList = document.getElementsByTagName(tagName);
        if (nodeList.getLength() > 0) {
            return nodeList.item(0).getTextContent();
        }
        return null;
    }

    private int generateElectronicId(String documentNr) {
        return documentNr != null ? documentNr.hashCode() : 0;
    }
}