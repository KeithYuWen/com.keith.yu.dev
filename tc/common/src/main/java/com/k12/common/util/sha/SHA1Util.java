package com.k12.common.util.sha;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA1Util {
    public static String getSHA1(String str) {
        StringBuffer hexstr = new StringBuffer();
        // SHA1签名生成
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-1");

            md.update(str.getBytes());
            byte[] digest = md.digest();

            String shaHex = "";
            for (int i = 0; i < digest.length; i++) {
                shaHex = Integer.toHexString(digest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexstr.append(0);
                }
                hexstr.append(shaHex);
            }
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
        return hexstr.toString().toUpperCase();

    }
}
