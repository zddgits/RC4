package com.zd.test;

import java.io.UnsupportedEncodingException;
import java.util.Scanner;

public class RC4Test {
    public static void main(String[] args) throws UnsupportedEncodingException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入明文：");
        String PlainText = scanner.nextLine();
        System.out.println("请输入密钥：");
        String key = scanner.nextLine();
        String CipherText = RC4.encodeRC4(PlainText, key, "UTF-8");
        String newPlainText = RC4.decodeRC4(CipherText,key,"UTF-8");
        System.out.println("加密所得16进制密文为：");
        System.out.println(CipherText);
        System.out.println("解密得： ");
        System.out.println(newPlainText);
    }
}
