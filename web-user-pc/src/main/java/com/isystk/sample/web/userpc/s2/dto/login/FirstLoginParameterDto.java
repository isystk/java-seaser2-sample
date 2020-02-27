package com.isystk.sample.web.userpc.s2.dto.login;

import java.io.Serializable;

/**
 * 初回ログイン画面へ渡すパラメータ用のDTO
 * 
 * @author yamashitatomoanri
 */
public class FirstLoginParameterDto implements Serializable {

    private static final long serialVersionUID = -6479516244328498592L;

    /** OpenIdのプロバイダ */
    public String openidProvidier;

    /** OpenIdのURL */
    public String openidUrl;

    /** OpenIdのEmail */
    public String email;
}
