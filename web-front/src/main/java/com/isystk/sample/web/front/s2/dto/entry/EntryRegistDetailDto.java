package com.isystk.sample.web.front.s2.dto.entry;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 会員登録詳細DTO
 * 
 * @author iseyoshitaka
 */
public class EntryRegistDetailDto {

    /** メールアドレス */
    public String mail;

    /** パスワード */
    public byte[] password;

    /** 姓 */
    public String familyName;

    /** 名 */
    public String name;

    /** 姓（カナ） */
    public String familyNameKana;

    /** 名（カナ） */
    public String nameKana;

    /** 郵便番号 */
    public String zip;

    /** 都道府県 */
    public Integer prefectureId;

    /** 市区町村 */
    public String area;

    /** 町名番地 */
    public String address;

    /** 建物名 */
    public String building;

    /** 電話番号 */
    public String tel;

    /** 性別 */
    public Integer sex;

    /** 生年月日 */
    public Date birthday;

    /** 配偶者姓 */
    public String partnerFamilyName;

    /** 配偶者名 */
    public String partnerName;

    /** 配偶者姓（カナ） */
    public String partnerFamilyNameKana;

    /** 配偶者名（カナ） */
    public String partnerNameKana;

    /** 挙式予定日 */
    public String ceremonyDateText;

    /** 挙式予定日未定フラグ */
    public boolean ceremonyDateUnfixFlg;

    /** 挙式人数 */
    public Integer ceremonyCnt;

    /** 挙式予算 */
    public Integer ceremonyPrice;

    /** 引越し検討時期 */
    public Date moveDate;

    /** 引越し検討未定フラグ */
    public Boolean moveDateUnfixFlg;

    /** メルマガ受信フラグ */
    public Boolean mailMagazineFlg;

    /** Mypo OpenId Url */
    public String mypoOpenIdUrl;

    /** 挙式スタイル */
    public Integer[] ceremonyStyleList;

    /** 最終ログイン日時 */
    public Date lastLoginTime;

    /** 会員登録アンケートMap */
    public Map<String, String> questionMap = new HashMap<String, String>();

}
