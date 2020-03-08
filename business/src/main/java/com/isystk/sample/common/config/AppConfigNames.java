package com.isystk.sample.common.config;

import javax.annotation.Generated;

/**
 * プロパティーファイルのキーを表すクラス。 <br />
 * src\main\resources\application-config.properties より生成
 * このクラスは自動生成されています。上書きされるので編集しないでください。
 */
@Generated(value = { "com.isystk.sample.common.gen.GenerateAppConfigTask" })
public enum AppConfigNames {

	/**
	 * ====================================================== = = = 全体 = = =
	 * ====================================================== システム環境
	 */
	ENV("env"),
	/** システム時間との時差(時) */
	SERVER_TIME_DIFFERENCE("server.time.difference"),
	/**
	 * --------------------------------- システム ---------------------------------
	 */
	SYSTEM_WORKDIR("system.workdir"),
	/** C:/isystk/docker-java/public/ */
	SYSTEM_HTDOCSDIR("system.htdocsdir"),
	/**
	 * --------------------------------- 画像 ---------------------------------
	 */
	IMAGE_DIR("image.dir"),
	/** jpg */
	IMAGE_EXTENSION("image.extension"),
	/** false */
	IMAGE_UPLOAD_CHMOD("image.upload.chmod"),
	/** localhost */
	FRONT_DOMAIN("front_domain"),
	/** localhost */
	IMG_DOMAIN("img_domain"),
	/**
	 * --------------------------------- プロトコル ---------------------------------
	 */
	HTTP_PROTCOL("http_protcol"),
	/** https: */
	HTTPS_PROTCOL("https_protcol"),
	/**
	 * --------------------------------- クッキー有効期限(JSesionID:秒)(86400 → 1日間有効)
	 * ---------------------------------
	 */
	COOKIE_EXPIRES("cookie.expires"),
	/**
	 * --------------------------------- メール送信用アドレス
	 * --------------------------------- システム(管理用)
	 */
	ADMIN_SYSTEM_MAIL("admin_system_mail"),
	/** エラーメール送信先 */
	ERRORS_MAIL_01("errors_mail_01"),
	/**
	 * --------------------------------- アップロード画像 ---------------------------------
	 */
	CLIENT_ALBUM_TAG_MAXSIZE("client_album_tag_maxsize"),
	/** JPG,JPEG */
	CLIENT_ALBUM_EXTENSION_TYPE("client_album_extension_type"),
	/** 10000000 */
	CLIENT_ALBUM_IMAGEFILE_MAXFILESIZE("client_album_imagefile_maxfilesize"),
	/** 1500 */
	CLIENT_ALBUM_IMAGEFILE_MAXUPCOUNT("client_album_imagefile_maxupcount"),
	/** 4000 */
	CLIENT_ALBUM_IMAGEFILE_MAXIMAGELENGTH("client_album_imagefile_maxImageLength"),
	/** 2 */
	CLIENT_ALBUM_IMAGEFILE_MAXASPECTRATE("client_album_imagefile_maxAspectRate"),
	/** 230 */
	CLIENT_ALBUM_IMAGEFILE_MAXIMAGEWIDTH_PCEDITORMAP("client_album_imagefile_maxImageWidth_pcEditorMap"),
	/** 195 */
	CLIENT_ALBUM_IMAGEFILE_MAXIMAGEHEIGHT_PCEDITORMAP("client_album_imagefile_maxImageHeight_pcEditorMap"),
	/** 600 */
	CLIENT_ALBUM_IMAGEFILE_MAXIMAGEWIDTH_SPEDITORMAP("client_album_imagefile_maxImageWidth_spEditorMap"),
	/** 500 */
	CLIENT_ALBUM_IMAGEFILE_MAXIMAGEHEIGHT_SPEDITORMAP("client_album_imagefile_maxImageHeight_spEditorMap"),
	/**
	 * --------------------------------- 投稿 --------------------------------- タグの上限数
	 */
	PEOPLE_USER_TAG_MAXLENGTH("people_user_tag_maxlength"),
	/** カテゴリーの上限数 */
	PEOPLE_USER_CATEGORY_MAXLENGTH("people_user_category_maxlength"),
	/** カテゴリー画像の上限数 */
	PEOPLE_USER_CATEGORY_IMAGE_MAXLENGTH("people_user_category_image_maxlength"),
	/** 投稿アップロード画像 */
	PEOPLE_USER_EXTENSION_TYPE("people_user_extension_type"),
	/** 10000000 */
	PEOPLE_USER_IMAGEFILE_MAXFILESIZE("people_user_imagefile_maxfilesize"),
	/** 4000 */
	PEOPLE_USER_IMAGEFILE_MAXIMAGELENGTH("people_user_imagefile_maxImageLength"),
	/**
	 * ====================================================== = = = サーチ = = =
	 * ====================================================== サーチmasterURL
	 */
	SOLR_MASTER_URL("solr_master_url"),
	/** サーチslaveURL */
	SOLR_SLAVE_URL("solr_slave_url");

	public String key;

	public String getKey() {
		return key;
	}

	AppConfigNames(String key) {
		this.key = key;
	}
}