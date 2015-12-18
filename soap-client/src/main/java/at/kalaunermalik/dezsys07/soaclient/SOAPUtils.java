package at.kalaunermalik.dezsys07.soaclient;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.soap.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class provides a SearchRequest and an Envelope to Entry mapping.
 */
public class SOAPUtils {
    private static final Logger logger = LogManager.getLogger(SOAPUtils.class.getName());

    /**
     * Creates a SearchRequest, using the given String as searchstring with the defined envelope.
     *
     * @param search The searchstring
     * @return the SOAPMessage with the SearchRequest with the given searchstring
     */
    public static SOAPMessage createSearchRequest(String search) {
        SOAPMessage message = null;
        try {
            message = MessageFactory.newInstance().createMessage();
        } catch (SOAPException e) {
            logger.log(Level.ERROR, "Could not create message");

        }
        SOAPPart soapPart = message.getSOAPPart();

        String prefix = "at";
        String location = "at.kalaunermalik.dezsys07";
        SOAPEnvelope soapEnvelope = null;
        try {
            soapEnvelope = soapPart.getEnvelope();
        } catch (SOAPException e) {
            logger.log(Level.ERROR, "Could not get a SOAP envelope");
        }
        try {
            soapEnvelope.addNamespaceDeclaration(prefix, location);
        } catch (SOAPException e) {
            logger.log(Level.ERROR, "Could not add a Namespacedeclaration");
        }

        SOAPBody soapBody = null;
        try {
            soapBody = soapEnvelope.getBody();
        } catch (SOAPException e) {
            logger.log(Level.ERROR, "Could not get envelope body");
        }
        SOAPElement soapElement = null;
        try {
            soapElement = soapBody.addChildElement("getDataRequest", prefix);
        } catch (SOAPException e) {
            logger.log(Level.ERROR, "Element in soap body does not exist");
        }
        SOAPElement soapElement1 = null;
        try {
            soapElement1 = soapElement.addChildElement("title", prefix);
        } catch (SOAPException e) {
            logger.log(Level.ERROR, "Element in Child does not exist");
        }
        try {
            soapElement1.addTextNode(search);
        } catch (SOAPException e) {
            logger.log(Level.ERROR, "Could not add a text node to child's element");
        }

        MimeHeaders headers = message.getMimeHeaders();
        headers.addHeader("SOAPAction", location + "getDataRequest");
        try {
            message.saveChanges();
        } catch (SOAPException e) {
            logger.log(Level.ERROR, "Could not save messages changes");
        }
        return message;
    }

    /**
     * Creates an Entry from the given envelope, only works with our implementation.
     *
     * @param response the responsemessage
     * @return the envelope as @see{Entry}
     */
    public static List<Entry> createEntry(SOAPMessage response) {
        SOAPBody responseBody = null;
        List<Entry> retEntries = new ArrayList<>(1);
        try {
            responseBody = response.getSOAPBody();
        } catch (Exception e) {
            logger.log(Level.INFO, "Could not get SOAPBody");
            return retEntries;
        }
        Node dataResponseElement = responseBody.getFirstChild();
        NodeList nodeList = dataResponseElement.getChildNodes();

        Entry entry = new Entry();
        for (int i = 0; i < nodeList.getLength(); i++) {
            NodeList entries = nodeList.item(i).getChildNodes();
            for (int f = 0; f < entries.getLength(); f++) {
                switch (entries.item(f).getLocalName()) {
                    case "content":
                        entry.setContent(entries.item(f).getTextContent());
                        break;
                    case "id":
                        long id = Long.parseLong(entries.item(f).getTextContent());
                        entry.setId(id);
                        break;
                    case "title":
                        entry.setTitle(entries.item(f).getTextContent());
                }
            }
            retEntries.add(entry);
        }

        return retEntries;
    }
}
