package com.isystk.sample.common.util;

import static java.util.Calendar.MILLISECOND;
import static java.util.Calendar.MINUTE;
import static java.util.Calendar.SECOND;
import static javax.crypto.Cipher.DECRYPT_MODE;
import static javax.crypto.Cipher.ENCRYPT_MODE;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.isystk.sample.common.exception.FailsDecryptException;
import com.isystk.sample.common.exception.SystemException;

/**
 * 暗号関連
 * 
 * @author haseko
 */
public final class AgentCipher {
	
	/** ALGORITHM */
	private static final String ALGORITHM = "Blowfish";

	/** Logger */
    private static Log logger = LogFactory.getLog(AgentCipher.class);

	/** key format */
	private static final String KEY_FORMAT      = "yyyyMMddHH";

	/** デフォルトログインキー */
	private static final String KEY_ORG         = "xhLY1BiUkRlX1Z7m";

	/** 暗号化方式 */
	private static final String CIPHER_RULE     = ALGORITHM + "/CBC/PKCS5Padding";
	
	/** 接頭辞 */
	private static final String AGENT_LOGIN_KEY = "aWzx8n";
	
	/** 暗号化キー サーバー再起動ごとに変化 */
	private static final byte[] CIPHER_KEY;
	static {
		CIPHER_KEY = createRandomKey(16);
	}

