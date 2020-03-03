package com.isystk.sample.common.s2.entity.names;

import com.isystk.sample.common.s2.entity.TUserOnetimeValid;
import com.isystk.sample.common.s2.entity.names.TUserNames._TUserNames;
import java.util.Date;
import javax.annotation.Generated;
import org.seasar.extension.jdbc.name.PropertyName;

/**
 * {@link TUserOnetimeValid}のプロパティ名の集合です。
 * 
 */
@Generated(value = { "S2JDBC-Gen 2.4.44", "org.seasar.extension.jdbc.gen.internal.model.NamesModelFactoryImpl" })
public class TUserOnetimeValidNames {

	/**
	 * userIdのプロパティ名を返します。
	 * 
	 * @return userIdのプロパティ名
	 */
	public static PropertyName<Integer> userId() {
		return new PropertyName<Integer>("userId");
	}

	/**
	 * onetimeKeyのプロパティ名を返します。
	 * 
	 * @return onetimeKeyのプロパティ名
	 */
	public static PropertyName<String> onetimeKey() {
		return new PropertyName<String>("onetimeKey");
	}

	/**
	 * onetimeValidTimeのプロパティ名を返します。
	 * 
	 * @return onetimeValidTimeのプロパティ名
	 */
	public static PropertyName<Date> onetimeValidTime() {
		return new PropertyName<Date>("onetimeValidTime");
	}

	/**
	 * TUserのプロパティ名を返します。
	 * 
	 * @return TUserのプロパティ名
	 */
	public static _TUserNames TUser() {
		return new _TUserNames("TUser");
	}

	/**
	 * @author S2JDBC-Gen
	 */
	public static class _TUserOnetimeValidNames extends PropertyName<TUserOnetimeValid> {

		/**
		 * インスタンスを構築します。
		 */
		public _TUserOnetimeValidNames() {
		}

		/**
		 * インスタンスを構築します。
		 * 
		 * @param name
		 *            名前
		 */
		public _TUserOnetimeValidNames(final String name) {
			super(name);
		}

		/**
		 * インスタンスを構築します。
		 * 
		 * @param parent
		 *            親
		 * @param name
		 *            名前
		 */
		public _TUserOnetimeValidNames(final PropertyName<?> parent, final String name) {
			super(parent, name);
		}

		/**
		 * userIdのプロパティ名を返します。
		 *
		 * @return userIdのプロパティ名
		 */
		public PropertyName<Integer> userId() {
			return new PropertyName<Integer>(this, "userId");
		}

		/**
		 * onetimeKeyのプロパティ名を返します。
		 *
		 * @return onetimeKeyのプロパティ名
		 */
		public PropertyName<String> onetimeKey() {
			return new PropertyName<String>(this, "onetimeKey");
		}

		/**
		 * onetimeValidTimeのプロパティ名を返します。
		 *
		 * @return onetimeValidTimeのプロパティ名
		 */
		public PropertyName<Date> onetimeValidTime() {
			return new PropertyName<Date>(this, "onetimeValidTime");
		}

		/**
		 * TUserのプロパティ名を返します。
		 * 
		 * @return TUserのプロパティ名
		 */
		public _TUserNames TUser() {
			return new _TUserNames(this, "TUser");
		}
	}
}