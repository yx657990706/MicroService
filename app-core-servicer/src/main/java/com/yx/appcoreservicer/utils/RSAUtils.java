package com.yx.appcoreservicer.utils;


import org.apache.commons.codec.binary.Base64;
import org.springframework.util.Base64Utils;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jesse
 * @version v1.0
 * @project my-base
 * @encoding UTF-8
 * @time 2019/5/11 11:42 PM
 * @Description <p>
 * 罗纳德·李维斯特（Ron [R]ivest）、阿迪·萨莫尔（Adi [S]hamir）和伦纳德·阿德曼（Leonard [A]dleman）
 * </p>
 * <p>
 * 字符串格式的密钥在未在特殊说明情况下都为BASE64编码格式<br/>
 * 由于非对称加密速度极其缓慢，一般文件不使用它来加密而是使用对称加密，<br/>
 * 非对称加密算法可以用来对对称加密的密钥加密，这样保证密钥的安全也就保证了数据的安全
 */
public class RSAUtils {
    /**
     * 加密算法RSA
     */
    public static final String KEY_ALGORITHM = "RSA";

    /**
     * 签名算法
     */
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";

    /**
     * 获取公钥的key
     */
    private static final String PUBLIC_KEY = "RSAPublicKey";

    /**
     * 获取私钥的key
     */
    private static final String PRIVATE_KEY = "RSAPrivateKey";

    /**
     * RSA最大加密明文大小<br>
     * RSA加密对明文的长度有所限制，规定需加密的明文最大长度=密钥长度-11（单位是字节，即byte），
     * 所以在加密和解密的过程中需要分块进行。而密钥默认是1024位，即1024位/8位-11=128-11=117字节。
     * 所以默认加密前的明文最大长度117字节，解密密文最大长度为128字。
     * 那么为啥两者相差11字节呢？是因为RSA加密使用到了填充模式（padding），
     * 即内容不足117字节时会自动填满，用到填充模式自然会占用一定的字节，而且这部分字节也是参与加密的。
     */
    private static final int MAX_ENCRYPT_BLOCK = 2048 / 8 - 11; // 117;

    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 2048 / 8; // 128;