    /**
     * 文字列のMD5ハッシュを生成します.
     * @param str 文字列
     * @return MD5ハッシュ
     */
    public static String md5(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            return changeByteToStrings(md.digest());
        } catch (NoSuchAlgorithmException e) {
	    throw new SystemException(e);
        }
    }

    /**
     * 文字列のSHA1ハッシュを生成します.
     * @param str 文字列
     * @return MD5ハッシュ
     */
    public static String sha1(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA1");
            md.update(str.getBytes());
            return changeByteToStrings(md.digest());
        } catch (NoSuchAlgorithmException e) {
	    throw new SystemException(e);
        }
    }
    
	/**
	 * ランダムな key を生成
	 * 
	 * @param length key の長さ
	 * @return ランダムな key
	 */
	public static String createRandomKeyString(int length) {

		try {
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
			char[] key = new char[length];
			for (int k = 0; k < key.length; k++) {
				switch (random.nextInt(3)) {
				case 0:
					key[k] = (char) (97 + random.nextInt(26));
					break;
				case 1:
					key[k] = (char) (65 + random.nextInt(26));
					break;
				case 2:
					key[k] = (char) (48 + random.nextInt(10));
					break;
				default:
					key[k] = 'a';
				}
			}
			return new String(key);
		} catch (Exception e) {
			return StringUtils.substr(KEY_ORG, 0, length);
		}
	}

	/**
	 * ランダムな key を生成
	 * 
	 * @param length key の長さ
	 * @return ランダムな key
	 */
	public static byte[] createRandomKey(int length) {
		byte[] generate;
		try {
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
			byte[] b = new byte[16];
			random.setSeed(random.generateSeed(length));
			random.nextBytes(b);
			generate = b;
		} catch (Exception e) {
			e.printStackTrace();
			generate = KEY_ORG.getBytes();
		}
		return generate;
	}

	/**
	 * インスタンス化禁止
	 */
	private AgentCipher() {
	}

	/**
	 * 暗号化
	 * 
	 * @param org オリジナル
	 * @param prefix 接頭語
	 * @return 暗号化文字列
	 */
	public static String encodeWithTimeLimit(String org, String prefix) {
		return encode(org, getTimeKey(prefix).getBytes());
	}

	/**
	 * 暗号化
	 * 
	 * @param org オリジナル
	 * @return 暗号化文字列
	 */
	public static String encodeWithTimeLimit(String org) {
		return encode(org, getTimeKey(AGENT_LOGIN_KEY).getBytes());
	}

	/**
	 * 分・秒・ミリ秒が 0 の現在日付
	 * 
	 * @param prefix 接頭語
	 * @return 分・秒・ミリ秒が 0 の現在日付
	 */
	private static String getTimeKey(String prefix) {
		Calendar now = Calendar.getInstance();
		now.set(MINUTE, 0);
		now.set(SECOND, 0);
		now.set(MILLISECOND, 0);
		if (prefix == null) {
			prefix = AGENT_LOGIN_KEY;
		}
		
		String onetimekey = 
			prefix + (new SimpleDateFormat(KEY_FORMAT)).format(now.getTime());
		
		if (logger.isDebugEnabled()) {
			logger.debug("onetimekey=" + onetimekey);
		}
		return onetimekey;
	}
	
	/**
	 * 暗号化
	 * 
	 * @param org オリジナル
	 * @return 暗号化文字列
	 */
	public static String encode(String org) {
		return encode(org, CIPHER_KEY);
	}

	/**
	 * 暗号化
	 * 
	 * @param org オリジナル
	 * @return 暗号化文字列
	 */
	public static String encodeByDefaultKey(String org) {
		return encode(org, KEY_ORG);
	}

	/**
	 * 暗号化
	 * 
	 * @param org オリジナル
	 * @param key 暗号化鍵
	 * @return 暗号化文字列
	 */
	public static String encode(String org, String key) {
		return encode(org, String.valueOf(key).getBytes());
	}

	/**
	 * 暗号化
	 * 
	 * @param org オリジナル
	 * @param key 暗号化鍵
	 * @return 暗号化文字列
	 */
	public static String encode(String org, byte[] key) {
		return encode(org.getBytes(), key);
	}
	
	/**
	 * encode.
	 * @param org byte[]
	 * @return String
	 */
	public static String encode(byte[] org) {
		return encode(org, CIPHER_KEY);
	}
	
	/**
	 * encode.
	 * @param org byte[]
	 * @param key byte[]
	 * @return String
	 */
	public static String encode(byte[] org, byte[] key) {
		try {
			if (org == null || org.length == 0) {
				logger.warn("暗号化対象文字列が存在しません");
				return "";
			} else if (key == null || key.length == 0) {
		throw new SystemException("暗号化キーが存在しません");
			}
			Cipher cipher = Cipher.getInstance(CIPHER_RULE);
			cipher.init(ENCRYPT_MODE, new SecretKeySpec(key, ALGORITHM));
			String encoded = changeByteToStrings(cipher.doFinal(org));
			return changeByteToStrings(cipher.getIV()) + encoded;
		} catch (Exception e) {
			logger.warn("暗号化失敗-->暗号化前[" + Arrays.toString(org) + "]");
	    throw new SystemException(e);
		}
	}
	
	/**
	 * 
	 * @param target
	 * @return
	 */
	private static String changeByteToStrings(byte[] target) {
		StringBuilder buider = new StringBuilder();
		for (byte b : target) {
			buider.append(String.format("%1$02x", (int) (b & 0xff)));
		}
		return buider.toString();
	}

	/**
	 * 復元
	 * 
	 * @param org オリジナル
	 * @return 復元した文字列
	 */
	public static String decodeWithTimeLimit(String org) {
		return decode(org, getTimeKey(AGENT_LOGIN_KEY));
	}

	/**
	 * 復元
	 * 
	 * @param org オリジナル
	 * @param prefix 接頭語
	 * @return 復元した文字列
	 */
	public static String decodeWithTimeLimit(String org, String prefix) {
		return decode(org, getTimeKey(prefix));
	}
	
	/**
	 * 復元
	 * 
	 * @param org オリジナル
	 * @return 復元した文字列
	 */
	public static String decodeByDefaultKey(String org) {
		return decode(org, KEY_ORG);
	}

	/**
	 * 復元
	 * 
	 * @param org オリジナル
	 * @return 復元した文字列
	 */
	public static String decode(String org) {
		return decode(org, CIPHER_KEY);
	}

	/**
	 * 復元
	 * 
	 * @param org オリジナル
	 * @param key 暗号化鍵
	 * @return 復元した文字列
	 */
	public static String decode(String org, String key) {
		return decode(org, String.valueOf(key).getBytes());
	}

	/**
	 * 復元
	 * 
	 * @param org オリジナル
	 * @param key 暗号化鍵
	 * @return 復元した文字列
	 */
	public static String decode(String org, byte[] key) {
		return new String(decodeByte(org, key));
	}
	
	/**
	 * decodeByte.
	 * @param org String
	 * @return byte[]
	 */
	public static byte[] decodeByte(String org) {
		return decodeByte(org, CIPHER_KEY);
	}
	
	/**
	 * decodeByte.
	 * @param org String
	 * @param key byte[]
	 * @return byte[]
	 */
	public static byte[] decodeByte(String org, byte[] key) {
		try {
			if (StringUtils.isNullOrEmpty(org)) {
		logger.debug("復号化対象文字列が存在しません[" + org + "]");
				return new byte[0];
			} else if (org.length() < 16) {
		throw new SystemException(
					"復号化対象文字列が短いです[" + org + "]");
			}
			byte[] iv = getOriginalByte(StringUtils.substr(org, 0, 16));
			byte[] by = getOriginalByte(StringUtils.substr(org, 16));
			IvParameterSpec dps = new IvParameterSpec(iv);
			Cipher cipher = Cipher.getInstance(CIPHER_RULE);
			cipher.init(DECRYPT_MODE, new SecretKeySpec(key, ALGORITHM), dps);
			return cipher.doFinal(by);
		} catch (Exception e) {
			logger.warn("復号化失敗-->復号化前[" 
					+ org + "], key[" + new String(key) + "]");
			throw new FailsDecryptException(e);
		}
	}
	
	/**
	 * 
	 * @param org
	 * @return
	 */
	private static byte[] getOriginalByte(String org) {
		int ivLen = org.length() / 2;
		byte[] orgByte = new byte[ivLen];
		for (int i = 0; i < ivLen; i++) {
			int index = i * 2;
			orgByte[i] = (byte) Integer.parseInt(
                    StringUtils.substr(org, index, index + 2), 16);
		}
		return orgByte;
	}
	
	/**
	 * 復号化＆デシリアライズ
	 * 
	 * @param blowfish 暗号化＆シリアライズ
	 * @return object デシリアライズされたオブジェクト
	 */
	public static Object decodeObject(String blowfish) {
		
		ObjectInputStream ois = null;
        try {
            ois = 
            	new ObjectInputStream(
            		new BufferedInputStream(
            			new ByteArrayInputStream(
                			AgentCipher.decodeByte(blowfish))));
            return ois.readObject();
        } catch (IOException e) {
	    throw new SystemException(e);
        } catch (ClassNotFoundException e) {
	    throw new SystemException(e);
        } finally {
            try {
                if (ois != null) {
                	ois.close();
                }
            } catch (Exception e) {
		throw new SystemException(e);
            }
        }
    }
	
	/**
	 * シリアライズ＆暗号化
	 * 
	 * @param object シリアライズ対象
	 * @return シリアライズ＆暗号化された文字列
	 */
	public static String encodeObject(Serializable object) {
		
		ObjectOutputStream oos = null;
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
        	oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            oos.flush();
            return AgentCipher.encode(baos.toByteArray());
        } catch (IOException e) {
	    throw new SystemException(e);
        } finally {
            if (oos != null) {
                try {
                	oos.close();
                } catch (IOException e) {
		    throw new SystemException(e);
                }
            }
        }
	}

	/**
	 * TEST
	 * 
	 * @param args nashi
	 */
	public static void main(String[] args) {
		
		String name = "a";
		
		String encoded = encode(name);
		String encoded2 = encode(name);
		String encoded3 = encode(name);
		String encoded4 = encode("ながたに");
		String encoded5 = encode("");
		
		String encoded6 = encodeByDefaultKey(name);
		
		String time = encodeWithTimeLimit("NAGATANI");
		
		System.out.println(encoded);
		System.out.println(encoded2);
		System.out.println(encoded3);
		System.out.println(encoded4);
		System.out.println(encoded5);
		
		System.out.println("default:" + encoded6);
		
		System.out.println(decode(encoded));
		System.out.println(decode(encoded2));
		System.out.println(decode(encoded3));
		System.out.println(decode(encoded4));
		System.out.println(decode(encoded5));
		
		System.out.println(decodeWithTimeLimit(time));
		
		System.out.println("default:" + decodeByDefaultKey(encoded6));
		
		System.out.println("default:" + decodeByDefaultKey("01234567890123456"));
		
		System.out.println("byte[0] :" + new String(new byte[0]));
		
		System.out.println(StringUtils.substr("0123456789012345", 0, 16));
		System.out.println(StringUtils.substr("0123456789012345", 16));
	}
}


