package com.isystk.sample.common.s2.entity.names;

import com.isystk.sample.common.s2.entity.TPost;
import com.isystk.sample.common.s2.entity.names.TPostImageNames._TPostImageNames;
import com.isystk.sample.common.s2.entity.names.TPostTagNames._TPostTagNames;
import com.isystk.sample.common.s2.entity.names.TUserNames._TUserNames;
import java.util.Date;
import javax.annotation.Generated;
import org.seasar.extension.jdbc.name.PropertyName;

/**
 * {@link TPost}のプロパティ名の集合です。
 * 
 */
@Generated(value = { "S2JDBC-Gen 2.4.44", "org.seasar.extension.jdbc.gen.internal.model.NamesModelFactoryImpl" })
public class TPostNames {

	/**
	 * postIdのプロパティ名を返します。
	 * 
	 * @return postIdのプロパティ名
	 */
	public static PropertyName<Integer> postId() {
		return new PropertyName<Integer>("postId");
	}

	/**
	 * userIdのプロパティ名を返します。
	 * 
	 * @return userIdのプロパティ名
	 */
	public static PropertyName<Integer> userId() {
		return new PropertyName<Integer>("userId");
	}

	/**
	 * titleのプロパティ名を返します。
	 * 
	 * @return titleのプロパティ名
	 */
	public static PropertyName<String> title() {
		return new PropertyName<String>("title");
	}

	/**
	 * textのプロパティ名を返します。
	 * 
	 * @return textのプロパティ名
	 */
	public static PropertyName<String> text() {
		return new PropertyName<String>("text");
	}

	/**
	 * registTimeのプロパティ名を返します。
	 * 
	 * @return registTimeのプロパティ名
	 */
	public static PropertyName<Date> registTime() {
		return new PropertyName<Date>("registTime");
	}

	/**
	 * updateTimeのプロパティ名を返します。
	 * 
	 * @return updateTimeのプロパティ名
	 */
	public static PropertyName<Date> updateTime() {
		return new PropertyName<Date>("updateTime");
	}

	/**
	 * deleteFlgのプロパティ名を返します。
	 * 
	 * @return deleteFlgのプロパティ名
	 */
	public static PropertyName<Boolean> deleteFlg() {
		return new PropertyName<Boolean>("deleteFlg");
	}

	/**
	 * versionのプロパティ名を返します。
	 * 
	 * @return versionのプロパティ名
	 */
	public static PropertyName<Long> version() {
		return new PropertyName<Long>("version");
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
	 * TPostImageListのプロパティ名を返します。
	 * 
	 * @return TPostImageListのプロパティ名
	 */
	public static _TPostImageNames TPostImageList() {
		return new _TPostImageNames("TPostImageList");
	}

	/**
	 * TPostTagListのプロパティ名を返します。
	 * 
	 * @return TPostTagListのプロパティ名
	 */
	public static _TPostTagNames TPostTagList() {
		return new _TPostTagNames("TPostTagList");
	}

	/**
	 * @author S2JDBC-Gen
	 */
	public static class _TPostNames extends PropertyName<TPost> {

		/**
		 * インスタンスを構築します。
		 */
		public _TPostNames() {
		}

		/**
		 * インスタンスを構築します。
		 * 
		 * @param name
		 *            名前
		 */
		public _TPostNames(final String name) {
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
		public _TPostNames(final PropertyName<?> parent, final String name) {
			super(parent, name);
		}

		/**
		 * postIdのプロパティ名を返します。
		 *
		 * @return postIdのプロパティ名
		 */
		public PropertyName<Integer> postId() {
			return new PropertyName<Integer>(this, "postId");
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
		 * titleのプロパティ名を返します。
		 *
		 * @return titleのプロパティ名
		 */
		public PropertyName<String> title() {
			return new PropertyName<String>(this, "title");
		}

		/**
		 * textのプロパティ名を返します。
		 *
		 * @return textのプロパティ名
		 */
		public PropertyName<String> text() {
			return new PropertyName<String>(this, "text");
		}

		/**
		 * registTimeのプロパティ名を返します。
		 *
		 * @return registTimeのプロパティ名
		 */
		public PropertyName<Date> registTime() {
			return new PropertyName<Date>(this, "registTime");
		}

		/**
		 * updateTimeのプロパティ名を返します。
		 *
		 * @return updateTimeのプロパティ名
		 */
		public PropertyName<Date> updateTime() {
			return new PropertyName<Date>(this, "updateTime");
		}

		/**
		 * deleteFlgのプロパティ名を返します。
		 *
		 * @return deleteFlgのプロパティ名
		 */
		public PropertyName<Boolean> deleteFlg() {
			return new PropertyName<Boolean>(this, "deleteFlg");
		}

		/**
		 * versionのプロパティ名を返します。
		 *
		 * @return versionのプロパティ名
		 */
		public PropertyName<Long> version() {
			return new PropertyName<Long>(this, "version");
		}

		/**
		 * TUserのプロパティ名を返します。
		 * 
		 * @return TUserのプロパティ名
		 */
		public _TUserNames TUser() {
			return new _TUserNames(this, "TUser");
		}

		/**
		 * TPostImageListのプロパティ名を返します。
		 * 
		 * @return TPostImageListのプロパティ名
		 */
		public _TPostImageNames TPostImageList() {
			return new _TPostImageNames(this, "TPostImageList");
		}

		/**
		 * TPostTagListのプロパティ名を返します。
		 * 
		 * @return TPostTagListのプロパティ名
		 */
		public _TPostTagNames TPostTagList() {
			return new _TPostTagNames(this, "TPostTagList");
		}
	}
}