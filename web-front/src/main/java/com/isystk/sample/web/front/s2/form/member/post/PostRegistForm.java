package com.isystk.sample.web.front.s2.form.member.post;

import java.io.Serializable;
import java.util.List;

import org.apache.struts.action.ActionMessages;

import com.google.common.collect.Lists;
import com.isystk.sample.common.config.AppConfigNames;
import com.isystk.sample.common.config.AppMessageNames;
import com.isystk.sample.common.config.Config;
import com.isystk.sample.common.constants.entity.MaxLength;
import com.isystk.sample.common.util.StringUtils;
import com.isystk.sample.web.common.sastruts.URLParam;
import com.isystk.sample.web.common.util.ValidateUtil;

/**
 * 投稿の新規登録アクションフォーム
 *
 * @author iseyoshitaka
 */
public class PostRegistForm implements Serializable {

	private static final long serialVersionUID = 5558052156052077219L;

	/** 投稿ID */
	@URLParam
	public String postId;

	/** タイトル */
	@URLParam
	public String title;

	/** 本文 */
	@URLParam
	public String text;

	/** 投稿写真ID */
	@URLParam
	public String[] postImageId;

	/** 投稿タグID */
	@URLParam
	public String[] postTagId;

	/** バージョン */
	@URLParam
	public String version;

	/** リアルタイムチェック用 プロパティ名 */
	public String targetName;

	/**
	 * バリデーション
	 */
	public ActionMessages validateInput() {
		ActionMessages messages = new ActionMessages();

		// タイトル
		if (StringUtils.isBlankOrSpace(targetName) || "title".equals(targetName)) {
			ValidateUtil.requiredChk("title", title, messages, "タイトル");
			ValidateUtil.maxLengthChk("title", title, MaxLength.T_POST_TITLE, messages, "タイトル");
		}

		// 本文
		if (StringUtils.isBlankOrSpace(targetName) || "text".equals(targetName)) {
			ValidateUtil.requiredChk("text", text, messages, "本文");
			ValidateUtil.maxLengthChk("text", text, MaxLength.T_POST_TITLE, messages, "本文");
		}

		// 投稿画像
		if (StringUtils.isBlankOrSpace(targetName) || targetName.contains("postImageId")) {
			if (postImageId == null || postImageId.length == 0) {
				ValidateUtil.add("postImageId", messages, AppMessageNames.ERRORS_CHOSEONEORMORE.key, "投稿写真");
			}
			int CATEGORY_IMAGE_MAX_COUNT = Config.getInteger(AppConfigNames.PEOPLE_USER_CATEGORY_IMAGE_MAXLENGTH);
			if (postImageId == null || CATEGORY_IMAGE_MAX_COUNT < postImageId.length) {
				ValidateUtil.add("postImageId", messages, AppMessageNames.ERRORS_CANREGISTNUMBER.key, "投稿写真",
						"最大" + CATEGORY_IMAGE_MAX_COUNT);
			}
		}

		// タグ
		if (StringUtils.isBlankOrSpace(targetName) || targetName.contains("postTagId")) {
			int TAG_MAX_COUNT = Config.getInteger(AppConfigNames.PEOPLE_USER_TAG_MAXLENGTH);
			if (postTagId != null && 0 < postTagId.length) {
				List<String> includes = Lists.newArrayList();
				for (String id : postTagId) {
					if (includes.contains(id)) {
						ValidateUtil.add("postTagId", messages, AppMessageNames.ERRORS_DUPLICATE.key, id, "ハッシュタグ");
						break;
					}
					includes.add(id);
				}
				if (TAG_MAX_COUNT < postTagId.length) {
					ValidateUtil.add("postTagId", messages, AppMessageNames.ERRORS_CANREGISTNUMBER.key, "ハッシュタグ",
							"最大" + TAG_MAX_COUNT);
				}
			}
		}

		return messages;
	}

	/**
	 * バリデーション (コンテンツ一覧→確認)
	 */
	public ActionMessages validateConfirm() {
		ActionMessages messages = validateInput();

		return messages;
	}

	/**
	 * バリデーション (確認→完了)
	 */
	public ActionMessages validateUpdate() {
		ActionMessages messages = validateConfirm();
		return messages;
	}

	/**
	 * バリデーション (プレビュー)
	 */
	public ActionMessages validatePreview() {
		ActionMessages messages = new ActionMessages();

		return messages;
	}

}