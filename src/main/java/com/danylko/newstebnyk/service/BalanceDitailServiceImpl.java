package com.danylko.newstebnyk.service;

import com.danylko.newstebnyk.config.PersonInfoProperties;
import com.danylko.newstebnyk.entity.BalanceDitail;
import com.danylko.newstebnyk.util.SignatureGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class BalanceDitailServiceImpl implements BalanceDitailService {

    Logger logger = LoggerFactory.getLogger(BalanceDitailServiceImpl.class);

    @Autowired
    PersonInfoProperties personInfoProperties;

    private static String signature;

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

    @Override
    public BalanceDitail getBalanceDitail() {
        BalanceDitail balanceDitail = null;
        String xml = getXml();
        try {
            URL url = new URL("https://api.privatbank.ua/p24api/balance");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setInstanceFollowRedirects(false);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/xml");
            logger.info("-------------send xml started------------");

            sendXml(connection, xml);
            logger.info("-------------send xml finished------------");
            balanceDitail = unmarhal(connection);
            connection.getResponseCode();
            connection.disconnect();
        } catch(Exception e) {
            logger.info("Can't request and unmarshal object", e);
        }
        return balanceDitail;
    }

    private BalanceDitail unmarhal(HttpURLConnection connection) throws JAXBException, IOException {
        InputStream is = connection.getInputStream();
        byte[] res = new byte[2048];
        int i = 0;
        StringBuilder response = new StringBuilder();
        while ((i = is.read(res)) != -1) {
            response.append(new String(res, 0, i));
        }
        logger.info("-------------Response=" + response.toString() +"------------");
        JAXBContext jaxbContext = JAXBContext.newInstance(BalanceDitail.class);
        is.close();
        BalanceDitail balanceDitail = (BalanceDitail) jaxbContext.createUnmarshaller().unmarshal(new FileReader("C:\\Users\\Mykola\\Development\\newstebnyk\\src\\main\\resources\\balance-ditail.xml"));
        return  balanceDitail;
    }
    private void sendXml(HttpURLConnection connection, String xmlLocation) throws IOException {
        OutputStream outputStream = connection.getOutputStream();
        byte[] b = xmlLocation.getBytes();
        outputStream.write(b);
        outputStream.flush();
        outputStream.close();
    }
}
