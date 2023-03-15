# RC4算法

#### 描述：

- RC4是一种对称密码算法，它属于对称密码算法中的序列密码(streamcipher,也称为流密码)，它是可变密钥长度，面向字节操作的流密码。
- 对称密码算法的工作方式有四种：电子密码本(ECB, electronic codebook)方式、密码分组链接(CBC, cipherblock chaining)方式、密文反馈(CFB, cipher-feedback)方式、输出反馈(OFB, output-feedback)方式。
- RC4算法采用的是输出反馈工作方式(OFB)，所以可以用一个短的密钥产生一个相对较长的密钥序列。

#### 步骤：

- 1、S、T初始化
- 2、数据表S的初始置换
- 3、密钥流生成  ---- 伪随机数生成方法（PGRA）
- 4、加密  ---异或
- 5、解密  ---异或

![image-20230315121354017](C:\Users\zdsss\AppData\Roaming\Typora\typora-user-images\image-20230315121354017.png)

#### 伪码：

```c
//1、初始化S和T

for i=0 to 255 do

   S[i]=i;

   T[i]=K[ imod keylen ];

//2、初始排列S

j=0;

for i=0 to 255 do

   j= ( j+S[i]+T[i])mod256;

   swap(S[i],S[j]);//循环执行完成后，数据表S被伪随机化；

//3、产生密钥流

i,j=0;

for r=0 to len do  //r为明文长度，r字节

   i=(i+1) mod 256;

   j=(j+S[i])mod 256;

   swap(S[i],S[j]);

   t=(S[i]+S[j])mod 256;

   k[r]=S[t];
```

#### 代码实现（Java）：

```java
package com.zd.test;
import java.io.UnsupportedEncodingException;

public class RC4 {
    /**
     * RC4加密
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
        if (chartSet == null || chartSet.isEmpty()) {
            byte[] Data = data.getBytes();
            return bytesToHex(RC4Base(Data, key));
        } else {
            byte[] Data = data.getBytes(chartSet);
            return bytesToHex(RC4Base(Data, key));
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
     * @param Key 密钥
     * @return 初始化密钥
     */
    private static byte[] initKey(String Key) {
        byte[] S = new byte[256];
        byte[] T = new byte[256];
        byte[] K = Key.getBytes();
        int kLen = K.length;
        if (kLen == 0) {
            return null;
        }
        //1初始化S和T
        for (int i = 0; i < 256; i++) {
            S[i] = (byte) i;
            T[i]=K[i % kLen];
        }
        //2初始排列S
        int j = 0;
        for(int i = 0; i < 256; i++) {
            j = (j + (S[i] & 0xff) + (T[i] & 0xff)) & 0xff;//取后八位
            byte tmp = S[i];
            S[i] = S[j];
            S[j] = tmp;
        }
        return S;
    }
    /**
     * 字节数组转十六进制
     * @param bytes 字节数字
     * @return 十六进制字符串
     */
    public static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte aByte : bytes) {
            String hex = Integer.toHexString(aByte & 0xFF);
            if (hex.length() < 2) {
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
    public static byte[] hexToByte(String hexString){
        int hexlen = hexString.length();
        byte[] result;
        if (hexlen % 2 == 1){
            hexlen++;
            result = new byte[(hexlen/2)];
            hexString="0"+hexString;
        }else {
            result = new byte[(hexlen/2)];
        }
        int j=0;
        for (int i = 0; i < hexlen; i+=2){
            result[j]=(byte)Integer.parseInt(hexString.substring(i,i+2),16);
            j++;
        }
        return result;
    }

    /**
     * 产生密钥流并与输入异或
     * @param input
     * @param Key
     * @return
     */
    private static byte[] RC4Base(byte[] input, String Key) {
        int i = 0;
        int j = 0;
        byte[] S = initKey(Key);
        int t;
        byte[] result = new byte[input.length];
        for (int r = 0; r < input.length; r++) {
            i = (i + 1) & 0xff;
            assert S != null;
            j = ((S[i] & 0xff) + j) & 0xff;
            byte tmp = S[i];
            S[i] = S[j];
            S[j] = tmp;
            t = ((S[i] & 0xff) + (S[j] & 0xff)) & 0xff;
            result[r] = (byte) (input[r] ^ S[t]);
        }
        return result;
    }
}

```

#### 测试及结果展示：

##### 测试代码（Java）

```java
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
```

##### 结果展示：

![image-20230315204943849](C:\Users\zdsss\AppData\Roaming\Typora\typora-user-images\image-20230315204943849.png)
