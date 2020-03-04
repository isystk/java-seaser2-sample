package com.isystk.sample.web.front.s2.action.member.post;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.struts.upload.FormFile;
import org.seasar.framework.env.Env;
import org.seasar.framework.util.StringUtil;
import org.seasar.struts.annotation.ActionForm;
import org.seasar.struts.annotation.Execute;
import org.seasar.struts.util.RequestUtil;
import org.seasar.struts.util.ResponseUtil;
import org.seasar.struts.util.S2ActionMappingUtil;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.isystk.sample.common.config.AppConfigNames;
import com.isystk.sample.common.config.AppMessageNames;
import com.isystk.sample.common.config.Config;
import com.isystk.sample.common.config.Message;
import com.isystk.sample.common.config.PeopleTagNgWord;
import com.isystk.sample.common.constants.Constants;
import com.isystk.sample.common.exception.SystemException;
import com.isystk.sample.common.image.ImageInfo;
import com.isystk.sample.common.image.ImageSuffix;
import com.isystk.sample.common.s2.logic.ImageCommonLogic;
import com.isystk.sample.common.util.StringUtils;
import com.isystk.sample.web.common.annotation.LoginCheck;
import com.isystk.sample.web.common.annotation.SSL;
import com.isystk.sample.web.common.util.CmnFunctions;
import com.isystk.sample.web.common.util.ValidateUtil;
import com.isystk.sample.web.front.s2.dto.member.post.PostUserTagDto;
import com.isystk.sample.web.front.s2.form.member.post.PostIndexForm;
import com.isystk.sample.web.front.s2.logic.MemberPostLogic;
import com.isystk.sample.web.front.s2.logic.PostCommonLogic;

import net.sf.json.JSONObject;

/**
 * 先輩カップルの共通処理アクション
 *
 * @author iseyoshitaka
 */
@SSL
@LoginCheck
public class IndexAction {

	@Resource
	@ActionForm
	public PostIndexForm postIndexForm;

	/** 投稿共通 ロジック */
	@Resource
	protected PostCommonLogic postCommonLogic;

	/** 画像 共通サービス */
	@Resource
	protected ImageCommonLogic imageCommonLogic;

	/** 投稿のロジック */
	@Resource
	protected MemberPostLogic memberPostLogic;

	/** 先輩カップルNGワードタグ一覧 */
	private static List<String> peopleTagNgWordList = PeopleTagNgWord.getAllList();

	/**
	 * 画像アップロード
	 *
	 * @return
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	@Execute(validator = false)
	public String imageUpload() throws FileNotFoundException, IOException {

		FormFile imageFile = postIndexForm.imageFile;

		if (!checkLogicalUploadImage(imageFile)) {
			return null;
		}

		ImageInfo image = new ImageInfo();
		if (!StringUtils.isBlankOrSpace(imageFile.getFileName())) {

			String fileName = imageFile.getFileName();

			// 画像ファイル情報を取得
			image = imageCommonLogic.createImage(imageFile.getInputStream(), fileName);

			Map<String, String> jsonMap = new HashMap<String, String>();
			jsonMap.put("imageId", Integer.toString(image.getImageId()));
			jsonMap.put("imagePath", image.getPath());
			jsonMap.put("imageFullpath",
					CmnFunctions.getImageUrl(Integer.toString(image.getImageId()), ImageSuffix.SQUARE.getSuffix()));
			JSONObject jsonObject = JSONObject.fromObject(jsonMap);

			// レスポンスに設定する
			ResponseUtil.write(jsonObject.toString(), "text/html");
		}
		imageFile.destroy();
		RequestUtil.getRequest().getSession()
				.removeAttribute(S2ActionMappingUtil.getActionMapping().getActionFormComponentDef().getComponentName());

		return null;
	}

	/**
	 * 画像アップロード時の論理チェック。
	 *
	 * @return
	 */
	private boolean checkLogicalUploadImage(FormFile imageFile) {

		List<String> messages = Lists.newArrayList();

		if (imageFile == null) {
			messages.add(Message.getString(AppMessageNames.ERRORS_INVALID.key, "画像ファイル"));
		} else if (!StringUtil.isEmpty(imageFile.getFileName())) {

			if (ValidateUtil.isCmykImageChk(imageFile)) {
				// 画像のCMYKチェック
				messages.add(Message.getString(AppMessageNames.ERRORS_UNSUPPRTIMAGEFILE.key, imageFile.getFileName()));

			} else {
				// 画像ファイルの拡張子チェック
				if (!ValidateUtil.isExtension(imageFile,
						Config.getStringArray(AppConfigNames.PEOPLE_USER_EXTENSION_TYPE))) {
					messages.add(Message.getString(AppMessageNames.ERRORS_ISEXTENSION.key, "画像ファイル"));
				}

				// 画像ファイルの最大サイズチェック (最大10Mバイト)
				if (!ValidateUtil.maxFileSize(imageFile,
						Config.getInteger(AppConfigNames.PEOPLE_USER_IMAGEFILE_MAXFILESIZE))) {
					messages.add(Message.getString(AppMessageNames.ERRORS_MAXFILESIZE.key, "画像ファイル", String
							.valueOf(Config.getInteger(AppConfigNames.PEOPLE_USER_IMAGEFILE_MAXFILESIZE) / 1000000)));
				}

				// 画像ファイルチェック
				if (!ValidateUtil.isImageFile(imageFile)) {
					messages.add(Message.getString(AppMessageNames.ERRORS_ISIMAGEFILE.key, "画像ファイル"));
				}

				try {
					// 画像の長さチェック
					if (!ValidateUtil.imageFileMaxImageLengthChk(imageFile.getInputStream(),
							Config.getInteger(AppConfigNames.PEOPLE_USER_IMAGEFILE_MAXIMAGELENGTH))) {
						messages.add(Message.getString(AppMessageNames.ERRORS_MAXIMAGELENGTH.key, "画像ファイル", Integer
								.toString(Config.getInteger(AppConfigNames.PEOPLE_USER_IMAGEFILE_MAXIMAGELENGTH))));
					}
				} catch (Exception e) {
					messages.add(
							Message.getString(AppMessageNames.ERRORS_UNSUPPRTIMAGEFILE.key, imageFile.getFileName()));
				}
			}

		}

		String[] errors = messages.toArray(new String[messages.size()]);

		if (0 < errors.length) {
			Map<String, String[]> jsonMap = new HashMap<String, String[]>();
			jsonMap.put("errors", errors);
			JSONObject jsonObject = JSONObject.fromObject(jsonMap);

			// レスポンスに設定する
			ResponseUtil.write(jsonObject.toString(), "text/html");
			return false;
		}
		return true;
	}

