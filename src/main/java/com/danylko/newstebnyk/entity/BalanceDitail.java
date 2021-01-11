package com.danylko.newstebnyk.entity;

public class BalanceDitail {

    private String currency;
    private double avBalance;

    public BalanceDitail(String currency, double avBalance) {
        this.currency = currency;
        this.avBalance = avBalance;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getAvBalance() {
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
