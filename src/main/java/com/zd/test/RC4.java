package com.zd.test;

import java.io.UnsupportedEncodingException;

public class RC4 {

    /**
     * RC4加密，将加密后的字节数据返回字符串
     * @param data 需要加密的数据
     * @param key 加密密钥
     * @param chartSet 编码方式
     * @return 返回加密后的数据
     * @throws UnsupportedEncodingException
     */
    public static String encodeRC4(String data, String key, String chartSet) throws UnsupportedEncodingException {
        if (data == null || key == null) {
            return null;
        }
        return bytesToHex(encodeRC4Byte(data, key, chartSet));
    }

    /**
     * RC4加密，将返回加密后的字节数据
     * @param data 需要加密的数据
     * @param key 加密密钥
     * @param chartSet 编码方式
     * @return 返回加密后的数据
     * @throws UnsupportedEncodingException
     */
    public static byte[] encodeRC4Byte(String data, String key, String chartSet) throws UnsupportedEncodingException {
        if (data == null || key == null) {
            return null;
        }
        if (chartSet == null || chartSet.isEmpty()) {
            byte[] Data = data.getBytes();
            return RC4Base(Data, key);
        } else {
            byte[] Data = data.getBytes(chartSet);
            return RC4Base(Data, key);
        }
    }

    /**
     * RC4解密
     *
     * @param data 需要解密的数据
     * @param key  加密密钥
     * @return 返回解密后的数据
     * @throws UnsupportedEncodingException
     */
    public static String decodeRC4(String data, String key,String chartSet) throws UnsupportedEncodingException {
        if (data == null || key == null) {
            return null;
        }
        return new String(RC4Base(hexToByte(data), key),chartSet);
    }

    /**
     * RC4加密初始化密钥
     * @param aKey
     * @return
     */
    private static byte[] initKey(String aKey) {
        byte[] K = aKey.getBytes();
        int kLen = K.length;
        byte[] S = new byte[256];
        byte[] T = new byte[256];
        //1初始化S
        for (int i = 0; i < 256; i++) {
            S[i] = (byte) i;
        }
        int index1 = 0;
        int index2 = 0;
        if (kLen == 0) {
            return null;
        }
        //2初始化T
        for(int i = 0;i < 256 ; i++){
            T[i]=K[i % kLen];
        }
        //3初始排列S
        int j = 0;
        for(int i = 0; i < 256; i++) {
            j = (j + S[i] + T[i]) % 256;
            byte tmp = S[i];
            S[i] = S[j];
            S[j] = tmp;
        }
        return S;
    }
    /**
     * 字节数组转十六进制
     * @param bytes
     * @return
     */
    public static String bytesToHex(byte[] bytes) {
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if(hex.length() < 2){
                sb.append(0);
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    /**
     * 十六进制转字节数组
     * @param
     * @return
     */
    public static byte[] hexToByte(String inHex){
        int hexlen = inHex.length();
        byte[] result;
        if (hexlen % 2 == 1){
            hexlen++;
            result = new byte[(hexlen/2)];
            inHex="0"+inHex;
        }else {
            result = new byte[(hexlen/2)];
        }
        int j=0;
        for (int i = 0; i < hexlen; i+=2){
            result[j]=(byte)Integer.parseInt(inHex.substring(i,i+2),16);
            j++;
        }
        return result;
    }

    /**
     * RC4解密
     * @param input
     * @param mKkey
     * @return
     */
    private static byte[] RC4Base(byte[] input, String mKkey) {
        int x = 0;
        int y = 0;
        byte key[] = initKey(mKkey);
        int xorIndex;
        byte[] result = new byte[input.length];
        for (int i = 0; i < input.length; i++) {
            x = (x + 1) & 0xff;
            y = ((key[x] & 0xff) + y) & 0xff;
            byte tmp = key[x];
            key[x] = key[y];
            key[y] = tmp;
            xorIndex = ((key[x] & 0xff) + (key[y] & 0xff)) & 0xff;
            result[i] = (byte) (input[i] ^ key[xorIndex]);
        }
        return result;
    }
}
