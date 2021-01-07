package com.danylko.newstebnyk.entity;

import java.util.Date;

public class BalanceDitail {

    private String id;
    private String signature;
    private String cardNumber;
    private String currency;
    private int avBalance;
    private Date balanceDate;

    public BalanceDitail(String id, String signature, String cardNumber, String currency, int avBalance, Date balanceDate) {
        this.id = id;
        this.signature = signature;
        this.cardNumber = cardNumber;
        this.currency = currency;
        this.avBalance = avBalance;
        this.balanceDate = balanceDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public int getAvBalance() {
        return avBalance;
    }

    public void setAvBalance(int avBalance) {
        this.avBalance = avBalance;
    }

    public Date getBalanceDate() {
        return balanceDate;
    }

    public void setBalanceDate(Date balanceDate) {
        this.balanceDate = balanceDate;
    }
}
