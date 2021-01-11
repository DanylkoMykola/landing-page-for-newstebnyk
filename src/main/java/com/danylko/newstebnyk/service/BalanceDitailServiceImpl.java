package com.danylko.newstebnyk.service;

import com.danylko.newstebnyk.entity.BalanceDitail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    @Override
    public BalanceDitail getBalanceDitail() {
        String testStr =
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<request version=\"1.0\">" +
                "<merchant>" +
                "<id>180423</id>" +
                "<signature>3115efc6f4543ffd529bbf1b50f4d778ffbd2d3e</signature>" +
                "</merchant>" +
                "<data>" +
                "<oper>cmt</oper>" +
                "<wait>0</wait>" +
                "<test>1</test>" +
                "<payment id=\"\">" +
                "<prop name=\"cardnum\" value=\"4149439010692766\" />" +
                "<prop name=\"country\" value=\"UA\" />" +
                "</payment>" +
                "</data>" +
                "</request>";
        BalanceDitail balanceDitail = null;
        try {
            URL url = new URL("https://api.privatbank.ua/p24api/balance");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setInstanceFollowRedirects(false);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/xml");
            logger.info("-------------send xml started------------");
            sendXml(connection, testStr);
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
