package com.isystk.sample.common.s2.entity.names;

import com.isystk.sample.common.s2.entity.TImage;
import com.isystk.sample.common.s2.entity.names.TPostImageNames._TPostImageNames;
import java.util.Date;
import javax.annotation.Generated;
import org.seasar.extension.jdbc.name.PropertyName;

/**
 * {@link TImage}のプロパティ名の集合です。
 * 
 */
@Generated(value = { "S2JDBC-Gen 2.4.44", "org.seasar.extension.jdbc.gen.internal.model.NamesModelFactoryImpl" })
public class TImageNames {

	/**
	 * imageIdのプロパティ名を返します。
	 * 
	 * @return imageIdのプロパティ名
	 */
	public static PropertyName<Integer> imageId() {
		return new PropertyName<Integer>("imageId");
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
	 * TPostImageListのプロパティ名を返します。
	 * 
	 * @return TPostImageListのプロパティ名
	 */
	public static _TPostImageNames TPostImageList() {
		return new _TPostImageNames("TPostImageList");
	}

	/**
	 * @author S2JDBC-Gen
	 */
	public static class _TImageNames extends PropertyName<TImage> {

		/**
		 * インスタンスを構築します。
		 */
		public _TImageNames() {
		}

		/**
		 * インスタンスを構築します。
		 * 
		 * @param name
		 *            名前
		 */
		public _TImageNames(final String name) {
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
		public _TImageNames(final PropertyName<?> parent, final String name) {
			super(parent, name);
		}

		/**
		 * imageIdのプロパティ名を返します。
		 *
		 * @return imageIdのプロパティ名
		 */
		public PropertyName<Integer> imageId() {
			return new PropertyName<Integer>(this, "imageId");
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
		 * TPostImageListのプロパティ名を返します。
		 * 
		 * @return TPostImageListのプロパティ名
		 */
		public _TPostImageNames TPostImageList() {
			return new _TPostImageNames(this, "TPostImageList");
		}
	}
}