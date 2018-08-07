package com.k12.common.util.rsa;



import java.io.ByteArrayOutputStream;
//import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import org.apache.commons.codec.binary.Base64;



public class RSAUtils {
	/**********************************以证书的方式进行加密解密，加签验签*************************************/
	/**
	 * ：
	 */
	/** 
     * Java密钥库(Java Key Store，JKS)KEY_STORE 
     */  
    public static final String KEY_STORE = "JKS";
    public static final String X509 = "X.509"; 
    
    /** 
     * 获得秘钥库信息
     * 
     * @param keyStorePath 
     * @param password 
     * @return 
     * @throws Exception 
     */  
    public static KeyStore getKeyLibs(String keyStorePath, String password)
            throws Exception {  
        FileInputStream is = new FileInputStream(keyStorePath);  
        KeyStore ks = KeyStore.getInstance(KEY_STORE);  
        ks.load(is, password.toCharArray());  
        is.close();  
        return ks;  
    }
    /** 
     * 获得证书（公钥）
     * @param certificatePath 
     * @return 
     * @throws Exception 
     */  
    public static Certificate getCertificate(String certificatePath) throws Exception {  
        CertificateFactory certificateFactory = CertificateFactory.getInstance(X509);  
        FileInputStream in = new FileInputStream(certificatePath);  
        Certificate certificate = certificateFactory.generateCertificate(in);  
        in.close();  
        return certificate;  
    }
	/** 
     * 验证签名 （用公钥验证签名）
     * @param data 
     * @param sign 
     * @param certificatePath 
     * @return 
     * @throws Exception 
     */  
    public static boolean verify(byte[] data, String sign,X509Certificate cert) throws Exception {  
        // 获得公钥
        PublicKey publicKey = cert.getPublicKey();  
        // 构建签名 
        Signature signature = Signature.getInstance(cert.getSigAlgName());  
        signature.initVerify(publicKey);  
        signature.update(data);  
        return signature.verify(decryptBASE64(sign));  
    }
    
    /** 
     * BASE64解密 
     * @param key 
     * @return 
     * @throws Exception 
     */  
    public static byte[] decryptBASE64(String key) throws Exception {  
    	return Base64.decodeBase64(key);
    }
	
