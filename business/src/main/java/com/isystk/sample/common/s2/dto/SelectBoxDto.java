package com.isystk.sample.common.s2.dto;

import java.io.Serializable;

/**
 * プルダウン用IDリストDTO
 *
 * @author iseyoshitaka
 */
public class SelectBoxDto implements Serializable  {

    private static final long serialVersionUID = 6083497996213396744L;

    /** id */
    public String id;

    /** value */
    public String value;

    /** default */
    public Boolean isDefault;
}
