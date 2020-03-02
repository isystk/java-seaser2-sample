<%@ page language="java" contentType="application/x-javascript; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">

window.Mynavi = window.Mynavi || {};

<%--
 JS内で使用する定数
 ここはEnumとか
--%>
Mynavi.constants = {

};

<%--
 設定値
--%>
Mynavi.constants.config = {

	people_user_categpry_image_maxlength : "${config:string(AppConfigNames.PEOPLE_USER_CATEGORY_IMAGE_MAXLENGTH.key)}"

};

<%--
 メッセージ
--%>
Mynavi.constants.message = {

    <%-- {0}が不正です。 --%>
    errors_invalid : "${message:string(AppMessageNames.ERRORS_INVALID.key)}",

    <%-- {0}の長さが最大値({1})を超えています。 --%>
    errors_maxlength : "${message:string(AppMessageNames.ERRORS_MAXLENGTH.key)}",

	<%-- {0}は数字の範囲が正しくありません。 --%>
	errors_numRange : "${message:string(AppMessageNames.ERRORS_NUMRANGE.key)}",

    <%-- {0}は日付でなければいけません。 --%>
    errors_date : "${message:string(AppMessageNames.ERRORS_DATE.key)}",

	<%-- {0}は日付の範囲が正しくありません。 --%>
	errors_daterange : "${message:string(AppMessageNames.ERRORS_DATERANGE.key)}",

};

<%--
 URL
--%>
Mynavi.constants.url = {

	<%-- サンプル。下記のように追記で。不要なら削除してください--%>
	domain : "${Url.frontDomain.path}",
	entry : "${Url.frontEntry.path}"

};


<%--
 環境
--%>
Mynavi.constants.env = {
	<%-- 本番環境 --%>
	isEnvProduct : "${cmn:isEnvProduct()}",
	<%-- ステージング環境 --%>
	isEnvSt : "${cmn:isEnvSt()}",
	<%-- ローカル環境 --%>
	isEnvUt : "${cmn:isEnvUt()}"
};


<%--
===========================================================
 【注意事項】

 ここに新しいオブジェクトを追加した場合は、common.js内に空のオブジェクトを定義しないと
 CMS側に影響が出てしまいますのでご注意くださいませ。

===========================================================
--%>


</script>