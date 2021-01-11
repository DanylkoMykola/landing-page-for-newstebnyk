package com.danylko.newstebnyk.handlerxml;

import com.danylko.newstebnyk.entity.BalanceDitail;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SaxXmlHendler extends DefaultHandler {

    private String balance, currency, lastElementName;
    private BalanceDitail balanceDitail;

    public BalanceDitail getBalanceDitail() {
        return balanceDitail;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        lastElementName = qName;
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if ( (balance != null && !balance.isEmpty()) && (currency != null && !currency.isEmpty()) ) {
            int bal = Integer.parseInt(balance);
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
