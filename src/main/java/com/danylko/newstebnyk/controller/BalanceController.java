package com.danylko.newstebnyk.controller;

import com.danylko.newstebnyk.config.StorageProperties;
import com.danylko.newstebnyk.entity.BalanceDitail;
import com.danylko.newstebnyk.service.BalanceDitailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BalanceController {

    Logger logger = LoggerFactory.getLogger(BalanceController.class);

    private final BalanceDitailService balanceDitailService;
    private final StorageProperties storageProperties;

    public BalanceController(BalanceDitailService balanceDitailService, StorageProperties storageProperties) {
        this.balanceDitailService = balanceDitailService;
        this.storageProperties = storageProperties;
    }

    @GetMapping(name = "/")
    public String get(Model model) {
        BalanceDitail balanceDitail =
                balanceDitailService.requestBalanceDitail(
                        storageProperties.getUriBalanceRequest(),
                        storageProperties.getXmlLocation()
                );
        logger.info("BalanceDitail created:" + balanceDitail.toString());
        model.addAttribute("balance", balanceDitail);
        return "index";
    }
}
