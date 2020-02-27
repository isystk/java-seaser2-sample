/**
 * ColorSpace.java
 * 2010/02/08 iseyoshitaka
 * Copyright (c) 2010 TEAM LAB Inc. All rights reserved.
 */
package com.isystk.sample.common.image;

/**
 * EXIF: Color Space
 * 
 * @author iseyoshitaka
 */
public enum ColorSpace {
    sRGB, others, Uncalibrated;

    public static ColorSpace valueOf(int c) {
	switch (c) {
	case 1:
	    return sRGB;
	case 65535:
	    return Uncalibrated;
	default:
	    return others;
	}
    }
}
