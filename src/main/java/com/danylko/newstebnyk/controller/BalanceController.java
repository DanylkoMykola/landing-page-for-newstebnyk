package com.danylko.newstebnyk.controller;

import com.danylko.newstebnyk.config.PersonInfoProperties;
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

    public BalanceController(BalanceDitailService balanceDitailService) {
        this.balanceDitailService = balanceDitailService;

    }

    @GetMapping(name = "/")
    public String get(Model model) {
        BalanceDitail bd = balanceDitailService.getBalanceDitail();
        model.addAttribute("balance", bd);
        return "index";
    }
}
