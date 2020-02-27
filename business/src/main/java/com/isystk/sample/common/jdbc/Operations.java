package com.isystk.sample.common.jdbc;

import org.seasar.extension.jdbc.OrderByItem;
import org.seasar.extension.jdbc.OrderByItem.OrderingSpec;

/**
 * MySqL用にOperationsを拡張する
 */
public class Operations extends org.seasar.extension.jdbc.operation.Operations {

    /**
     * MySQLの場合のNullを最後にした昇順の{@link OrderByItem}を作成します。 例えば、「order by prop IS
     * NULL asc, prop asc」のようなOrderbyを構築することができます。
     * 
     * @param propertyName プロパティ名
     * @return 昇順の{@link OrderByItem}
     */
    public static OrderByItem ascNullsLast(final CharSequence propertyName) {
	final OrderByItem ascIsNull = new OrderByItem(propertyName + " IS NULL");
	final OrderByItem asc = new OrderByItem(propertyName);

	return new OrderByItem(propertyName) {
	    @Override
	    public String getCriteria() {
		return ascIsNull.getCriteria() + " ," + asc.getCriteria();
	    }
	};
    }

    /**
     * MySQLの場合のNull最初にした降順の{@link OrderByItem}を作成します。 例えば、「order by prop IS
     * NULL desc, prop desc」のようなOrderbyを構築することができます。
     * 
     * @param propertyName プロパティ名
     * @return ｌ降順の{@link OrderByItem}
     */
    public static OrderByItem descNullsFirst(final CharSequence propertyName) {
	final OrderByItem descIsNull = new OrderByItem(propertyName + " IS NULL", OrderingSpec.DESC);
	final OrderByItem desc = new OrderByItem(propertyName, OrderingSpec.DESC);

	return new OrderByItem(propertyName) {
	    @Override
	    public String getCriteria() {
		return descIsNull.getCriteria() + " ," + desc.getCriteria();
	    }
	};
    }

}
