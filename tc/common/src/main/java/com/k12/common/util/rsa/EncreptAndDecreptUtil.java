package com.k12.common.util.rsa;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 * RSA加密解密
 * 
 * @author Administrator
 * 
 */
public class EncreptAndDecreptUtil {
	private static Logger log = Logger.getLogger(EncreptAndDecreptUtil.class);

	/** */
	/**
	 * RSA最大加密明文大小
	 */
	private static final int MAX_ENCRYPT_BLOCK = 117;

	/** */
	/**
	 * RSA最大解密密文大小
	 */
	private static final int MAX_DECRYPT_BLOCK = 128;

	/**
	 * 使用RSA算法获取一个随机密钥对
	 * 
	 * @param keyNum
	 * @return
	 */
	public static Map<String, String> getKeyMap(int keyNum) {

		Map<String, String> map = new HashMap<String, String>();
		Security.addProvider(new BouncyCastleProvider());
		KeyPairGenerator keyGen;
		try {
			keyGen = KeyPairGenerator.getInstance("RSA");
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
			keyGen.initialize(keyNum, random);
			KeyPair pair = keyGen.generateKeyPair();
			PrivateKey priv = pair.getPrivate();
			PublicKey pub = pair.getPublic();

			String privateStr = RSAUtils
					.encryptBASE64(((Key) priv).getEncoded());
			String publicStr = RSAUtils.encryptBASE64(((Key) pub).getEncoded());
			map.put("publicKey", publicStr);
			map.put("privateKey", privateStr);
			return map;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 使用公钥加密字符串
	 * 
	 * @param content
	 *            需要加密的内容
	 * @param pulicKey
	 *            加密时需要的公钥
	 * @return
	 */
	public static String encreptData(String content, String pulicKey) {
		/* Create the cipher */
		Cipher rsaCipher = null;

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache = null;
		int i = 0;
		try {
			int inputLen = content.getBytes("UTF-8").length;
			rsaCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			// rsaCipher.init(Cipher.ENCRYPT_MODE, pub);
			rsaCipher.init(Cipher.ENCRYPT_MODE,
					RSAUtils.getPublicKeyByString(pulicKey));
			// 对数据分段加密
			while (inputLen - offSet > 0) {
				if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
					cache = rsaCipher.doFinal(content.getBytes("UTF-8"),
							offSet, MAX_ENCRYPT_BLOCK);
				} else {
					cache = rsaCipher.doFinal(content.getBytes("UTF-8"),
							offSet, inputLen - offSet);
				}
				out.write(cache, 0, cache.length);
				i++;
				offSet = i * MAX_ENCRYPT_BLOCK;
			}
			byte[] encryptedData = out.toByteArray();
			out.close();
			return RSAUtils.encryptBASE64(encryptedData);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * RSA使用私钥解密解密
	 * 
	 * @param content
	 *            需要解密的base64字符串
	 * @param privateKey
	 *            私钥
	 * @return
	 */
	public static String decreptData(String content, String privateKey) {
		Cipher rsaCipher = null;
		int offSet2 = 0;
		byte[] cache2 = null;
		int i2 = 0;
		try {
			byte[] contentByte = RSAUtils.decryptBASE64(content);

			int inputLen2 = contentByte.length;
			ByteArrayOutputStream out2 = new ByteArrayOutputStream();
			rsaCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			rsaCipher.init(Cipher.DECRYPT_MODE,
					RSAUtils.getPrivateKeyByString(privateKey));
			// 对数据分段解密
			while (inputLen2 - offSet2 > 0) {
				if (inputLen2 - offSet2 > MAX_DECRYPT_BLOCK) {
					cache2 = rsaCipher.doFinal(contentByte, offSet2,
							MAX_DECRYPT_BLOCK);
				} else {
					cache2 = rsaCipher.doFinal(contentByte, offSet2, inputLen2
							- offSet2);
				}
				out2.write(cache2, 0, cache2.length);
				i2++;
				offSet2 = i2 * MAX_DECRYPT_BLOCK;
			}
			byte[] decryptedData2 = out2.toByteArray();
			out2.close();
			return new String(decryptedData2, "UTF-8");
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String md5(String str) {
		StringBuffer buf = new StringBuffer("");
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes());
			byte b[] = md.digest();
			int i;
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
		} catch (NoSuchAlgorithmException e) {
			log.error(EncreptAndDecreptUtil.class, e);

		}
		return buf.toString();
	}

	public static void main(String[] args) throws Exception {
		// >>>>>>>>>>>>>>生成公钥私钥>>>>>>>>>>>>>>>>>>
		Map<String, String> keyMap = getKeyMap(1024);
		System.out.println("com:"+md5("W0256"));
		System.out.println("pub:"+keyMap.get("publicKey"));
		System.out.println("prv:"+keyMap.get("privateKey"));
		FileUtils.writeStringToFile(new File("publick.txt"),
				keyMap.get("publicKey"));
		FileUtils.writeStringToFile(new File("private.txt"),
				keyMap.get("privateKey"));
	}
}
