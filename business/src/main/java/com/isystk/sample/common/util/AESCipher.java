package com.isystk.sample.common.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
 * AES暗号・複合化
 */
public class AESCipher {
	/** 暗号アルゴリズム名 */
	private static final String ALGORITHM = "AES";
	private static final String TRANSFORM = "AES/CBC/PKCS5Padding";

	/** キー */
	private Key skey;
	/** 初期ベクトル */
	private IvParameterSpec iv;

	public void setKey(byte[] key) throws NoSuchAlgorithmException {
		this.skey = new SecretKeySpec(key, ALGORITHM);
	}

	public void setIv(byte[] iv) {
		this.iv = new IvParameterSpec(iv);
	}

	/**
	 * 暗号化
	 * 
	 * @param src
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws InvalidAlgorithmParameterException
	 */
	public byte[] encrypt(byte[] src) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
			IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
		Cipher cipher = Cipher.getInstance(TRANSFORM);

		cipher.init(Cipher.ENCRYPT_MODE, this.skey, this.iv);
		return cipher.doFinal(src);
	}

	public String encryptBase64(byte[] src)
			throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException,
			BadPaddingException, InvalidAlgorithmParameterException, UnsupportedEncodingException {
		return new String(Base64.encodeBase64(this.encrypt(src)));
	}

	/**
	 * 複合化
	 * 
	 * @param src
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws InvalidAlgorithmParameterException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public byte[] decrypt(byte[] src) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
			InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		Cipher cipher = Cipher.getInstance(TRANSFORM);

		cipher.init(Cipher.DECRYPT_MODE, this.skey, this.iv);
		return cipher.doFinal(src);
	}

	public byte[] decryptBase64(String src)
			throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException,
			BadPaddingException, InvalidAlgorithmParameterException, UnsupportedEncodingException {
		return this.decrypt(Base64.decodeBase64(src.getBytes()));
	}
}
