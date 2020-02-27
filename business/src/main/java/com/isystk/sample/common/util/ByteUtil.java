package com.isystk.sample.common.util;

/**
 * @author ikedanatsuko
 *
 */
public class ByteUtil {

    /**
     * booleanからByteへ変換
     *
     * @param value 値
     * @return Byte
     */
    public static Byte toByte(boolean value) {

	if (value) {
	    return (byte) 1;
	} else {
	    return (byte) 0;
	}
    }
}
