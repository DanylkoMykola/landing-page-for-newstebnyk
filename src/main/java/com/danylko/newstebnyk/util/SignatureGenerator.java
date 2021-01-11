package com.danylko.newstebnyk.util;

import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public  class SignatureGenerator {

    private SignatureGenerator() {}

    public static String getSignature(String password) {
        try {
            byte[] bytesOfPassword = password.getBytes("UTF-8");
            byte[] bytesMD5 = getHashFunc(bytesOfPassword, "MD5");
            if (bytesMD5 != null) {
                byte[] bytesSHA1 = getHashFunc(bytesMD5, "SHA-1");
                return DatatypeConverter.printHexBinary(bytesSHA1);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
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
