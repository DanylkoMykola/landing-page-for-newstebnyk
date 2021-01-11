package com.danylko.newstebnyk.parserrxml;

import com.danylko.newstebnyk.entity.BalanceDitail;

import java.io.InputStream;
import java.net.HttpURLConnection;

public interface ParserXml {
    BalanceDitail parse(HttpURLConnection connection);
}
