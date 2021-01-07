package com.danylko.newstebnyk.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "location")
public class StorageProperties {

    private String xmlLocation;

    public String getUriBalanceRequest() {
        return uriBalanceRequest;
    }

    public void setUriBalanceRequest(String uriBalanceRequest) {
        this.uriBalanceRequest = uriBalanceRequest;
    }

    private String uriBalanceRequest;

    public String getXmlLocation() {
        return xmlLocation;
    }

    public void setXmlLocation(String xmlLocation) {
        this.xmlLocation = xmlLocation;
    }
}
