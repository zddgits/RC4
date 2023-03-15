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
##### 注意事项
byte范围为[-128~127],故使用%256会出现负数，会导致数组越界报错，故使用& 0xff取后八位。