package com.danylko.newstebnyk.util;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public  class SignatureGenerator {

    public static String getSignature(String password) {
        byte[] bytesOfPassword = password.getBytes();
        byte[] bytesMD5 = getHashFunc(bytesOfPassword, "MD5");
        if (bytesMD5 == null) { return null; }
        String convert = DatatypeConverter.printHexBinary(bytesMD5).toLowerCase();
        byte[] bytesSHA1 = getHashFunc(convert.getBytes(), "SHA-1");
        return DatatypeConverter.printHexBinary(bytesSHA1).toLowerCase();
    }

    private static byte[] getHashFunc(byte[] bytes, String algorithm) {
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            md.update(bytes);
            return md.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

}