    /**
     * <p>
     * 生成密钥对(公钥和私钥)
     * </p>
     *
     * @return
     * @throws Exception
     */
    public static Map<String, Object> genKeyPair(String keyAlgorithm) throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(keyAlgorithm);
        keyPairGen.initialize(1024);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        Map<String, Object> keyMap = new HashMap<String, Object>(2);
        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);
        return keyMap;
    }

    /**
     * <p>
     * 用私钥对信息生成数字签名
     * </p>
     *
     * @param data       已加密数据
     * @param privateKey 私钥(BASE64编码)
     * @return  签名字符串(BASE64编码)
     * @throws Exception
     */
    public static String sign(byte[] data, String privateKey, String keyAlgorithm, String signAlgorithm)
            throws Exception {
        byte[] keyBytes = Base64.decodeBase64(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(keyAlgorithm);
        PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Signature signature = Signature.getInstance(signAlgorithm);
        signature.initSign(privateK);
        signature.update(data);
        return new String(Base64.encodeBase64(signature.sign()), "UTF-8");
    }

    /**
     * <p>
     * 校验数字签名
     * </p>
     *
     * @param data      原始字符串的byte[]
     * @param publicKey 公钥(BASE64编码)
     * @param sign      数字签名(BASE64编码)
     * @return
     * @throws Exception
     */
    public static boolean verify(byte[] data, String publicKey, String sign, String keyAlgorithm, String signAlgorithm)
            throws Exception {
        byte[] keyBytes = Base64.decodeBase64(publicKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(keyAlgorithm);
        PublicKey publicK = keyFactory.generatePublic(keySpec);
        Signature signature = Signature.getInstance(signAlgorithm);
        signature.initVerify(publicK);
        signature.update(data);
        return signature.verify(Base64.decodeBase64(sign));
    }

    public static boolean verifyByHex(byte[] data, String publicKey, String sign, String keyAlgorithm,
                                      String signAlgorithm) throws Exception {
        byte[] keyBytes = hexStringToBytes(publicKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(keyAlgorithm);
        PublicKey publicK = keyFactory.generatePublic(keySpec);
        Signature signature = Signature.getInstance(signAlgorithm);
        signature.initVerify(publicK);
        signature.update(data);
        return signature.verify(hexStringToBytes(sign));
    }

    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    /**
     * Convert char to byte
     *
     * @param c char
     * @return byte
     */
    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    /**
     * <P>
     * 私钥解密
     * </p>
     *
     * @param encryptedData 已加密数据
     * @param privateKey    私钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPrivateKey(byte[] encryptedData, String privateKey, String keyAlgorithm)
            throws Exception {
        byte[] keyBytes = Base64.decodeBase64(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(keyAlgorithm);
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateK);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        int maxDecryptBlock = MAX_DECRYPT_BLOCK;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > maxDecryptBlock) {
                cache = cipher.doFinal(encryptedData, offSet, maxDecryptBlock);
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * maxDecryptBlock;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }

    /**
     * <p>
     * 公钥解密
     * </p>
     *
     * @param encryptedData 已加密数据
     * @param publicKey     公钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPublicKey(byte[] encryptedData, String publicKey, String keyAlgorithm)
            throws Exception {
        byte[] keyBytes = Base64.decodeBase64(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(keyAlgorithm);
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicK);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        int maxDecryptBlock = MAX_DECRYPT_BLOCK;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > maxDecryptBlock) {
                cache = cipher.doFinal(encryptedData, offSet, maxDecryptBlock);
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * maxDecryptBlock;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }

    /**
     * <p>
     * 公钥加密
     * </p>
     *
     * @param data      源数据
     * @param publicKey 公钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPublicKey(byte[] data, String publicKey, String keyAlgorithm) throws Exception {
        byte[] keyBytes = Base64.decodeBase64(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(keyAlgorithm);
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicK);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        int maxEncryptBlock = MAX_ENCRYPT_BLOCK;

        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > maxEncryptBlock) {
                cache = cipher.doFinal(data, offSet, maxEncryptBlock);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * maxEncryptBlock;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }

    /**
     * <p>
     * 私钥加密
     * </p>
     *
     * @param data       源数据
     * @param privateKey 私钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPrivateKey(byte[] data, String privateKey, String keyAlgorithm) throws Exception {
        byte[] keyBytes = Base64.decodeBase64(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(keyAlgorithm);
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateK);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        int maxEncryptBlock = MAX_ENCRYPT_BLOCK;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > maxEncryptBlock) {
                cache = cipher.doFinal(data, offSet, maxEncryptBlock);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * maxEncryptBlock;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }

    /**
     * <p>
     * 获取私钥
     * </p>
     *
     * @param keyMap 密钥对
     * @return
     * @throws Exception
     */
    public static String getPrivateKey(Map<String, Object> keyMap) throws Exception {
        Key key = (Key) keyMap.get(PRIVATE_KEY);
        return Base64Utils.encodeToUrlSafeString(key.getEncoded());
    }

    /**
     * <p>
     * 获取公钥
     * </p>
     *
     * @param keyMap 密钥对
     * @return
     * @throws Exception
     */
    public static String getPublicKey(Map<String, Object> keyMap) throws Exception {
        Key key = (Key) keyMap.get(PUBLIC_KEY);
        return Base64Utils.encodeToUrlSafeString(key.getEncoded());
    }


    public static void main(String[] args) throws Exception {

        //实际应用中，要根据情况使用，也可以同时使用加密和签名，比如A和B都有一套自己的公钥和私钥，当A要给B发送消息时，先用B的公钥对消息加密，
        // 再对加密的消息使用A的私钥加签名，达到既不泄露也不被篡改，更能保证消息的安全性。
        Map map = RSAUtils.genKeyPair(RSAUtils.KEY_ALGORITHM);
        String publickey = RSAUtils.getPublicKey(map);
        String priveteKey = RSAUtils.getPrivateKey(map);

        System.out.println("publickey:" + publickey);
        System.out.println("priveteKey:" + priveteKey);

        String orignStr = "1234qwer!@#$%^&*)(撒的是啊（）&……%¥#@！";
        byte[] data = RSAUtils.encryptByPublicKey(orignStr.getBytes(), publickey, RSAUtils.KEY_ALGORITHM);
        System.out.println("加密后：" + new String(Base64.encodeBase64(data), "UTF-8"));
        System.out.println("解密后：" + new String(RSAUtils.decryptByPrivateKey(data, priveteKey, RSAUtils.KEY_ALGORITHM), "UTF-8"));

        String signAlgorithm = "MD5withRSA";
        String keyAlgorithm = "RSA";

        String sign = RSAUtils.sign(orignStr.getBytes(),priveteKey,keyAlgorithm,signAlgorithm);
        System.out.println("数字签名后："+sign);
        System.out.println("验证签名："+RSAUtils.verify("QSAHFJHFKHFKJHF1234KLFLFJFJ".getBytes(),publickey,sign,keyAlgorithm,signAlgorithm));

//        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
//        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
    }
}
