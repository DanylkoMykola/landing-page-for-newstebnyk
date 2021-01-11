package com.danylko.newstebnyk.entity;

public class BalanceDitail {

    private String currency;
    private int avBalance;

    public BalanceDitail(String currency, int avBalance) {
        this.currency = currency;
        this.avBalance = avBalance;
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

    @Override
    public String toString() {
        return "BalanceDitail{" +
                "currency='" + currency + '\'' +
                ", avBalance=" + avBalance +
                '}';
    }
}
