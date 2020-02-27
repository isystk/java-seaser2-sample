package com.isystk.sample.common.s2.entity.names;

import com.isystk.sample.common.s2.entity.TUser;
import com.isystk.sample.common.s2.entity.names.TPostNames._TPostNames;
import com.isystk.sample.common.s2.entity.names.TUserOnetimePassNames._TUserOnetimePassNames;
import com.isystk.sample.common.s2.entity.names.TUserOnetimeValidNames._TUserOnetimeValidNames;
import java.util.Date;
import javax.annotation.Generated;
import org.seasar.extension.jdbc.name.PropertyName;

/**
 * {@link TUser}のプロパティ名の集合です。
 * 
 */
@Generated(value = {"S2JDBC-Gen 2.4.44", "org.seasar.extension.jdbc.gen.internal.model.NamesModelFactoryImpl"})
public class TUserNames {

    /**
     * userIdのプロパティ名を返します。
     * 
     * @return userIdのプロパティ名
     */
    public static PropertyName<Integer> userId() {
        return new PropertyName<Integer>("userId");
    }

    /**
     * mailのプロパティ名を返します。
     * 
     * @return mailのプロパティ名
     */
    public static PropertyName<String> mail() {
        return new PropertyName<String>("mail");
    }

    /**
     * passwordのプロパティ名を返します。
     * 
     * @return passwordのプロパティ名
     */
    public static PropertyName<byte[]> password() {
        return new PropertyName<byte[]>("password");
    }

