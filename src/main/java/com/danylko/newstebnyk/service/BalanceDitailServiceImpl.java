package com.danylko.newstebnyk.service;

import com.danylko.newstebnyk.config.PersonInfoProperties;
import com.danylko.newstebnyk.entity.BalanceDitail;

import com.danylko.newstebnyk.parserrxml.ParserXml;
import com.danylko.newstebnyk.util.SignatureGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class BalanceDitailServiceImpl implements BalanceDitailService {

    Logger logger = LoggerFactory.getLogger(BalanceDitailServiceImpl.class);

    private PersonInfoProperties personInfoProperties;
    private ParserXml parserXml;

    public BalanceDitailServiceImpl(PersonInfoProperties personInfoProperties, ParserXml parserXml) {
        this.personInfoProperties = personInfoProperties;
        this.parserXml = parserXml;
    }

    private static String signature;

    @Override
    public BalanceDitail getBalanceDitail() {
        BalanceDitail balanceDitail = null;
        String xml = getXml();
        try {
            HttpURLConnection connection = getConnection();
            sendXml(connection, xml);
            balanceDitail = parserXml.parse(connection);
            connection.getResponseCode();
            connection.disconnect();
        } catch(IOException e) {
            throw new RuntimeException("Can't request and unmarshal object", e);
        }
        logger.info(balanceDitail.toString());
        return balanceDitail;
    }

    private String getXml() {
        String dataTag =
                "<oper>cmt</oper>\n" +
                        "        <wait>0</wait>\n" +
                        "        <test>0</test>\n" +
                        "        <payment id=\"\">\n" +
                        "            <prop name=\"cardnum\" value=\"" + personInfoProperties.getCardNum() + "\" />\n" +
                        "            <prop name=\"country\" value=\"UA\" />\n" +
                        "        </payment>";

        if (signature == null) {
            signature = SignatureGenerator.getSignature(dataTag + personInfoProperties.getMerchantPassword());
            if (signature == null)
                throw new RuntimeException("Signature not generated!");
        }

        String header = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<request version=\"1.0\">\n" +
                "    <merchant>\n" +
                "        <id>" + personInfoProperties.getMerchantId() + "</id>\n" +
                "        <signature>" + signature + "</signature>\n" +
                "    </merchant>\n" +
                "    <data>\n";
        String footer = "\n    </data>\n" +
                "</request>";
        return header + dataTag + footer;
    }

    private void sendXml(HttpURLConnection connection, String xmlLocation) throws IOException {
        OutputStream outputStream = connection.getOutputStream();
        byte[] b = xmlLocation.getBytes();
        outputStream.write(b);
        outputStream.flush();
        outputStream.close();
    }

    private HttpURLConnection getConnection() throws IOException {
        URL url = new URL("https://api.privatbank.ua/p24api/balance");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setInstanceFollowRedirects(false);
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/xml");
        return connection;
    }
}
