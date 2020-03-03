package com.isystk.sample.web.front.s2.form.member.post;

import java.io.Serializable;
import org.apache.struts.upload.FormFile;

/**
 * 先輩カップルの共通処理アクションアクションフォーム
 * 
 * @author iseyoshitaka
 */
public class PostIndexForm implements Serializable {

	private static final long serialVersionUID = 1L;

	/** アップロードする画像ファイル */
	public FormFile imageFile;

}