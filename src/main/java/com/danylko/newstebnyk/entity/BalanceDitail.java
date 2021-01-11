package com.danylko.newstebnyk.entity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@XmlRootElement(name="cardbalance")
public class BalanceDitail {

 private String id;
    private String signature;
    private String cardNumber;
    private String currency;

    private int avBalance;
    public String getId() {
        return id;
    }

    @XmlElement(name="id")
    public void setId(String id) {
        this.id = id;
    }

    public String getSignature() {
        return signature;
    }
    @XmlElement(name="signature")
    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    @XmlElement(name="card_number")
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCurrency() {
        return currency;
    }

    @XmlElement(name="currency")
    public void setCurrency(String currency) {
        this.currency = currency;
    }


    public int getAvBalance() {
        return avBalance;
    }

    @XmlElement(name="av_balance")
    public void setAvBalance(int avBalance) {
        this.avBalance = avBalance;
    }

 @Override
    public String toString() {
        return "BalanceDitail{" +
                "id='" + id + '\'' +
                ", signature='" + signature + '\'' +
                ", cardNumber='" + cardNumber + '\'' +
                ", currency='" + currency + '\'' +
                ", avBalance=" + avBalance +
                '}';
    }

}
