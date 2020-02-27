/**
 * Copyright(c) team-lab</br>
 */
package com.isystk.sample.common.util;

import java.lang.reflect.Array;




/**
 * 非機能依存汎用ユーティリティークラス。<br>
 * <br>
 * プログラムで汎用的に利用される非機能依存ユーティリティーメソッドを定義したクラス。
 *
 * @author knagaoka
 *
 */
public final class GeneralUtils {


    /**
     * float型の有効桁数
     */
    private static final float      EPSILON_FLOAT   = 1.0e-7f;

    /**
     * double型の有効桁数
     */
    private static final double     EPSILON_DOBULE  = 1.0e-16;




    /**
     * インスタンス生成防止。
     *
     */
    private GeneralUtils() {

        // 処理なし

    }


    /**
     * オブジェクト一致判定を行う。<br>
     * <br>
     * オブジェクトが null のときも考慮した一致判定を行う。
     *
     * @param obj1  比較オブジェクト1
     * @param obj2  比較オブジェクト2
     * @return true : 一致　false 不一致
     */
    public static boolean equalsObject(
            final Object obj1,
            final Object obj2
            ) {

        return obj1 == null ? obj2 == null : obj1.equals(obj2);

    }


    /**
     * float型の値の一致判定を行う。
     *
     * @param value1    比較するfloat値1
     * @param value2    比較するfloat値2
     * @return true : 一致　false 不一致
     */
    public static boolean equalsFloat(
            final float value1,
            final float value2
            ) {

        // 大きい方の値を取得する
        final float     max     = Math.max(value1, value2);

        // 有効桁数と比較する値を取得する
        final float     value   = max == 0.0f ? 0.0f : Math.abs((value1 - value2) / max);

        // 一致するかどうかを返す
        return value < EPSILON_FLOAT;

    }


    /**
     * double型の値の一致判定を行う。
     *
     * @param value1    比較するdouble値1
     * @param value2    比較するdouble値2
     * @return true : 一致　false 不一致
     */
    public static boolean equalsDouble(
            final double value1,
            final double value2
            ) {

        // 大きい方の値を取得する
        final double    max     = Math.max(value1, value2);

        // 有効桁数と比較する値を取得する
        final double    value   = max == 0.0f ? 0.0f : Math.abs((value1 - value2) / max);

        // 一致するかどうかを返す
        return value < EPSILON_DOBULE;

    }


    /**
     * 指定されたインスタンスを代入先の型へ自動キャストする。
     *
     * @param <T>   キャストするインスタンスの型
     * @param value キャストするインスタンス
     * @return キャストしたインスタンス
     */
    @SuppressWarnings("unchecked")
    public static <T> T cast(
            final Object    value
            ) {

        return (T)value;

    }


    /**
     * 指定されたインスタンスが複製可能な場合、インスタンスを複製する。
     *
     * @param value 複製するインスタンス
     * @return 複製したインスタンス。複製できない場合は null
     */
    public static <T> T clone(
            final T     value
            ) {

        try {

            // 複製可能な場合
            if (value instanceof Cloneable) {

                final Class<?>  clazz = value.getClass();

                // 配列の場合
                if (clazz.isArray()) {

                    // 配列の複製メソッドを呼び出す
                    final int    length = Array.getLength(value);
                    final Object array  = Array.newInstance(clazz.getComponentType(), length);

                    // 各要素を複製する
                    System.arraycopy(value, 0, array, 0, length);

                    // 結果を返す
                    return GeneralUtils.<T>cast(array);

                } else {

                    // インスタンスを複製する
                    return GeneralUtils.<T>cast(value.getClass().getDeclaredMethod("clone", (Class[])null).invoke(value, (Object[])null));

                }

            } else {

                // 複製出来ない場合は null
                return null;

            }

        } catch (final Throwable e) {

            // 複製失敗
            return null;

        }

    }


}
