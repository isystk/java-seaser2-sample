package com.isystk.sample.common.s2.dto;

/**
 * @author iseyoshitaka ログイン時にセッションに保持するユーザ情報のベースDto
 */
public class BaseUserDto {

    public static final boolean USE_AUTO_LOGIN_FOR_DEBUG = false;
    public static final String AUTO_LOGIN_USER_NAME = "client1";
    public static final String AUTO_LOGIN_USER_PASSWORD = "pass";
    public static final String AUTO_LOGIN_TRADE_NAME_ID = "3"; //当該ユーザーが屋号IDを選択できる権限を持っていないとエラーとなりますのでご注意ください。
    public static final String AUTO_LOGIN_WEDDING_ID = "";
    public static final String AUTO_LOGIN_LOGIN_PATH = "/client/samplefunction/list";

    public static final String COMPONENT_NAME = "userDto";

}
