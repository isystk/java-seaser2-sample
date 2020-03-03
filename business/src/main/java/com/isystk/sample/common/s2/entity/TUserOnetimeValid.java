package com.isystk.sample.common.s2.entity;

import java.io.Serializable;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * TUserOnetimeValidエンティティクラス
 * 
 */
@Entity
@Generated(value = { "S2JDBC-Gen 2.4.44", "org.seasar.extension.jdbc.gen.internal.model.EntityModelFactoryImpl" })
public class TUserOnetimeValid implements Serializable {

	private static final long serialVersionUID = 1L;

	/** userIdプロパティ */
	@Id
	@Column(precision = 10, nullable = false, unique = true)
	public Integer userId;

	/** onetimeKeyプロパティ */
	@Column(length = 32, nullable = false, unique = false)
	public String onetimeKey;

	/** onetimeValidTimeプロパティ */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, unique = false)
	public Date onetimeValidTime;

	/** TUser関連プロパティ */
	@OneToOne
	@JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
	public TUser TUser;

	/**
	 * userIdを返します。
	 * 
	 * @param userId
	 */
	public Integer getUserId() {
		return userId;
	}

	/**
	 * userIdを設定します。
	 * 
	 * @param userId
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	/**
	 * onetimeKeyを返します。
	 * 
	 * @param onetimeKey
	 */
	public String getOnetimeKey() {
		return onetimeKey;
	}

	/**
	 * onetimeKeyを設定します。
	 * 
	 * @param onetimeKey
	 */
	public void setOnetimeKey(String onetimeKey) {
		this.onetimeKey = onetimeKey;
	}

	/**
	 * onetimeValidTimeを返します。
	 * 
	 * @param onetimeValidTime
	 */
	public Date getOnetimeValidTime() {
		return onetimeValidTime;
	}

	/**
	 * onetimeValidTimeを設定します。
	 * 
	 * @param onetimeValidTime
	 */
	public void setOnetimeValidTime(Date onetimeValidTime) {
		this.onetimeValidTime = onetimeValidTime;
	}

	/**
	 * TUserを返します。
	 * 
	 * @param TUser
	 */
	public TUser getTUser() {
		return TUser;
	}

	/**
	 * TUserを設定します。
	 * 
	 * @param TUser
	 */
	public void setTUser(TUser TUser) {
		this.TUser = TUser;
	}
}