package com.isystk.sample.common.s2.service;

import static com.isystk.sample.common.s2.entity.names.TUserNames.userId;
import static org.seasar.extension.jdbc.operation.Operations.asc;

import java.util.List;

import javax.annotation.Generated;

import org.apache.commons.collections.CollectionUtils;
import org.seasar.extension.jdbc.IterationCallback;
import org.seasar.extension.jdbc.IterationContext;
import org.seasar.extension.jdbc.where.SimpleWhere;

import com.google.common.collect.Lists;
import com.isystk.sample.common.constants.Flg;
import com.isystk.sample.common.constants.column.UserStatus;
import com.isystk.sample.common.s2.entity.TUser;
import com.isystk.sample.common.s2.entity.names.TUserNames;
import com.isystk.sample.common.util.CipherUtils;

/**
 * {@link TUser}のサービスクラスです。
 *
 */
@Generated(value = { "S2JDBC-Gen 2.4.44", "org.seasar.extension.jdbc.gen.internal.model.ServiceModelFactoryImpl" })
public class TUserService extends AbstractService<TUser> {

	/**
	 * 識別子でエンティティを検索します。
	 *
	 * @param userId
	 *            識別子
	 * @return エンティティ
	 */
	public TUser findById(Integer userId) {
		return select().id(userId).getSingleResult();
	}

	/**
	 * 識別子で排他制御を行います。
	 *
	 * @param userId
	 *            識別子
	 * @return エンティティ
	 */
	public TUser forUpdate(Integer userId) {
		return select().forUpdate().id(userId).getSingleResult();
	}

	/**
	 * 識別子で排他制御を行います。
	 *
	 * @param userIdList
	 *            識別子
	 * @return エンティティのリスト
	 */
	public List<TUser> forUpdate(List<Integer> userIdList) {
		if (CollectionUtils.isEmpty(userIdList)) {
			return Lists.newArrayList();
		}
		return select().forUpdate().where(new SimpleWhere().in(userId(), userIdList)).getResultList();
	}

	/**
	 * 識別子の昇順ですべてのエンティティを検索します。
	 *
	 * @return エンティティのリスト
	 */
	public List<TUser> findAllOrderById() {
		return select().orderBy(asc(userId())).getResultList();
	}

	/**
	 * 有効な状態の会員IDリストを取得します。
	 *
	 * @return
	 */
	public List<Integer> findEnableUserIdList() {
		return select().where(new SimpleWhere().eq(TUserNames.status(), UserStatus.VALID.getCode())
				.eq(TUserNames.deleteFlg(), Flg.OFF.getCode())).iterate(new IterationCallback<TUser, List<Integer>>() {
					List<Integer> idList = Lists.newArrayList();

					@Override
					public List<Integer> iterate(TUser entity, IterationContext context) {
						if (entity != null) {
							idList.add(entity.userId);
						}
						return idList;
					}
				});
	}

	/**
	 * 引数で指定したIDの会員リストを取得します。
	 *
	 * @return
	 */
	public List<TUser> findUserListByUserId(List<Integer> userIdList) {
		if (CollectionUtils.isEmpty(userIdList)) {
			return Lists.newArrayList();
		}
		return select().where(new SimpleWhere().in(TUserNames.userId(), userIdList)).getResultList();
	}

	/**
	 * メールアドレスに一致する仮登録ユーザーを取得します。
	 *
	 * @param mail
	 *            メールアドレス
	 * @return エンティティ
	 */
	public List<TUser> findTemporaryUserByMail(String mail) {
		return select().where(
				new SimpleWhere().eq(TUserNames.mail(), mail).eq(TUserNames.status(), UserStatus.TEMPORARY.getCode()))
				.getResultList();
	}

	/**
	 * メールアドレスに一致するレコードが存在するかどうか
	 *
	 * @param pcMail
	 *            PCメールアドレス
	 * @return 存在する場合はtrue・存在しない場合はfalse
	 */
	public TUser findByMail(String pcMail) {
		return select().where(new SimpleWhere().eq(TUserNames.mail(), pcMail).in(TUserNames.status(),
				UserStatus.VALID.getCode(), UserStatus.INVALID.getCode())).getSingleResult();
	}

	/**
	 * ユーザ名（メールアドレス）とパスワードからTUserを検索し、正しい場合には、そのTUserを返し、 正しくない場合にはNullを返す。
	 *
	 * @param loginUserName
	 *            ログインユーザ名（メールアドレス）
	 * @param loginUserPassword
	 *            ログインパスワード
	 * @return 正しい場合には、そのTUserを返す。正しくない場合にはNullを返す。
	 */
	public TUser findByMailAndPassword(String loginUserName, String loginUserPassword) {
		return select().where(new SimpleWhere().eq(TUserNames.mail(), loginUserName)
				.eq(TUserNames.password(), CipherUtils.sha1(loginUserPassword))
				.eq(TUserNames.status(), UserStatus.VALID.getCode())).getSingleResult();
	}

}