    /** 
     * 私钥加密 
     * @param data 
     * @param keyStorePath 
     * @param alias 
     * @param password 
     * @return 
     * @throws Exception 
     */  
    public static byte[] encryptByPrivateKey(byte[] data, String keyStorePath,  
            String alias, String password) throws Exception {  
        // 取得私钥  
    	FileInputStream is = new FileInputStream(keyStorePath);  
        KeyStore ks = KeyStore.getInstance(KEY_STORE);
        ks.load(is, password.toCharArray());  
        is.close();  
        PrivateKey privateKey = (PrivateKey) ks.getKey(alias, password.toCharArray());  
        // 对数据加密  
        Cipher cipher = Cipher.getInstance(privateKey.getAlgorithm());  
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);  
        return cipher.doFinal(data);  
    } 
    /** 
     * BASE64加密 
     *  
     * @param key 
     * @return 
     * @throws Exception 
     */  
    public static String encryptBASE64(byte[] key) throws Exception {  
    	return Base64.encodeBase64String(key);
    }  
	
    /********************************************以公钥私钥字符串的方式**********************************/
                               /**
                                * 生成密钥对，然后导出公钥字符串和私钥字符串，用公钥字符串和私钥字符串进行加密，解密。加签，验签名
                                */
    /**
     *  用私钥对信息生成数字签名
     * @param data  //加密数据
     * @param privateKey    //私钥
     * @return
     * @throws Exception
     */
    public static String sign(byte[] data,String privateKey)throws Exception{
        //解密私钥
        byte[] keyBytes = decryptBASE64(privateKey);
        //构造PKCS8EncodedKeySpec对象
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
        //指定加密算法
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        //取私钥匙对象
        PrivateKey privateKey2 = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        //用私钥对信息生成数字签名
        Signature signature = Signature.getInstance("MD5withRSA");
        signature.initSign(privateKey2);
        signature.update(data);
         
        return encryptBASE64(signature.sign());
    }
    /**
     *  用私钥对信息生成数字签名
     * @param data  //加密数据
     * @param privateKey    //私钥
     * @return
     * @throws Exception
     */
    public static String sign2(byte[] data,PrivateKey privateKey)throws Exception{
    	Signature signature = Signature.getInstance("MD5withRSA");
    	signature.initSign(privateKey);
    	signature.update(data);
    	
    	return encryptBASE64(signature.sign());
    }
    /**
     * 校验数字签名
     * @param data  加密数据
     * @param publicKey 公钥
     * @param sign  数字签名
     * @return
     * @throws Exception
     */
    public static boolean verify(byte[] data,String publicKey,String sign)throws Exception{
        //解密公钥
        byte[] keyBytes = decryptBASE64(publicKey);
        //构造X509EncodedKeySpec对象
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
        //指定加密算法
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        //取公钥匙对象
        PublicKey publicKey2 = keyFactory.generatePublic(x509EncodedKeySpec);       
        Signature signature = Signature.getInstance("MD5withRSA");
        signature.initVerify(publicKey2);
        signature.update(data);
        //验证签名是否正常
        return signature.verify(decryptBASE64(sign));
    }
    /**
     * MD5加密工具类
     * @param data
     * @return
     * @throws Exception
     */
    public static byte[] encryptMD5(byte[] data)throws Exception{
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(data);
        return md5.digest();
    }
     
	public static String getEncode32(String str,String charset) throws Exception {
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] byteArray= null; 
		if(charset!=null){
			byteArray = str.getBytes(charset);
		}else{		
			byteArray= str.getBytes();
		}
		byte[] resultArray = encryptMD5(byteArray);
		StringBuilder builder = new StringBuilder();
		for (byte b : resultArray) {
			builder.append(Integer.toHexString((b >> 4) & 0xf));
			builder.append(Integer.toHexString(b & 0xf));
		}
		return builder.toString();
	}
    
    
    /**
     * SHA加密工具类
     * @param data
     * @return
     * @throws Exception
     */
    public static byte[] encryptSHA(byte[] data)throws Exception{
        MessageDigest sha = MessageDigest.getInstance("SHA");
        sha.update(data);
        return sha.digest();
    }
    
    /**
     * 取得公钥，并转化为String类型
     * @param keyMap
     * @return
     * @throws Exception
     */
    public static String getPublicKey(Map<String, Object> keyMap)throws Exception{
        Key key = (Key) keyMap.get("RSAPublicKey");  
        return encryptBASE64(key.getEncoded());     
    }
 
    /**
     * 取得私钥，并转化为String类型
     * @param keyMap
     * @return
     * @throws Exception
     */
    public static String getPrivateKey(Map<String, Object> keyMap) throws Exception{
        Key key = (Key) keyMap.get("RSAPrivateKey");  
        return encryptBASE64(key.getEncoded());     
    }
    
    
    
    /**
     * 初始化密钥
     * @return
     * @throws Exception
     */
    public static Map<String,Object> initKey()throws Exception{
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
         
        //公钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        //私钥
        RSAPrivateKey privateKey =  (RSAPrivateKey) keyPair.getPrivate();
         
        Map<String,Object> keyMap = new HashMap<String, Object>(2);
        keyMap.put("RSAPublicKey", publicKey);
        keyMap.put("RSAPrivateKey", privateKey);
         
        return keyMap;
    }
    
    /**
     * 用私钥加密
     * @param data  加密数据
     * @param key   密钥
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPrivateKey(byte[] data,String key)throws Exception{
        //解密密钥
        byte[] keyBytes = decryptBASE64(key);
        //取私钥
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        Key privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
         
        //对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
         
        return cipher.doFinal(data);
    }
    
   /** *//** 
    * RSA最大加密明文大小 
    */  
   private static final int MAX_ENCRYPT_BLOCK = 117;  
     
   /** *//** 
    * RSA最大解密密文大小 
    */  
   private static final int MAX_DECRYPT_BLOCK = 128;  
    /** *//** 
     * <p> 
     * 分段公钥加密 
     * </p> 
     *  
     * @param data 源数据 
     * @param publicKey 公钥(BASE64编码) 
     * @return 
     * @throws Exception 
     */  
    
    public static byte[] encryptByPublicKeyF(byte[] data, String publicKey)  
            throws Exception {  
        byte[] keyBytes = decryptBASE64(publicKey);  
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);  
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");  
        Key publicK = keyFactory.generatePublic(x509KeySpec);  
        // 对数据加密  
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());  
        cipher.init(Cipher.ENCRYPT_MODE, publicK);  
        int inputLen = data.length;  
        ByteArrayOutputStream out = new ByteArrayOutputStream();  
        int offSet = 0;  
        byte[] cache;  
        int i = 0;  
        // 对数据分段加密  
        while (inputLen - offSet > 0) {  
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {  
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);  
            } else {  
                cache = cipher.doFinal(data, offSet, inputLen - offSet);  
            }  
            out.write(cache, 0, cache.length);  
            i++;  
            offSet = i * MAX_ENCRYPT_BLOCK;  
        }  
        byte[] encryptedData = out.toByteArray();  
        out.close();  
        return encryptedData;  
    }  
    
    
    /** *//** 
     * <P> 
     * 分段私钥解密 
     * </p> 
     *  
     * @param encryptedData 已加密数据 
     * @param privateKey 私钥(BASE64编码) 
     * @return 
     * @throws Exception 
     */  
    public static byte[] decryptByPrivateKeyF(byte[] encryptedData, String privateKey)  
            throws Exception {  
        byte[] keyBytes = decryptBASE64(privateKey);  
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);  
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");  
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);  
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());  
        cipher.init(Cipher.DECRYPT_MODE, privateK);  
        int inputLen = encryptedData.length;  
        ByteArrayOutputStream out = new ByteArrayOutputStream();  
        int offSet = 0;  
        byte[] cache;  
        int i = 0;  
        // 对数据分段解密  
        while (inputLen - offSet > 0) {  
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {  
                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);  
            } else {  
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);  
            }  
            out.write(cache, 0, cache.length);  
            i++;  
            offSet = i * MAX_DECRYPT_BLOCK;  
        }  
        byte[] decryptedData = out.toByteArray();  
        out.close();  
        return decryptedData;  
    }  
    
    /**
     * 用公钥解密
     * @param data  加密数据
     * @param key   密钥
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPublicKey(byte[] data,String key)throws Exception{
        //对私钥解密
        byte[] keyBytes = decryptBASE64(key);
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        Key publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
         
        //对数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
         
        return cipher.doFinal(data);
    }
    public static Key getPublicKeyByString(String key)throws Exception{
    	//对私钥解密
    	byte[] keyBytes = decryptBASE64(key);
    	X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
    	KeyFactory keyFactory = KeyFactory.getInstance("RSA");
    	Key publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
    	return publicKey;
    }
    /**
     * 用公钥加密
     * @param data  加密数据
     * @param key   密钥
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPublicKey(byte[] data,String key)throws Exception{
        //对公钥解密
        byte[] keyBytes = decryptBASE64(key);
        //取公钥
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        Key publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
         
        //对数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
         
        return cipher.doFinal(data);
    }
    /**
     * 用私钥解密<span style="color:#000000;"></span> * @param data  加密数据
     * @param key   密钥
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPrivateKey(byte[] data,String key)throws Exception{
        //对私钥解密
        byte[] keyBytes = decryptBASE64(key);
         
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        Key privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        //对数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
         
        return cipher.doFinal(data);
    }
    public static Key getPrivateKeyByString(String key)throws Exception{
    	//对私钥解密
    	byte[] keyBytes = decryptBASE64(key);
    	
    	PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
    	KeyFactory keyFactory = KeyFactory.getInstance("RSA");
    	Key privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
    	return privateKey;
    }
}

    

