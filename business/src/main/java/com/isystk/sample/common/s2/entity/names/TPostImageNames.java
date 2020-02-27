package com.isystk.sample.common.s2.entity.names;

import javax.annotation.Generated;

import org.seasar.extension.jdbc.name.PropertyName;

import com.isystk.sample.common.s2.entity.TPostImage;
import com.isystk.sample.common.s2.entity.names.TImageNames._TImageNames;
import com.isystk.sample.common.s2.entity.names.TPostNames._TPostNames;

/**
 * {@link TPostImage}のプロパティ名の集合です。
 * 
 */
@Generated(value = {"S2JDBC-Gen 2.4.44", "org.seasar.extension.jdbc.gen.internal.model.NamesModelFactoryImpl"})
public class TPostImageNames {

    /**
     * postIdのプロパティ名を返します。
     * 
     * @return postIdのプロパティ名
     */
    public static PropertyName<Integer> postId() {
        return new PropertyName<Integer>("postId");
    }

    /**
     * imageIdのプロパティ名を返します。
     * 
     * @return imageIdのプロパティ名
     */
    public static PropertyName<Integer> imageId() {
        return new PropertyName<Integer>("imageId");
    }

    /**
     * TImageのプロパティ名を返します。
     * 
     * @return TImageのプロパティ名
     */
    public static _TImageNames TImage() {
        return new _TImageNames("TImage");
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
     * @author S2JDBC-Gen
     */
    public static class _TPostImageNames extends PropertyName<TPostImage> {

        /**
         * インスタンスを構築します。
         */
        public _TPostImageNames() {
        }

        /**
         * インスタンスを構築します。
         * 
         * @param name
         *            名前
         */
        public _TPostImageNames(final String name) {
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
        public _TPostImageNames(final PropertyName<?> parent, final String name) {
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
         * imageIdのプロパティ名を返します。
         *
         * @return imageIdのプロパティ名
         */
        public PropertyName<Integer> imageId() {
            return new PropertyName<Integer>(this, "imageId");
        }

        /**
         * TImageのプロパティ名を返します。
         * 
         * @return TImageのプロパティ名
         */
        public _TImageNames TImage() {
            return new _TImageNames(this, "TImage");
        }

        /**
         * TPostのプロパティ名を返します。
         * 
         * @return TPostのプロパティ名
         */
        public _TPostNames TPost() {
            return new _TPostNames(this, "TPost");
        }
    }
}