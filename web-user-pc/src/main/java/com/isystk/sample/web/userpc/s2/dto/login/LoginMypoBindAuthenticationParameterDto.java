package com.isystk.sample.web.userpc.s2.dto.login;

import java.io.Serializable;

/**
 * マイポのヒモ付けを行う画面へ渡すパラメータ用のDTO
 * 
 * @author yamashitatomoanri
 */
public class LoginMypoBindAuthenticationParameterDto implements Serializable {

    private static final long serialVersionUID = 4862714184846911352L;

    /** OpenIdのURL */
    public String openidUrl;

    /** OpenIdのEmail */
    public String email;

}
