package com.zd.test;

import java.io.UnsupportedEncodingException;

public class RC4Test {
    public static void main(String[] args) throws UnsupportedEncodingException {
        String PlainText = "软件004 张迪 2204112362";
        String key = "123456";
        String CipherText = RC4.encryRC4String(PlainText, key, "UTF-8");
        String newPlainText = RC4.decryRC4(CipherText,key,"UTF-8");
        System.out.println(CipherText+newPlainText);
    }
}
