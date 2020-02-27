package com.isystk.sample.web.userpc.s2.logic;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.seasar.framework.container.annotation.tiger.Component;

import com.google.common.collect.Lists;
import com.isystk.sample.common.constants.Prefecture;
import com.isystk.sample.common.s2.dto.SelectBoxDto;
import com.isystk.sample.common.s2.service.TImageService;
import com.isystk.sample.common.s2.service.TUserService;
import com.isystk.sample.common.util.DateUtils;
import com.isystk.sample.common.util.NumberUtil;
import com.isystk.sample.common.util.StringUtils;
import com.isystk.sample.web.userpc.constants.Constants;

/**
 * （フロントPC共通） ロジック
 *
 * @author hayashitakehiko
 */
@Component
public class WeddingCommonLogic {

	/** 画像テーブルのサービスクラス */
	@Resource
	protected TImageService tImageService;

	/** 会員のサービスクラス */
	@Resource
	protected TUserService tUserService;

	/**
	 * 都道府県存在チェック
	 *
	 * @param prefectureId
	 *            都道府県ID
	 * @return true：存在する都道府県ID、false:存在しない都道府県ID
	 */
	public boolean checkExistencePrefecture(String prefectureId) {

		// 空の場合は正常とし、確認を行わない
		if (StringUtils.isBlankOrSpace(prefectureId)) {
			return true;
		}

		// Integer型か確認する
		Integer intPreId = NumberUtil.toInteger(prefectureId, null);

		// Integer型でない場合、不正値とする
		if (null == intPreId) {
			return false;
		}

		// 都道府県エンティティリストを取得する
		List<Prefecture> entityList = Lists.newArrayList(Prefecture.ITEMS);

		// 引数の都道府県IDが存在するかチェックする
		for (Prefecture entity : entityList) {
			if (intPreId.equals(entity.getCode())) {
				// 存在する
				return true;
			}
		}

		// 存在しない
		return false;

	}

	/**
	 * 生年月日入力時に使用する西暦リストを取得する
	 *
	 * @param 選択中の値
	 * @return 4桁の西暦リスト（60年前～13年前）
	 */
	public List<SelectBoxDto> getBirthdayYyyyList(String selectVal) {

		// 戻り値
		List<SelectBoxDto> lst = new ArrayList<SelectBoxDto>();

		// 西暦MINを取得（※60年前）
		int yyyyMin = Integer.parseInt(DateUtils.toDateFormat(DateUtils.getNow()).substring(0, 4)) - 60;

		// 西暦MAXを取得（※13年前）
		int yyyyMax = Integer.parseInt(DateUtils.toDateFormat(DateUtils.getNow()).substring(0, 4)) - 13;

		// 西暦リストを生成
		for (int i = yyyyMin; i <= yyyyMax; i++) {
			SelectBoxDto dto = new SelectBoxDto();
			dto.id = String.valueOf(i);
			dto.value = String.valueOf(i);
			if (!StringUtils.isNullOrEmpty(selectVal)) {
				if (selectVal.equals(dto.id)) {
					dto.isDefault = true;
				} else {
					dto.isDefault = false;
				}
			} else {
				if (Constants.BIRTHDAY_DEFAULT_YEAR.equals(dto.id)) {
					dto.isDefault = true;
				} else {
					dto.isDefault = false;
				}
			}
			lst.add(dto);
		}
		return lst;
	}

	/**
	 * 生年月日入力時に使用する月リストを取得する
	 *
	 * @param 選択中の値
	 * @return 2桁の月リスト
	 */
	public List<SelectBoxDto> getBirthdayMmList(String selectVal) {

		// 戻り値
		List<SelectBoxDto> lst = new ArrayList<SelectBoxDto>();

		// 月リストを生成
		for (int i = 1; i <= 12; i++) {
			SelectBoxDto dto = new SelectBoxDto();
			dto.id = String.valueOf(i);
			dto.value = String.valueOf(i);
			if (!StringUtils.isNullOrEmpty(selectVal)) {
				if (selectVal.equals(dto.id)) {
					dto.isDefault = true;
				} else {
					dto.isDefault = false;
				}
			} else {
				dto.isDefault = false;
			}
			lst.add(dto);
		}
		return lst;
	}

	/**
	 * 生年月日入力時に使用する日リストを取得する
	 *
	 * @param 選択中の値
	 * @return 2桁の日リスト
	 */
	public List<SelectBoxDto> getBirthdayDdList(String selectVal) {

		// 戻り値
		List<SelectBoxDto> lst = new ArrayList<SelectBoxDto>();

		// 日リストを生成
		for (int i = 1; i <= 31; i++) {
			SelectBoxDto dto = new SelectBoxDto();
			dto.id = String.valueOf(i);
			dto.value = String.valueOf(i);
			if (!StringUtils.isNullOrEmpty(selectVal)) {
				if (selectVal.equals(dto.id)) {
					dto.isDefault = true;
				} else {
					dto.isDefault = false;
				}
			} else {
				dto.isDefault = false;
			}
			lst.add(dto);
		}
		return lst;
	}

}
