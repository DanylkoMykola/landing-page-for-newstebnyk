package com.danylko.newstebnyk.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "personinfo")
public class PersonInfoProperties {

    private String merchantId;
    private String merchantPassword;
    private String cardNum;



    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantPassword() { return merchantPassword; }

    public void setMerchantPassword(String merchantPassword) { this.merchantPassword = merchantPassword; }

    public String getCardNum() { return cardNum; }

    public void setCardNum(String cardNum) { this.cardNum = cardNum; }
}
