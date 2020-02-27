package com.isystk.sample.web.userpc.s2.logic;

import javax.annotation.Resource;

import org.seasar.framework.container.annotation.tiger.Component;

import com.isystk.sample.common.s2.entity.TUser;
import com.isystk.sample.common.s2.service.TUserService;
import com.isystk.sample.common.util.BeanCopyUtil;
import com.isystk.sample.web.userpc.s2.dto.member.UserDetailDto;

/**
 * 会員データ処理のロジックを実装します。
 *
 * @author uedakeita
 */
@Component
public class UserLogic {

	@Resource
	protected TUserService tUserService;

	/**
	 * 会員詳細DTOの取得
	 */
	public UserDetailDto getUserDetailDto(Integer userId) {

		// 会員を取得する
		TUser tUser = tUserService.findById(userId);

		// 取得した値を会員詳細DTOに詰替える
		UserDetailDto userDetailDto = new UserDetailDto();
		BeanCopyUtil.copy(tUser, userDetailDto).execute();

		return userDetailDto;

	}

	/**
	 * 識別子でエンティティを検索します。
	 *
	 * @param id
	 * @return エンティティ
	 */
	public TUser findById(int id) {
		return tUserService.findById(id);
	}

}
