package com.danylko.newstebnyk.util;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public  class SignatureGenerator {

    public static String getSignature(String password) {
        String strMD5 = getHashFunc(password, "MD5");
        if (strMD5 == null) { return null; }
        return getHashFunc(strMD5, "SHA-1");
    }

    private static String getHashFunc(String password, String algorithm) {
        try {
            byte[] bytes = password.getBytes();
            MessageDigest md = MessageDigest.getInstance(algorithm);
            md.update(bytes);
            return DatatypeConverter.printHexBinary(md.digest()).toLowerCase();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

}