	/**
	 * タグの一覧を取得します。
	 */
	@Execute(validator = false)
	public String tagList() {

		Map<String, Object> responseDataMap = new LinkedHashMap<String, Object>();

		// すべてのタグの一覧を取得します。(承認済みのもののみ)
		List<PostUserTagDto> allTagList = postCommonLogic.getPostTagList();
		responseDataMap.put("allTagList", allTagList);

		// タグのNGワードリストを取得します。
		responseDataMap.put("ngWordList", PeopleTagNgWord.getAllList());

		JSONObject json = new JSONObject();
		json.element("success", true);
		json.element("message", Message.getString(AppMessageNames.ERRORS_SUCCESSFUL.key, "タグの一覧取得"));
		json.element("responseData", responseDataMap);

		// 開発環境では、インデントして返す
		if (Env.PRODUCT.equals(Env.getValue())) {
			ResponseUtil.write(json.toString(), Constants.MIME_TYPE_JSON);
		} else {
			ResponseUtil.write(json.toString(4), Constants.MIME_TYPE_JSON);
		}

		return null;
	}

	/**
	 * 追加するタグのIDを取得します。
	 */
	@Execute(validator = false)
	public String checkTagList() {

		Map<String, Object> responseDataMap = new LinkedHashMap<String, Object>();

		String[] selectTags = (String[]) RequestUtil.getRequest().getParameterValues("selectTags");
		if (selectTags == null || 0 == selectTags.length) {
			selectTags = new String[0];
		}

		// 同じタグがあったら除外
		final List<String> selectTagList = Lists.newArrayList(Sets.newHashSet(selectTags));

		// タグの入力チェック
		{
			List<String> errorMessages = Lists.newArrayList();
			if (!CollectionUtils.isEmpty(selectTagList)) {
				for (String selectTag : selectTagList) {
					if (StringUtils.isBlankOrSpace(selectTag)) {
						errorMessages.add("空のハッシュタグは設定できません。");
						break;
					}
					if (peopleTagNgWordList.contains(selectTag)) {
						errorMessages.add("ハッシュタグ[" + selectTag + "]はNGワードの為設定できません。");
						break;
					}
				}

				int TAG_MAX_COUNT = Config.getInteger(AppConfigNames.PEOPLE_USER_TAG_MAXLENGTH);
				if (TAG_MAX_COUNT < selectTagList.size()) {
					errorMessages.add("ハッシュタグの登録可能件数は、最大" + TAG_MAX_COUNT + "件です。");
				}
			}
			if (0 < errorMessages.size()) {
				JSONObject json = new JSONObject();
				json.element("success", false);
				json.element("message", StringUtils.join(errorMessages, ""));
				json.element("responseData", responseDataMap);
				ResponseUtil.write(json.toString(), Constants.MIME_TYPE_JSON);
				return null;
			}
		}

		// 全てのタグ (承認待ちのものも含める)
		final Map<String, Integer> allTagMap = postCommonLogic.getPostTagNameMap(null);

		// 新規に追加する予定のタグ
		List<String> firstTagList = Lists.newArrayList(selectTagList);
		CollectionUtils.filter(firstTagList, new Predicate() {
			public boolean evaluate(Object input) {
				return allTagMap.get((String) input) == null;
			}
		});

		// 新規に追加申請した予定のタグ
		List<PostUserTagDto> appendTagList = memberPostLogic.addPostTag(firstTagList);
		Map<String, Integer> appendTagMap = Maps.newHashMap();
		for (PostUserTagDto appendTag : appendTagList) {
			appendTagMap.put(appendTag.name, appendTag.postTagId);
		}

		// 返却用のデータリストを生成
		List<PostUserTagDto> setTagList = Lists.newArrayList();
		for (String selectTag : selectTagList) {
			PostUserTagDto dto = new PostUserTagDto();
			dto.name = selectTag;
			Integer tagId = allTagMap.get(selectTag);
			if (tagId == null) {
				tagId = appendTagMap.get(selectTag);
			}
			if (tagId == null) {
				tagId = appendTagMap.get(selectTag);
			}
			if (tagId == null) {
				throw new SystemException("想定外のエラーです。");
			}
			dto.postTagId = tagId;
			setTagList.add(dto);
		}
		responseDataMap.put("setTagList", setTagList);

		JSONObject json = new JSONObject();
		json.element("success", true);
		json.element("message", Message.getString(AppMessageNames.ERRORS_SUCCESSFUL.key, "タグの追加申請"));
		json.element("responseData", responseDataMap);

		// 開発環境では、インデントして返す
		if (Env.PRODUCT.equals(Env.getValue())) {
			ResponseUtil.write(json.toString(), Constants.MIME_TYPE_JSON);
		} else {
			ResponseUtil.write(json.toString(4), Constants.MIME_TYPE_JSON);
		}

		return null;
	}

}
