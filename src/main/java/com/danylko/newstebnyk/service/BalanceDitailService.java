package com.danylko.newstebnyk.service;

import com.danylko.newstebnyk.entity.BalanceDitail;
import org.springframework.stereotype.Service;

import java.net.URI;

@Service
public interface BalanceDitailService {
    BalanceDitail requestBalanceDitail(String uri, String xmlLocation);
}
