package com.isystk.sample.web.front.s2.dto;

import java.io.Serializable;

import org.seasar.framework.container.SingletonS2Container;
import org.seasar.framework.container.annotation.tiger.Component;
import org.seasar.framework.container.annotation.tiger.InstanceType;

import com.isystk.sample.common.s2.dto.BaseUserDto;

/**
 * ログイン時にセッションに保持する管理者向けのDto
 */
@Component(instance = InstanceType.SESSION)
public class UserDto implements Serializable {

	private static final long serialVersionUID = 3465424473200044951L;

	public static final String SESSION_VALIDATION_COOKIE_NAME = "ls__";

	/** ユーザID */
	public Integer userId;

	/**
	 * ログイン済みのユーザ情報かどうかを検証する
	 *
	 * @return 有効な場合は{@code true}、そうでない場合は{@code false}
	 */
	public boolean hasLogined() {
		return userId != null;
	}

	// ------------------getter
	/* セッションオブジェクトのpublicフィールドにEL式でアクセスできないので、必要なものは getter を用意する */

	/**
	 * @return userId を取得します。
	 */
	public Integer getUserId() {
		return userId;
	}

	public static String getComponentName() {
		return BaseUserDto.COMPONENT_NAME;
	}

	/**
	 * 現在のログインユーザーを取得する。ログインしていない場合にはnullが返る。
	 *
	 * @return 現在のログインユーザ
	 */
	public static UserDto getUserDto() {
		UserDto result = SingletonS2Container.getComponent(UserDto.class);
		if (result.hasLogined() == false) {
			result = null;
		}

		return result;
	}

}
