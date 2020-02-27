package com.isystk.sample.common.image;

/**
 * @author iseyoshitaka
 * 
 */
public enum ImageSuffix {

    // アップロードされたそのままのファイル
    UNTOUCHED(1, "_ut"),

    // アップロードされたそのままの品質を維持したファイル(M)
    UNTOUCHED_M(2, "_ut_m"),

    // アップロードされたそのままの品質を維持したファイル(M)
    UNTOUCHED_S(3, "_ut_s"),

    // オリジナル
    ORIGINAL(11, ""),

    // オリジナルS 横幅160px
    S(12, "_s"),

    // オリジナルM 横幅360px
    M(13, "_m"),

    // オリジナルL 横幅720px
    L(14, "_l"),

    // 正方形
    SQUARE(21, "_square"),

    // 正方形(S)
    SQUARE_S(22, "_square_s"),

    // 正方形(M)
    SQUARE_M(23, "_square_m"),

    // 正方形(L) ※ _がないものより大きい
    SQUARE_L(24, "_square_l"),

    // 16対9
    HD(31, "_hd"),

    // 4対3
    SD(41, "_sd"),

    // 4対3(S)
    SD_S(42, "_sd_s"),

    // 4対3(L)
    SD_L(44, "_sd_l"),

    // 4対3(LL)
    SD_LL(45, "_sd_ll"),

    // 自由なアスペクト比でトリミングした画像
    FREE(91, "_free");

    private String suffix;

    private Integer code;

    ImageSuffix(Integer code, String suffix) {
	this.code = code;
	this.suffix = suffix;
    }

    public Integer getCode() {
	return code;
    }

    public void setCode(Integer code) {
	this.code = code;
    }

    public String getSuffix() {
	return suffix;
    }

    public void setSuffix(String suffix) {
	this.suffix = suffix;
    }

}
