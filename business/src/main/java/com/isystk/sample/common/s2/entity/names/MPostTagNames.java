package com.isystk.sample.common.s2.entity.names;

import java.util.Date;
import javax.annotation.Generated;

import org.seasar.extension.jdbc.name.PropertyName;

import com.isystk.sample.common.s2.entity.MPostTag;
import com.isystk.sample.common.s2.entity.names.TPostTagNames._TPostTagNames;

/**
 * {@link MPostTag}のプロパティ名の集合です。
 * 
 */
@Generated(value = {"S2JDBC-Gen 2.4.44", "org.seasar.extension.jdbc.gen.internal.model.NamesModelFactoryImpl"})
public class MPostTagNames {

    /**
     * postTagIdのプロパティ名を返します。
     * 
     * @return postTagIdのプロパティ名
     */
    public static PropertyName<Integer> postTagId() {
        return new PropertyName<Integer>("postTagId");
    }

    /**
     * nameのプロパティ名を返します。
     * 
     * @return nameのプロパティ名
     */
    public static PropertyName<String> name() {
        return new PropertyName<String>("name");
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
    public static class _MPostTagNames extends PropertyName<MPostTag> {

        /**
         * インスタンスを構築します。
         */
        public _MPostTagNames() {
        }

        /**
         * インスタンスを構築します。
         * 
         * @param name
         *            名前
         */
        public _MPostTagNames(final String name) {
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
        public _MPostTagNames(final PropertyName<?> parent, final String name) {
            super(parent, name);
        }

        /**
         * postTagIdのプロパティ名を返します。
         *
         * @return postTagIdのプロパティ名
         */
        public PropertyName<Integer> postTagId() {
            return new PropertyName<Integer>(this, "postTagId");
        }

        /**
         * nameのプロパティ名を返します。
         *
         * @return nameのプロパティ名
         */
        public PropertyName<String> name() {
            return new PropertyName<String>(this, "name");
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
         * TPostTagListのプロパティ名を返します。
         * 
         * @return TPostTagListのプロパティ名
         */
        public _TPostTagNames TPostTagList() {
            return new _TPostTagNames(this, "TPostTagList");
        }
    }
}