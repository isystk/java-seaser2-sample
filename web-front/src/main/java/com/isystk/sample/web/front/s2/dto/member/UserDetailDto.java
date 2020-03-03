package com.isystk.sample.web.front.s2.dto.member;

import java.io.Serializable;
import java.util.Date;

/**
 * 会員詳細を保持するDto
 * 
 * @author uedakeita
 */
public class UserDetailDto implements Serializable {

	private static final long serialVersionUID = -2047500500807188379L;

	/** 会員ID */
	public Integer userId;

	/** マイポユーザID */
	public Integer mypoUserId;

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

	/** 挙式予定日 */
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

	/** 最終ログイン日時 */
	public Date lastLoginTime;

	/** ステータス */
	public Integer status;

	/** ステータス更新日時 */
	public Date statusUpdateTime;

	/** 退会者フラグ */
	public Boolean withdrawFlg;

	/** 退会日時 */
	public Date withdrawTime;

	/** Mypo OpenIDURL */
	public String mypoOpenIdUrl;

	/** 登録日時 */
	public Date registTime;

	/** 更新日時 */
	public Date updateTime;

	/** 楽観チェック用バージョン */
	public Long version;

	/** 挙式スタイルリスト */
	public Integer[] ceremonyStyleList;

}
