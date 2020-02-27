package com.isystk.sample.common.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * Tポイント暗号・複合化
 */
public class TpointCipher extends AESCipher {
    @Override
    public void setKey(byte[] key) throws NoSuchAlgorithmException {
	MessageDigest md = MessageDigest.getInstance("MD5");
	md.update(key);
	// 暗号化キー[X]をパラメタにMD5ハッシュ値を取得
	byte[] mdvalA = md.digest();
	md.update(mdvalA);
	// [A]をパラメタにMD5ハッシュ値を取得[B]
	byte[] mdvalB = md.digest();
	// [A]+[B]を変換後暗号化キー[Y]とする
	byte[] keyY = new byte[mdvalA.length + mdvalB.length];
	for (int i = 0; i < mdvalA.length; i++) {
	    keyY[i] = mdvalA[i];
	}
	for (int i = 0; i < mdvalB.length; i++) {
	    keyY[mdvalA.length + i] = mdvalB[i];
	}
	// [Y]を暗号化キーとして使用する
	super.setKey(keyY);
    }
    
    public String encryptBase64URLEncode(byte[] src) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, UnsupportedEncodingException {
	return URLEncoder.encode(super.encryptBase64(src), "Shift-JIS");
//	return super.encryptBase64(src);
    }
}
