package com.isystk.sample.common.s2.entity.names;

import javax.annotation.Generated;

import org.seasar.extension.jdbc.name.PropertyName;

import com.isystk.sample.common.s2.entity.TPostTag;
import com.isystk.sample.common.s2.entity.names.MPostTagNames._MPostTagNames;
import com.isystk.sample.common.s2.entity.names.TPostNames._TPostNames;

/**
 * {@link TPostTag}のプロパティ名の集合です。
 * 
 */
@Generated(value = {"S2JDBC-Gen 2.4.44", "org.seasar.extension.jdbc.gen.internal.model.NamesModelFactoryImpl"})
public class TPostTagNames {

    /**
     * postIdのプロパティ名を返します。
     * 
     * @return postIdのプロパティ名
     */
    public static PropertyName<Integer> postId() {
        return new PropertyName<Integer>("postId");
    }

    /**
     * postTagIdのプロパティ名を返します。
     * 
     * @return postTagIdのプロパティ名
     */
    public static PropertyName<Integer> postTagId() {
        return new PropertyName<Integer>("postTagId");
    }

    /**
     * TPostのプロパティ名を返します。
     * 
     * @return TPostのプロパティ名
     */
    public static _TPostNames TPost() {
        return new _TPostNames("TPost");
    }

    /**
     * MPostTagのプロパティ名を返します。
     * 
     * @return MPostTagのプロパティ名
     */
    public static _MPostTagNames MPostTag() {
        return new _MPostTagNames("MPostTag");
    }

    /**
     * @author S2JDBC-Gen
     */
    public static class _TPostTagNames extends PropertyName<TPostTag> {

        /**
         * インスタンスを構築します。
         */
        public _TPostTagNames() {
        }

        /**
         * インスタンスを構築します。
         * 
         * @param name
         *            名前
         */
        public _TPostTagNames(final String name) {
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
        public _TPostTagNames(final PropertyName<?> parent, final String name) {
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
         * postTagIdのプロパティ名を返します。
         *
         * @return postTagIdのプロパティ名
         */
        public PropertyName<Integer> postTagId() {
            return new PropertyName<Integer>(this, "postTagId");
        }

        /**
         * TPostのプロパティ名を返します。
         * 
         * @return TPostのプロパティ名
         */
        public _TPostNames TPost() {
            return new _TPostNames(this, "TPost");
        }

        /**
         * MPostTagのプロパティ名を返します。
         * 
         * @return MPostTagのプロパティ名
         */
        public _MPostTagNames MPostTag() {
            return new _MPostTagNames(this, "MPostTag");
        }
    }
}