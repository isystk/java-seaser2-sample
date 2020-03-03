package com.isystk.sample.common.config;

import javax.annotation.Generated;

/**
 * プロパティーファイルのキーを表すクラス。 <br />
 * src\main\resources\application-message.properties より生成
 * このクラスは自動生成されています。上書きされるので編集しないでください。
 */
@Generated(value = { "com.isystk.sample.common.gen.GenerateAppConfigTask" })
public enum AppMessageNames {

	/** &lt;div class="alertBox"&gt; */
	ERRORS_HEADER("errors.header"),
	/** &lt;/div&gt; */
	ERRORS_FOOTER("errors.footer"),
	/** &lt;p class='errortxt'&gt; */
	ERRORS_PREFIX("errors.prefix"),
	/** &lt;/p&gt; */
	ERRORS_SUFFIX("errors.suffix"),
	/** &lt;p class='error_txt'&gt;▲ */
	ERRORS_SPCOMMON_PREFIX("errors.spcommon.prefix"),
	/** &lt;/p&gt; */
	ERRORS_SPCOMMON_SUFFIX("errors.spcommon.suffix"),
	/** &lt;span class='error'&gt; */
	ERRORS_HEADER_ITEM("errors.header.item"),
	/** &lt;/span&gt; */
	ERRORS_FOOTER_ITEM("errors.footer.item"),
	/** {0} */
	ERRORS_ANY("errors.any"),
	/** {0}が不正です。 */
	ERRORS_INVALID("errors.invalid"),
	/** {0}の長さが最大値({1})を超えています。 */
	ERRORS_MAXLENGTH("errors.maxlength"),
	/** {0}の長さが({1})未満です。 */
	ERRORS_MINLENGTH("errors.minlength"),
	/** {0}のバイト長が最大値({1})を超えています。 */
	ERRORS_MAXBYTELENGTH("errors.maxbytelength"),
	/** {0}のバイト長が最小値({1})未満です。 */
	ERRORS_MINBYTELENGTH("errors.minbytelength"),
	/** {0}の値が最小値({1})未満です。 */
	ERRORS_MINVALUE("errors.minValue"),
	/** {0}の値が最大値({1})より大きいです。 */
	ERRORS_MAXVALUE("errors.maxValue"),
	/** {0}の値は、整数部({1})桁、小数部({2})桁を入力してください。 */
	ERRORS_DECIMALVALUE("errors.decimalValue"),
	/** {0}は{1}と{2}の間でなければいけません。 */
	ERRORS_RANGE("errors.range"),
	/** {0}を必ずご入力ください。 */
	ERRORS_REQUIRED("errors.required"),
	/** {0}はバイトでなければいけません。 */
	ERRORS_BYTE("errors.byte"),
	/** {0}は日付でなければいけません。 */
	ERRORS_DATE("errors.date"),
	/** {0}は日付(yyyymmdd)でなければいけません。 */
	ERRORS_MANUALENTRYDATE("errors.manualentrydate"),
	/** {0}は日付の範囲が正しくありません。 */
	ERRORS_DATERANGE("errors.dateRange"),
	/** {0}は時間の範囲が正しくありません。 */
	ERRORS_TIMERANGE("errors.timeRange"),
	/** {0}は現在日付より前の日付を入力してください。 */
	ERRORS_DATELTNOW("errors.dateLtNow"),
	/** {0}は現在日付以前の日付を入力してください。 */
	ERRORS_DATELENOW("errors.dateLeNow"),
	/** {0}は現在日付より後の日付を入力してください。 */
	ERRORS_DATEGTNOW("errors.dateGtNow"),
	/** {0}は現在日付以降の日付を入力してください。 */
	ERRORS_DATEGENOW("errors.dateGeNow"),
	/** {0}は{1}より前の時間を入力してください。 */
	ERRROS_DATEDEADLINE("errros.dateDeadline"),
	/** {0}は時分でなければいけません。 */
	ERRORS_TIME("errors.time"),
	/** {0}は倍精度実数でなければいけません。 */
	ERRORS_DOUBLE("errors.double"),
	/** {0}は単精度実数でなければいけません。 */
	ERRORS_FLOAT("errors.float"),
	/** {0}は整数でなければいけません。 */
	ERRORS_INTEGER("errors.integer"),
	/** {0}は長整数でなければいけません。 */
	ERRORS_LONG("errors.long"),
	/** {0}は短整数でなければいけません。 */
	ERRORS_SHORT("errors.short"),
	/** {0}はクレジットカード番号として不正です。 */
	ERRORS_CREDITCARD("errors.creditcard"),
	/** {0}は正しい形式で入力してください。 */
	ERRORS_EMAIL("errors.email"),
	/** {0}はURLとして不正です。 */
	ERRORS_URL("errors.url"),
	/** {0}は{1}以上でなければいけません。 */
	ERRORS_MORE("errors.more"),
	/** {0}は{1}以下でなければいけません。 */
	ERRORS_LESSEQUAL("errors.lessequal"),
	/** {0}は{1}未満でなければいけません。 */
	ERRORS_LESSTHAN("errors.lessthan"),
	/** パスワードがパスワード確認入力欄と違います。 */
	ERRORS_PASS("errors.pass"),
	/** メールアドレス、またはパスワードが違います。 */
	ERRORS_LOGIN("errors.login"),
	/** パスワードが違うか、かんたんログインが設定されていません。 */
	ERRORS_EASYLOGIN("errors.easylogin"),
	/** {0}が違います。 */
	ERRORS_SAYNO("errors.sayno"),
	/** {0}が存在しません。 */
	ERRORS_NOEXISTS("errors.noexists"),
	/** {0}は他のユーザーによって変更されています。再度検索を行ってください。 */
	ERRORS_UPDATED("errors.updated"),
	/** {0}は他のユーザーによって変更または削除されている可能性があります。再度検索を行ってください。 */
	ERRORS_UPDATED_SUPPOSE("errors.updated.suppose"),
	/** 他のユーザーによって既に変更されています。データは保存されませんので、メモ帳に保存するなどして編集したデータを回避してください。 */
	ERRORS_OPTIMISTICLOCK("errors.optimisticlock"),
	/** {0}は登録済みです。 */
	ERRORS_ALREADY("errors.already"),
	/** {0}は全角カタカナにしてください。 */
	ERRORS_ISKATAKANA("errors.iskatakana"),
	/** {0}は半角英数にしてください。 */
	ERRORS_ISHANKAKUEISU("errors.ishankakueisu"),
	/** {0}は半角数字にしてください。 */
	ERRORS_ISHANKAKUSU("errors.ishankakusu"),
	/** {0}は半角英数記号にしてください。 */
	ERRORS_ISHANKAKUEISUKIGOU("errors.ishankakueisukigou"),
	/** {0}は半角数字（ハイフン含む）10文字以上14文字以内で入力してください。 */
	ERRORS_ISTELEPHONENUMBER("errors.istelephonenumber"),
	/** {0}はhttpで始まる正しいURL形式でなければいけません。 */
	ERRORS_HTTPURLTYPE("errors.httpurltype"),
	/** {0}はhttpsで始まる正しいURL形式でなければいけません。 */
	ERRORS_HTTPSURLTYPE("errors.httpsurltype"),
	/** {0}はhttp/httpsで始まる正しいURL形式でなければいけません。 */
	ERRORS_HTTPORHTTPSURLTYPE("errors.httporhttpsurltype"),
	/** {0}は「{1}」のドメインのURLでなければいけません。 */
	ERRORS_HTTPURLTYPEWITHDOMAINNAME("errors.httpurltypewithdomainname"),
	/** {0}は0より大きい数値でなければいけません。 */
	ERRORS_POSITIVETYPE("errors.positivetype"),
	/** {0}はEnumで定義された値でなければいけません。 */
	ERRORS_ENUMIDTYPE("errors.enumidtype"),
	/** {0}は郵便番号(数字が七桁)でなければいけません。 */
	ERRORS_POSTCODETYPE("errors.postcodetype"),
	/** {0}は全角文字列のみで構成されていなければいけません。 */
	ERRORS_ZENKAKUTYPE("errors.zenkakutype"),
	/** {0}は全角カタカナで入力してください。 */
	ERRORS_ISFULLWIDTHKATAKANA("errors.isFullWidthKatakana"),
	/** {0}はスペース（全角、半角）を一つ以上含んでいなければいけません。 */
	ERRORS_INCLUDESPACETYPE("errors.includespacetype"),
	/** {0}のステータス更新で例外が発生しました。 */
	ERRORS_STATUSUPDATENG("errors.statusUpdateNg"),
	/** 検索条件を指定してください。 */
	ERRORS_NOTSPECIFIEDSEARCHCONDITION("errors.notSpecifiedSearchCondition"),
	/** {0}は{1}桁以内の数字で入力してください。 */
	ERRORS_NUMLENGTH("errors.numlength"),
	/** {0}を入力してください。 */
	ERRORS_NOTBLANK("errors.notBlank"),
	/** {0}は既に更新されています。 */
	ERRORS_ALREADYUPDATE("errors.alreadyUpdate"),
	/** ボタンが２回以上押されました。２回目以降のリクエストは処理されていません。 */
	ERRORS_TOKENINVALIDRUNTIMEEXCEPTION("errors.TokenInvalidRuntimeException"),
	/** 直接アクセスすることが許されないExecuteにアクセスしました。 */
	ERRORS_NOALLOWDIRECTACCESSEXCEPTION("errors.NoAllowDirectAccessException"),
	/** {0}は使用不可能な文字を使っています。 */
	ERRORS_ENABLECHAR("errors.enableChar"),
	/** {0}は半角文字にしてください。 */
	ERRORS_HALFCHAR("errors.halfChar"),
	/** {0}は半角英数、アンダースコア、ハイフン、ドットのみを使用するようにしてください。 */
	ERRORS_RESOURCEKEY("errors.resourceKey"),
	/** {0}は半角正の小数にしてください。 */
	ERRORS_HALFPOSITIVEDECIMAL("errors.halfPositiveDecimal"),
	/** {0}は半角の小数にしてください。 */
	ERRORS_HALFDECIMAL("errors.halfDecimal"),
	/** {0}は正しい形式で入力してください。 */
	ERRORS_PHONE("errors.phone"),
	/** {0}は{1}から{2}文字にしてください。 */
	ERRORS_LENGTH("errors.length"),
	/** {0}は1以上の半角数字で入力してください。 */
	ERRORS_HALFPOSITIVEINTEGER("errors.halfPositiveInteger"),
	/** {0}は0以上の半角数字で入力してください。 */
	ERRORS_HALFPOSITIVEINTEGERORZERO("errors.halfPositiveIntegerOrZero"),
	/** {0}は-90から90までの半角正の小数で入力してください。（最大20桁まで） */
	ERRORS_LATITUDE("errors.latitude"),
	/** {0}は-180から180までの半角正の小数で入力してください。（最大20桁まで） */
	ERRORS_LONGITUDE("errors.longitude"),
	/** {0}を選択してください。 */
	ERRORS_CHOSEONE("errors.choseOne"),
	/** {0}をひとつ以上選択してください。 */
	ERRORS_CHOSEONEORMORE("errors.choseOneOrMore"),
	/** {0}は拡張子が不正です。 */
	ERRORS_ISEXTENSION("errors.isExtension"),
	/** {0}のファイルサイズが最大値({1}MB)を超えています。 */
	ERRORS_MAXFILESIZE("errors.maxFileSize"),
	/** {0}は画像ファイルとして不正です。 */
	ERRORS_ISIMAGEFILE("errors.isImageFile"),
	/** アップロードされた{0}はフォーマット形式(MP4)が不正です。 */
	ERRORS_ISMOVIEFILE("errors.isMovieFile"),
	/** {0}はサポート対象外の画像です。 */
	ERRORS_UNSUPPRTIMAGEFILE("errors.unsupprtImageFile"),
	/** {0}は既に登録されています。 */
	ERRORS_REGISTRATION("errors.registration"),
	/** HTTPS接続でなければならない画面にHTTPで接続しました。 */
	ERRORS_INVALIDNOSSLACCESS("errors.invalidNoSslAccess"),
	/** HTTP接続でなければならない画面にHTTPSで接続しました。 */
	ERRORS_INVALIDSSLACCESS("errors.invalidSslAccess"),
	/** 権限のない画面にアクセスしました。 */
	ERRORS_INVALIDAUTHORITYACCESS("errors.invalidAuthorityAccess"),
	/** Webコール番号発番処理でエラーが発生しました。 */
	ERRORS_INVALIDWEBCALL("errors.invalidWebCall"),
	/** 選択された{0}へのアクセス権限がありません。 */
	ERRORS_NOTAUTHORITYACCESS("errors.notAuthorityAccess"),
	/** アクセス権限がありません。 */
	ERRORS_NOTAUTHORITYACCESSWITHOUTENTITY("errors.notAuthorityAccessWithoutEntity"),
	/** {0}を行う権限がありません。 */
	ERRORS_NOTAUTHORITYPROCESS("errors.notAuthorityProcess"),
	/** ログインせずに屋号選択の画面にアクセスしました。権限がありません。 */
	ERRORS_NOTAUTHORITYSELECTWEDDING("errors.notAuthoritySelectWedding"),
	/** 有効期限が切れているか、アクセス権がありません。再度アカウントを登録してください。 */
	ERRORS_NOTAUTHORITYVALIDONETIMEKEY("errors.notAuthorityValidOnetimeKey"),
	/** 権限のないユーザによるアクセス */
	ERRORS_NOTAUTHORITYUSERACCESS("errors.notAuthorityUserAccess"),
	/** 指定した{0}はすでに削除されています。 */
	ERRORS_DELETED("errors.deleted"),
	/** 指定した{0}は存在しません。 */
	ERRORS_NOTEXIST("errors.notExist"),
	/** 指定した{0}は表示できません。 */
	ERRORS_NOTSHOW("errors.notShow"),
	/** クライアントIDか屋号IDが指定されていないければなりません。 */
	ERRORS_NOTLOGIN("errors.notLogin"),
	/** {0}のご契約がないため、当機能をご利用できません。 */
	ERRORS_NOREQUEST("errors.noRequest"),
	/** {0}は、{1}対象ではありません。 */
	ERRORS_NOTARGET("errors.noTarget"),
	/** {0}ため、{1}できません。 */
	ERRORS_CANNOT("errors.cannot"),
	/** {0}の登録可能件数は、{1}件です。 */
	ERRORS_CANREGISTNUMBER("errors.canRegistNumber"),
	/** {0}は登録済みの{1}と重複しています。 */
	ERRORS_DUPLICATE("errors.duplicate"),
	/** {0}は重複しています。 */
	ERRORS_SIMPLE_DUPLICATE("errors.simple.duplicate"),
	/** {0}することはできないユーザステータスです。 */
	ERRORS_USERSTATUS("errors.userStatus"),
	/** {0}が登録されていません。{0}情報を登録後、再度実行してください。 */
	ERRORS_NOREGIST("errors.noRegist"),
	/** {0}に失敗しました。 */
	ERRORS_FAILURE("errors.failure"),
	/** 入力されたメールアドレスでのご登録はありません。 */
	ERRORS_NOTREGISTMAILADDRESS("errors.notRegistMailAddress"),
	/** メールアドレスがメールアドレス確認入力欄と違います。 */
	ERRORS_MAIL("errors.mail"),
	/** {0}は数字の範囲が正しくありません。 */
	ERRORS_NUMRANGE("errors.numRange"),
	/** {0}に成功しました。 */
	ERRORS_SUCCESSFUL("errors.successful"),
	/** {0}に失敗しました。 */
	ERRORS_FAILED("errors.failed"),
	/** {0}に入力できない文字が存在します。 */
	ERRORS_CANNOTCHAR("errors.canNotChar"),
	/** 該当するデータはありません。 */
	ERRORS_NODATA("errors.noData"),
	/** 「この式場の写真はまだ掲載されていません」 */
	ERRORS_NODATA_PHOTO("errors.noData.photo"),
	/** 検索条件を入力して、検索ボタンを押下してください。 */
	ERRORS_INPUTCONDITION("errors.inputCondition"),
	/** 検索結果が最大件数を超えました。条件を変えて検索を行ってください。 */
	ERRORS_COUNTOVER("errors.countOver"),
	/** 検索結果が最大件数を超えました。 */
	ERRORS_COUNTOVER_SHORT("errors.countOver.short"),
	/** メニューからお戻りください。 */
	ERRORS_BACKMENU("errors.backMenu"),
	/** {0}は選択できません。 */
	ERRORS_NOTSELECT("errors.notSelect"),
	/** {0}は、縦または横の長さが最大({1}px)を超えています。 */
	ERRORS_MAXIMAGELENGTH("errors.maxImageLength"),
	/** {0}は、アスペクト比が最大({1})を超えています。 */
	ERRORS_MAXASPECTRATE("errors.maxAspectRate"),
	/** メール送信時にエラーが発生しました。メールアドレスをご確認ください。 */
	ERRORS_SENDMAIL("errors.sendMail"),
	/** 入力された郵便番号に該当する住所が存在しません。 */
	ERRORS_ADDRESS_NOEXISTS("errors.address.noexists"),
	/** 条件に合う{0}が見つかりませんでした。条件を変えて検索してみてください */
	ERRORS_SEARCH_NODATA("errors.search.noData"),
	/** 条件に合う{0}が見つかりませんでした。 */
	ERRORS_SEARCH_NODATA_SHORT("errors.search.noData.short"),
	/** {0}は変更できません。 */
	ERRORS_NOTEDIT("errors.notEdit"),
	/** 外部ドメインへのリダイレクトは許可されません。 */
	ERRORS_OPENREDIRECTERROR("errors.openRedirectError"),
	/** ログインに関するメッセージ */
	ERRORS_LOGIN_INVALIDIDORPASSWORD("errors.login.invalididorpassword"),
	/** パスワードが間違っています。 */
	ERRORS_LOGIN_INVALIDPASSWORD("errors.login.invalidpassword");

	public String key;

	public String getKey() {
		return key;
	}

	AppMessageNames(String key) {
		this.key = key;
	}
}