package com.isystk.sample.web.userpc.s2.action.member.post;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.apache.struts.action.ActionMessages;
import org.seasar.extension.jdbc.exception.SOptimisticLockException;
import org.seasar.struts.annotation.ActionForm;
import org.seasar.struts.annotation.Execute;
import org.seasar.struts.exception.ActionMessagesException;
import org.seasar.struts.util.RequestUtil;
import org.seasar.struts.util.ResponseUtil;

import com.google.common.collect.Lists;
import com.isystk.sample.common.config.AppMessageNames;
import com.isystk.sample.common.s2.entity.TPost;
import com.isystk.sample.common.s2.entity.TPostImage;
import com.isystk.sample.common.s2.entity.TPostTag;
import com.isystk.sample.common.s2.service.TPostService;
import com.isystk.sample.common.util.BeanCopyUtil;
import com.isystk.sample.common.util.NumberUtil;
import com.isystk.sample.common.util.StringUtils;
import com.isystk.sample.web.common.annotation.LoginCheck;
import com.isystk.sample.web.common.annotation.NoAllowDirectAccessCheck;
import com.isystk.sample.web.common.annotation.SSL;
import com.isystk.sample.web.common.sastruts.token.TokenCheck;
import com.isystk.sample.web.common.sastruts.token.TokenSet;
import com.isystk.sample.web.common.util.ValidateUtil;
import com.isystk.sample.web.userpc.s2.dto.member.post.PostUserDetailDto;
import com.isystk.sample.web.userpc.s2.form.member.post.PostRegistForm;
import com.isystk.sample.web.userpc.s2.logic.MemberPostLogic;

import net.sf.json.JSONObject;

/**
 * 投稿の登録アクション
 *
 * @author iseyoshitaka
 */
@SSL
@LoginCheck
public class RegistAction {

	@Resource
	@ActionForm
	public PostRegistForm postRegistForm;

	/** 投稿のロジック */
	@Resource
	protected MemberPostLogic memberPostLogic;

	/** 投稿サービス */
	@Resource
	protected TPostService tPostService;

	/** 投稿詳細 DTO */
	public PostUserDetailDto detailDto;

	/** プレビュー表示フラグ */
	public boolean isPreview = false;

	/**
	 * 初期表示時
	 */
	@TokenSet
	@Execute(validator = false)
	public String index() {

		// 論理チェック
		chkLogical();

		return showInput();
	}

	/**
	 * 論理チェック
	 */
	private void chkLogical() {
	}

	/**
	 * 基本情報入力①画面を表示
	 */
	@NoAllowDirectAccessCheck
	@Execute(validator = false)
	public String showInput() {

		detailDto = transferToEditDto();

		return "regist_input.jsp";
	}

	/**
	 * 「確認画面へ進む」ボタン押下時
	 */
	@Execute(validate = "validateConfirm", input = "showInput")
	public String confirm() {
		// 論理チェック
		chkLogical();

		return showConfirm();
	}

	/**
	 * 確認画面を表示
	 */
	@NoAllowDirectAccessCheck
	@Execute(validator = false)
	public String showConfirm() {

		detailDto = transferToEditDto();

		return "regist_confirm.jsp";
	}

    /**
     * 「前の画面へ戻る」ボタン押下時
     */
    @Execute(validator = false)
    public String backFrom() {
	    return showInput();
    }

	/**
	 * 確認画面から「投稿する」ボタン押下時
	 */
	@TokenCheck
	@Execute(validate = "validateUpdate", input = "showConfirm")
	public String update() {
		// 論理チェックを実施
		chkLogical();

		// アクションフォームの変更内容を詳細Dtoに転送
		PostUserDetailDto detailDto = transferToEditDto();

		try {
			// 更新実行
			memberPostLogic.regist(detailDto);
		} catch (SOptimisticLockException e) {
			// 楽観的ロックにより他のユーザーの更新を検出した場合の例外
			throw new ActionMessagesException(AppMessageNames.ERRORS_OPTIMISTICLOCK.key);
		}

		return showComplete();
	}

	/**
	 * 完了画面を表示
	 */
	@NoAllowDirectAccessCheck
	@Execute(validator = false)
	public String showComplete() {
		return "regist_complete.jsp";
	}

	/**
	 * プレビュー
	 */
	@Execute(validate = "validatePreview", input = "showInput")
	public String preview() {
		// 論理チェック
		this.chkLogical();

		// プレビュー表示フラグ
		isPreview = true;

		// 入力画面を表示する
		return showInput();
	}

	/**
	 * アクションフォームの内容をDtoに転送する
	 *
	 */
	private PostUserDetailDto transferToEditDto() {

		final Integer postId = NumberUtil.toInteger(postRegistForm.postId);

		TPost tPost = tPostService.findPostListByPostId(postId);
		if (tPost != null) {
			BeanCopyUtil.copy(postRegistForm, tPost).excludes("postImageId", "postTagId").execute();
		} else {
			tPost = BeanCopyUtil.createAndCopy(TPost.class, postRegistForm).excludes("postImageId", "postTagId")
					.execute();
		}

		// 投稿画像
		if (postRegistForm.postImageId != null) {
			tPost.TPostImageList = Lists.newArrayList();
			tPost.TPostImageList.addAll(Lists.newArrayList(CollectionUtils.collect(
					Lists.newArrayList(NumberUtil.toIntegerArray(postRegistForm.postImageId)), new Transformer() {
						public Object transform(Object o) {
							TPostImage dto = new TPostImage();
							dto.postId = postId;
							dto.imageId = ((Integer) o);
							return dto;
						}
					})));
		}

		// タグ
		if (postRegistForm.postTagId != null) {
			tPost.TPostTagList = Lists.newArrayList();
			tPost.TPostTagList.addAll(Lists.newArrayList(CollectionUtils.collect(
					Lists.newArrayList(NumberUtil.toIntegerArray(postRegistForm.postTagId)), new Transformer() {
						public Object transform(Object o) {
							TPostTag dto = new TPostTag();
							dto.postId = postId;
							dto.postTagId = ((Integer) o);
							return dto;
						}
					})));
		}

		// 変更後のデータ
		PostUserDetailDto detailDto = memberPostLogic.findPostDetailUser(tPost);

		return detailDto;
	}

    /**
     * テキストボックスの入力値をリアルタイムにチェックする(Ajaxトリガーで実行)
     *
     * @return null
     */
    @SSL
    @Execute(validator = false)
    public String callRealtimeCheck() {

	String propertyName = StringUtils.defaultValue((String) RequestUtil.getRequest().getParameter("propertyName"), "");
	postRegistForm.targetName = propertyName;

	// JSONオブジェクトを生成する
	JSONObject json = new JSONObject();

	// validateチェック
	ActionMessages messages = postRegistForm.validateInput();

	// ハッシュコード値よりメッセージ内容を取得
	List<String> messageList = ValidateUtil.getMessageStrings(messages);

	json.element("message", messageList);
	json.element("result", (0 != messageList.size()));

	ResponseUtil.write(json.toString(), "application/json");
	return null;

    }

}
