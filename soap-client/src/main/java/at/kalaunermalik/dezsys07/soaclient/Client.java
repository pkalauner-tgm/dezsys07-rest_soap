package at.kalaunermalik.dezsys07.soaclient;


import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.soap.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by patrick on 15.12.2015.
 */
public class Client {

    private static final Logger logger = LogManager.getLogger(Client.class.getName());

    public static void main(String[] args) {

        Cli cliparser = new Cli(args);
        String url = cliparser.parse();

        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);

        url += "/ws";

        SOAPConnectionFactory soapConnectionFactory = null;
        SOAPConnection soapConnection = null;
        try {
            soapConnectionFactory = SOAPConnectionFactory.newInstance();
            soapConnection = soapConnectionFactory.createConnection();
        } catch (SOAPException e) {
            logger.log(Level.ERROR, "Could not establish a connection to the server.");
        }

        boolean run = true;
        String input = null;
        System.out.println("Enter the title of entry you want to see or \\q to leave:");
        while (run) {

            try {
                input = br.readLine();
            } catch (IOException e) {
                logger.log(Level.ERROR, "Could not read the given input");
            }
            if (input.equals("\\q")) {
                run = false;
                break;
            }

            SOAPMessage message = SOAPUtils.createSearchRequest(input);

            SOAPMessage soapResponse = null;
            try {
                soapResponse = soapConnection.call(message, url);
            } catch (SOAPException e) {
                logger.log(Level.ERROR, "Could not receive SOAP response");
            }
            List<Entry> responseEntry = SOAPUtils.createEntry(soapResponse);
            System.out.println("-------------------------------------");
            for (Entry e : responseEntry) {
                System.out.println(e.toFormattedString());
                System.out.println("-------------------------------------");
            }
        }
        try {
            soapConnection.close();
        } catch (SOAPException e) {
            logger.log(Level.ERROR, "Could not close connection");
        }


    }

}
