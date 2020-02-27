/**
 * Orientation.java
 * 2010/02/08 iseyoshitaka
 * Copyright (c) 2010 TEAM LAB Inc. All rights reserved.
 */
package com.isystk.sample.common.image;

/** EXIF の回転情報 */
public enum Orientation {
    /** そのまま */
    HORIZONTAL(1, 0, false, false),
    /** 左右反転 */
    FLIP_HORIZONTAL(2, 0, true, false),
    /** 180度回転 */
    ROTATE_180(3, 180, false, false),
    /** 上下反転 */
    FLIP_VERTICAL(4, 0, false, true),
    /** 上下反転して時計回りに90度回転 */
    FLIP_VERTICAL_ROTATE_90(5, 90, false, true),
    /** 時計回りに270度回転 */
    ROTATE_270(6, 270, false, false),
    /** 上下反転して時計回りに270度回転 */
    FLIP_VERTICAL_ROTATE_270(7, 270, false, true),
    /** 時計回りに90度回転 */
    ROTATE_90(8, 90, false, false);

    private int val_, degree_;
    private boolean flipH_, flipV_;

    private Orientation(int val, int degree, boolean flipH, boolean flipV) {
	val_ = val;
	degree_ = degree;
	flipH_ = flipH;
	flipV_ = flipV;
    }

    public int value() {
	return val_;
    }

    public int degree() {
	return degree_;
    }

    public boolean flipHorizontal() {
	return flipH_;
    }

    public boolean flipVertical() {
	return flipV_;
    }

    public static Orientation find(int val) {
	return values()[val - 1];
    }
}
