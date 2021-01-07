package com.danylko.newstebnyk.service;

import com.danylko.newstebnyk.config.StorageProperties;
import com.danylko.newstebnyk.entity.BalanceDitail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class BalanceDitailServiceImpl implements BalanceDitailService {

    Logger logger = LoggerFactory.getLogger(BalanceDitailServiceImpl.class);

    @Override
    public BalanceDitail requestBalanceDitail(String uri, String xmlLocation) {
        BalanceDitail balanceDitail = null;
        try {
            URL url = new URL(uri);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setInstanceFollowRedirects(false);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", xmlLocation);
            InputStream is = connection.getInputStream();
            balanceDitail = unmarhal(is);
            is.close();
            connection.getResponseCode();
            connection.disconnect();
        } catch(Exception e) {
            logger.info("Can't request and unmarshal object", e);
        }
        return balanceDitail;
    }

    public BalanceDitail unmarhal(InputStream is) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(BalanceDitail.class);
        return (BalanceDitail) jaxbContext.createUnmarshaller().unmarshal(is);
    }
}