    /**
     * familyNameのプロパティ名を返します。
     * 
     * @return familyNameのプロパティ名
     */
    public static PropertyName<String> familyName() {
        return new PropertyName<String>("familyName");
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
     * familyNameKanaのプロパティ名を返します。
     * 
     * @return familyNameKanaのプロパティ名
     */
    public static PropertyName<String> familyNameKana() {
        return new PropertyName<String>("familyNameKana");
    }

    /**
     * nameKanaのプロパティ名を返します。
     * 
     * @return nameKanaのプロパティ名
     */
    public static PropertyName<String> nameKana() {
        return new PropertyName<String>("nameKana");
    }

    /**
     * zipのプロパティ名を返します。
     * 
     * @return zipのプロパティ名
     */
    public static PropertyName<String> zip() {
        return new PropertyName<String>("zip");
    }

    /**
     * prefectureIdのプロパティ名を返します。
     * 
     * @return prefectureIdのプロパティ名
     */
    public static PropertyName<Integer> prefectureId() {
        return new PropertyName<Integer>("prefectureId");
    }

    /**
     * areaのプロパティ名を返します。
     * 
     * @return areaのプロパティ名
     */
    public static PropertyName<String> area() {
        return new PropertyName<String>("area");
    }

    /**
     * addressのプロパティ名を返します。
     * 
     * @return addressのプロパティ名
     */
    public static PropertyName<String> address() {
        return new PropertyName<String>("address");
    }

    /**
     * buildingのプロパティ名を返します。
     * 
     * @return buildingのプロパティ名
     */
    public static PropertyName<String> building() {
        return new PropertyName<String>("building");
    }

    /**
     * telのプロパティ名を返します。
     * 
     * @return telのプロパティ名
     */
    public static PropertyName<String> tel() {
        return new PropertyName<String>("tel");
    }

    /**
     * sexのプロパティ名を返します。
     * 
     * @return sexのプロパティ名
     */
    public static PropertyName<Integer> sex() {
        return new PropertyName<Integer>("sex");
    }

    /**
     * birthdayのプロパティ名を返します。
     * 
     * @return birthdayのプロパティ名
     */
    public static PropertyName<Date> birthday() {
        return new PropertyName<Date>("birthday");
    }

    /**
     * lastLoginTimeのプロパティ名を返します。
     * 
     * @return lastLoginTimeのプロパティ名
     */
    public static PropertyName<Date> lastLoginTime() {
        return new PropertyName<Date>("lastLoginTime");
    }

    /**
     * statusのプロパティ名を返します。
     * 
     * @return statusのプロパティ名
     */
    public static PropertyName<Integer> status() {
        return new PropertyName<Integer>("status");
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
     * TPostListのプロパティ名を返します。
     * 
     * @return TPostListのプロパティ名
     */
    public static _TPostNames TPostList() {
        return new _TPostNames("TPostList");
    }

    /**
     * TUserOnetimePassのプロパティ名を返します。
     * 
     * @return TUserOnetimePassのプロパティ名
     */
    public static _TUserOnetimePassNames TUserOnetimePass() {
        return new _TUserOnetimePassNames("TUserOnetimePass");
    }

    /**
     * TUserOnetimeValidのプロパティ名を返します。
     * 
     * @return TUserOnetimeValidのプロパティ名
     */
    public static _TUserOnetimeValidNames TUserOnetimeValid() {
        return new _TUserOnetimeValidNames("TUserOnetimeValid");
    }

    /**
     * @author S2JDBC-Gen
     */
    public static class _TUserNames extends PropertyName<TUser> {

        /**
         * インスタンスを構築します。
         */
        public _TUserNames() {
        }

        /**
         * インスタンスを構築します。
         * 
         * @param name
         *            名前
         */
        public _TUserNames(final String name) {
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
        public _TUserNames(final PropertyName<?> parent, final String name) {
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
         * mailのプロパティ名を返します。
         *
         * @return mailのプロパティ名
         */
        public PropertyName<String> mail() {
            return new PropertyName<String>(this, "mail");
        }

        /**
         * passwordのプロパティ名を返します。
         *
         * @return passwordのプロパティ名
         */
        public PropertyName<byte[]> password() {
            return new PropertyName<byte[]>(this, "password");
        }

        /**
         * familyNameのプロパティ名を返します。
         *
         * @return familyNameのプロパティ名
         */
        public PropertyName<String> familyName() {
            return new PropertyName<String>(this, "familyName");
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
         * familyNameKanaのプロパティ名を返します。
         *
         * @return familyNameKanaのプロパティ名
         */
        public PropertyName<String> familyNameKana() {
            return new PropertyName<String>(this, "familyNameKana");
        }

        /**
         * nameKanaのプロパティ名を返します。
         *
         * @return nameKanaのプロパティ名
         */
        public PropertyName<String> nameKana() {
            return new PropertyName<String>(this, "nameKana");
        }

        /**
         * zipのプロパティ名を返します。
         *
         * @return zipのプロパティ名
         */
        public PropertyName<String> zip() {
            return new PropertyName<String>(this, "zip");
        }

        /**
         * prefectureIdのプロパティ名を返します。
         *
         * @return prefectureIdのプロパティ名
         */
        public PropertyName<Integer> prefectureId() {
            return new PropertyName<Integer>(this, "prefectureId");
        }

        /**
         * areaのプロパティ名を返します。
         *
         * @return areaのプロパティ名
         */
        public PropertyName<String> area() {
            return new PropertyName<String>(this, "area");
        }

        /**
         * addressのプロパティ名を返します。
         *
         * @return addressのプロパティ名
         */
        public PropertyName<String> address() {
            return new PropertyName<String>(this, "address");
        }

        /**
         * buildingのプロパティ名を返します。
         *
         * @return buildingのプロパティ名
         */
        public PropertyName<String> building() {
            return new PropertyName<String>(this, "building");
        }

        /**
         * telのプロパティ名を返します。
         *
         * @return telのプロパティ名
         */
        public PropertyName<String> tel() {
            return new PropertyName<String>(this, "tel");
        }

        /**
         * sexのプロパティ名を返します。
         *
         * @return sexのプロパティ名
         */
        public PropertyName<Integer> sex() {
            return new PropertyName<Integer>(this, "sex");
        }

        /**
         * birthdayのプロパティ名を返します。
         *
         * @return birthdayのプロパティ名
         */
        public PropertyName<Date> birthday() {
            return new PropertyName<Date>(this, "birthday");
        }

        /**
         * lastLoginTimeのプロパティ名を返します。
         *
         * @return lastLoginTimeのプロパティ名
         */
        public PropertyName<Date> lastLoginTime() {
            return new PropertyName<Date>(this, "lastLoginTime");
        }

        /**
         * statusのプロパティ名を返します。
         *
         * @return statusのプロパティ名
         */
        public PropertyName<Integer> status() {
            return new PropertyName<Integer>(this, "status");
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
         * TPostListのプロパティ名を返します。
         * 
         * @return TPostListのプロパティ名
         */
        public _TPostNames TPostList() {
            return new _TPostNames(this, "TPostList");
        }

        /**
         * TUserOnetimePassのプロパティ名を返します。
         * 
         * @return TUserOnetimePassのプロパティ名
         */
        public _TUserOnetimePassNames TUserOnetimePass() {
            return new _TUserOnetimePassNames(this, "TUserOnetimePass");
        }

        /**
         * TUserOnetimeValidのプロパティ名を返します。
         * 
         * @return TUserOnetimeValidのプロパティ名
         */
        public _TUserOnetimeValidNames TUserOnetimeValid() {
            return new _TUserOnetimeValidNames(this, "TUserOnetimeValid");
        }
    }
}