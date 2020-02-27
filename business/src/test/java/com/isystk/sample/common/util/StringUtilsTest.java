package com.isystk.sample.common.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.isystk.sample.common.util.StringUtils;

/**
 * {@link StringUtils}のテストクラスです。
 * 
 */
public class StringUtilsTest {

    @Test
    public void replaceControlCharacterTest() {
	
	assertEquals(null, StringUtils.replaceControlCharacter(null));
	
	//改行コード・タブ文字を除去
	assertEquals("\u0030\u0031\u0032\u0033", StringUtils.replaceControlCharacter("\u0030\u0031\t\r\n\u0032\u0033"));

	
	//※制御文字の範囲：U+0000～U+0008、U+000B～U+000C、U+000E～U+001F、U+FDD0～U+FDEF、U+FFFE、U+FFFF"
	assertEquals("\u0030\u0031\u0032\u0033", StringUtils.replaceControlCharacter("\u0030\u0031\u0000\u0032\u0033"));
	assertEquals("\u0030\u0031\u0032\u0033", StringUtils.replaceControlCharacter("\u0030\u0031\u0001\u0032\u0033"));
	assertEquals("\u0030\u0031\u0032\u0033", StringUtils.replaceControlCharacter("\u0030\u0031\u0007\u0032\u0033"));
	assertEquals("\u0030\u0031\u0032\u0033", StringUtils.replaceControlCharacter("\u0030\u0031\u0008\u0032\u0033"));
	assertEquals("\u0030\u0031\u0032\u0033", StringUtils.replaceControlCharacter("\u0030\u0031\u000b\u0032\u0033"));
	assertEquals("\u0030\u0031\u0032\u0033", StringUtils.replaceControlCharacter("\u0030\u0031\u000c\u0032\u0033"));
	assertEquals("\u0030\u0031\u0032\u0033", StringUtils.replaceControlCharacter("\u0030\u0031\u000e\u0032\u0033"));
	assertEquals("\u0030\u0031\u0032\u0033", StringUtils.replaceControlCharacter("\u0030\u0031\u000f\u0032\u0033"));
	assertEquals("\u0030\u0031\u0032\u0033", StringUtils.replaceControlCharacter("\u0030\u0031\u001e\u0032\u0033"));
	assertEquals("\u0030\u0031\u0032\u0033", StringUtils.replaceControlCharacter("\u0030\u0031\u001f\u0032\u0033"));
	assertEquals("\u0030\u0031\u0032\u0033", StringUtils.replaceControlCharacter("\u0030\u0031\ufdd0\u0032\u0033"));
	assertEquals("\u0030\u0031\u0032\u0033", StringUtils.replaceControlCharacter("\u0030\u0031\ufdd1\u0032\u0033"));
	assertEquals("\u0030\u0031\u0032\u0033", StringUtils.replaceControlCharacter("\u0030\u0031\ufdee\u0032\u0033"));
	assertEquals("\u0030\u0031\u0032\u0033", StringUtils.replaceControlCharacter("\u0030\u0031\ufdef\u0032\u0033"));
	assertEquals("\u0030\u0031\u0032\u0033", StringUtils.replaceControlCharacter("\u0030\u0031\ufffe\u0032\u0033"));
	assertEquals("\u0030\u0031\u0032\u0033", StringUtils.replaceControlCharacter("\u0030\u0031\uffff\u0032\u0033"));

    }
}