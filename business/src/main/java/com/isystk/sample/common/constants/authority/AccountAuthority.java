/**
 * Copyright(c) team-lab</br>
 */
package com.isystk.sample.common.constants.authority;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * アカウントの権限
 * @author nkawamata
 */
public enum AccountAuthority implements BaseAuthority {

    /** なし */
    NONE,

    /** 求人枠管理：掲載確定を除く全機能 */
    MANAGE_JOB_FRAME,

    /** 求人枠管理：掲載確定 */
    COMPLETE_JOB_PUBLICATION,

    /** 求人枠管理：枠CSVアップロード */
    UPLOAD_JOB_FRAME_CSV,

    // MANAGE_JOB_STOCKに統合のため削除
    /** 求人ストック管理：ストックの作成 */
    @Deprecated
    CREATE_JOB_STOCK,

    /** 求人ストック管理：ストック一覧の全機能 */
    MANAGE_JOB_STOCK,

    /** 応募者管理：応募者一覧の機能（個人情報は不可視） */
    MANAGE_ENTRY_USER,

    /** 応募者管理：応募者一覧における個人情報の表示 */
    VIEW_PERSONAL_DATA_OF_ENTRY_USER,

    /** 応募者管理：送信メール一覧の機能（個人情報は不可視） */
    MANAGE_SEND_MAIL,

    /** 応募者管理：送信メール一覧における個人情報の表示 */
    VIEW_PERSONAL_DATA_OF_SEND_MAIL,

    /** 効果測定：全機能 */
    VIEW_RESULTS,

    /** 共通機能：個別送信メールテンプレートの閲覧・編集 */
    MANAGE_INDIVIDUAL_MAIL_TEMPLATE,

    /** 共通機能：オリジナル自動送信メールテンプレートの閲覧・編集 */
    MANAGE_AUTOMATIC_MAIL_TEMPLATE,

    /** 共通機能：応募フォームカスタマイズの閲覧・編集 */
    MANAGE_CUSTOMIZED_ENTRY_FORM,

    /** 共通機能：画像管理の閲覧・編集 */
    MANAGE_JOB_IMAGE,

    /** 共通機能：アカウント管理の全機能 */
    MANAGE_CLIENT_SUB_ACCOUNT,

    /** 利用明細：利用明細の閲覧 */
    VIEW_SALES_AMOUNT,

    /** 利用明細：応募の削除 */
    DELETE_ENTRY,
    
    /** 商品申請・オプション申請：掲載開始日・終了日設定の特例 */
    EXCEPTION_OF_SETTING_PUBLICATION_DATE,

    /** お問い合わせ：お問い合わせの全機能 */
    MANAGE_CLIENT_INQUIRY;

    /** すべての権限を持つアカウントの表現文字列 */
    public static final String SUPER_ADMIN = "*";

    /**
     * nameで権限を見つけます。
     * @param name 権限名
     * @return 権限
     */
    public static AccountAuthority find(String name) {
        if (name == null) {
            return null;
        }
        for (AccountAuthority a : values()) {
            if (a.name().equals(name)) {
                return a;
            }
        }
        return null;
    }

    /** 空 */
    private static final AccountAuthority[] EMPTY = new AccountAuthority[0];

    /**
     * 文字列から権限を起こします
     * @param values 権限文字列
     * @return 権限
     */
    public static AccountAuthority[] valuesOf(String values) {
        if (values == null || values.length() <= 0) {
            return EMPTY;
        }

        if (SUPER_ADMIN.equals(values)) {
            return values();
        }

        List<AccountAuthority> list = new ArrayList<AccountAuthority>();
        for (String name : values.split("\\s*,\\s*")) {
            AccountAuthority a = find(name.trim());
            if (a == null) {
                continue;
            }
            list.add(a);
        }

        if (list.isEmpty()) {
            return EMPTY;
        }

        AccountAuthority[] arr = list
                .toArray(new AccountAuthority[list.size()]);
        Arrays.sort(arr);

        return arr;
    }

    /**
     * @param authes　権限
     * @return 権限文字列
     */
    public static String toString(AccountAuthority[] authes) {
        if (authes == null || authes.length <= 0) {
            return "";
        }

        boolean first = true;
        StringBuilder sb = new StringBuilder();
        for (AccountAuthority a : authes) {
            if (a == null) {
                continue;
            }
            if (first) {
                first = false;
            } else {
                sb.append(",");
            }
            sb.append(a.name());
        }

        return sb.toString();
    }

    /**
     * 権限を持っているかどうかを判定します
     * @param authes 権限の集合
     * @param auth 権限
     * @return 持っているときtrue
     */
    public static boolean hasAuthority(AccountAuthority[] authes,
            AccountAuthority auth) {

        if (auth == null) {
            throw new IllegalArgumentException();
        }

        if (NONE.equals(auth)) {
            return true;
        }

        if (authes == null || authes.length <= 0) {
            return false;
        }

        for (AccountAuthority a : authes) {
            if (a == null) {
                continue;
            }
            if (a.equals(auth)) {
                return true;
            }
        }
        return false;
    }
}
