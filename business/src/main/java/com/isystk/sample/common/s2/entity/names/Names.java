package com.isystk.sample.common.s2.entity.names;

import com.isystk.sample.common.s2.entity.MPostTag;
import com.isystk.sample.common.s2.entity.TImage;
import com.isystk.sample.common.s2.entity.TPost;
import com.isystk.sample.common.s2.entity.TPostImage;
import com.isystk.sample.common.s2.entity.TPostTag;
import com.isystk.sample.common.s2.entity.TUser;
import com.isystk.sample.common.s2.entity.TUserOnetimePass;
import com.isystk.sample.common.s2.entity.TUserOnetimeValid;
import com.isystk.sample.common.s2.entity.names.MPostTagNames._MPostTagNames;
import com.isystk.sample.common.s2.entity.names.TImageNames._TImageNames;
import com.isystk.sample.common.s2.entity.names.TPostImageNames._TPostImageNames;
import com.isystk.sample.common.s2.entity.names.TPostNames._TPostNames;
import com.isystk.sample.common.s2.entity.names.TPostTagNames._TPostTagNames;
import com.isystk.sample.common.s2.entity.names.TUserNames._TUserNames;
import com.isystk.sample.common.s2.entity.names.TUserOnetimePassNames._TUserOnetimePassNames;
import com.isystk.sample.common.s2.entity.names.TUserOnetimeValidNames._TUserOnetimeValidNames;
import javax.annotation.Generated;

/**
 * 名前クラスの集約です。
 * 
 */
@Generated(value = { "S2JDBC-Gen 2.4.44",
		"org.seasar.extension.jdbc.gen.internal.model.NamesAggregateModelFactoryImpl" })
public class Names {

	/**
	 * {@link MPostTag}の名前クラスを返します。
	 * 
	 * @return MPostTagの名前クラス
	 */
	public static _MPostTagNames mPostTag() {
		return new _MPostTagNames();
	}

	/**
	 * {@link TImage}の名前クラスを返します。
	 * 
	 * @return TImageの名前クラス
	 */
	public static _TImageNames tImage() {
		return new _TImageNames();
	}

	/**
	 * {@link TPost}の名前クラスを返します。
	 * 
	 * @return TPostの名前クラス
	 */
	public static _TPostNames tPost() {
		return new _TPostNames();
	}

	/**
	 * {@link TPostImage}の名前クラスを返します。
	 * 
	 * @return TPostImageの名前クラス
	 */
	public static _TPostImageNames tPostImage() {
		return new _TPostImageNames();
	}

	/**
	 * {@link TPostTag}の名前クラスを返します。
	 * 
	 * @return TPostTagの名前クラス
	 */
	public static _TPostTagNames tPostTag() {
		return new _TPostTagNames();
	}

	/**
	 * {@link TUser}の名前クラスを返します。
	 * 
	 * @return TUserの名前クラス
	 */
	public static _TUserNames tUser() {
		return new _TUserNames();
	}

	/**
	 * {@link TUserOnetimePass}の名前クラスを返します。
	 * 
	 * @return TUserOnetimePassの名前クラス
	 */
	public static _TUserOnetimePassNames tUserOnetimePass() {
		return new _TUserOnetimePassNames();
	}

	/**
	 * {@link TUserOnetimeValid}の名前クラスを返します。
	 * 
	 * @return TUserOnetimeValidの名前クラス
	 */
	public static _TUserOnetimeValidNames tUserOnetimeValid() {
		return new _TUserOnetimeValidNames();
	}
}