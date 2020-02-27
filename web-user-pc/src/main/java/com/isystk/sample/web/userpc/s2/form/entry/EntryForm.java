package com.isystk.sample.web.userpc.s2.form.entry;

import java.io.Serializable;

/**
 * 会員登録(予約フォームから受け継いだ会員情報)アクションフォーム
 * 
 * @author iseyoshitaka
 */
public class EntryForm implements Serializable {

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
    public String prefectureId;

    /** 市区町村 */
    public String area;

    /** 町名番地 */
    public String address;

    /** 建物名 */
    public String building;

    /** メールアドレス */
    public String mail;

    /** 電話番号 */
    public String tel;

    /** 性別 */
    public String sex;

    /** 生年月日 */
    public String birthday;

    /** 生年月日（年） */
    public String birthdayYyyy;
    /** 生年月日（月） */
    public String birthdayMm;
    /** 生年月日（日） */
    public String birthdayDd;

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

    /** 挙式予定日 未定 */
    public boolean ceremonyDateUnfixFlg;

    /** 挙式人数 */
    public String ceremonyCnt;

    /** 挙式予算 */
    public String ceremonyPrice;

    /** 挙式スタイル */
    public String[] ceremonyStyleList;

}