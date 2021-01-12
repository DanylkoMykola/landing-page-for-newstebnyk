package com.danylko.newstebnyk.parserrxml;

import com.danylko.newstebnyk.entity.BalanceDitail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.stream.Collectors;

@Component
public class SaxXmlParser implements ParserXml {

    Logger logger = LoggerFactory.getLogger(SaxXmlHandler.class);

    private static BalanceDitail balanceDitail;
    private static SAXParser parser;

    public BalanceDitail parse(HttpURLConnection connection)  {
        try {
            InputStream is = connection.getInputStream();
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            SaxXmlHandler handler = new SaxXmlHandler();
            parser.parse(is,handler);
            is.close();
        } catch (SAXException | ParserConfigurationException | IOException e) {
            e.printStackTrace();
        }
        return balanceDitail;
    }

    class SaxXmlHandler extends DefaultHandler {

        private String balance, currency, lastElementName;

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            lastElementName = qName;
            if (lastElementName.equals("error"))
                throw new SAXException("Error was happening when reading the xml: error message = " +
                        attributes.getValue("message"));
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            if ((balance != null && !balance.isEmpty()) && (currency != null && !currency.isEmpty())) {
                double bal = Double.parseDouble(balance);
                balanceDitail = new BalanceDitail(currency, bal);
                currency = null;
                balance = null;
            }
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            String information = new String(ch, start, length);
            information = information.replace("\n", "").trim();

            if (!information.isEmpty()) {
                if (lastElementName.equals("currency"))
                    currency = information;
                if (lastElementName.equals("av_balance"))
                    balance = information;
            }
        }
    }
}
