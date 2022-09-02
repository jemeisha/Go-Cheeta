package com.jemeisha.gocheeta;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Util {

    public static String hashMD5(String password) throws NoSuchAlgorithmException {

        MessageDigest md= MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte [] digest= md.digest();

        return bytesToHex(digest);
    }
    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
