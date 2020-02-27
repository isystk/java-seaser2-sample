package com.isystk.sample.common.gen.command;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.seasar.extension.jdbc.gen.command.Command;
import org.seasar.extension.jdbc.gen.desc.AttributeDesc;
import org.seasar.extension.jdbc.gen.desc.EntityDesc;
import org.seasar.extension.jdbc.gen.desc.EntitySetDesc;
import org.seasar.extension.jdbc.gen.internal.command.GenerateEntityCommand;

import com.isystk.sample.common.util.NumberUtil;

/**
 * エンティティクラスのMaxLengthファイルを生成する{@link Command}の実装クラスです。
 * <p>
 * このコマンドは、データベースのメタデータからエンティティクラスのMaxLengthのJavaファイルを生成します。
 * </p>
 * <p>
 * lengthの指定がないカラムはフィールドとして生成しません。
 * </p>
 * 
 * @author iseyoshitaka
 * @author iseyoshitaka
 */
public class GenerateMaxLengthCommand extends GenerateEntityCommand {

    private static final int INTEGER_LENGTH = 9;
    @Override
    protected void doExecute() {
	EntitySetDesc entitySetDesc = entitySetDescFactory.getEntitySetDesc();
	EntityDesc result = new EntityDesc();
	// これがファイル名となる
	result.setName("MaxLength");

	Pattern pattern = Pattern.compile(".*int\\(([0-9]+)\\).*");
	for (EntityDesc desc : entitySetDesc.getEntityDescList()) {
	    for (AttributeDesc attributeDesc : desc.getAttributeDescList()) {
		AttributeDesc attr = new AttributeDesc();
		attr.setName(desc.getTableName() + "_" + attributeDesc.getName());
		attr.setLength(attributeDesc.getLength());

		// Integer型　かつ　commentにint([0-9]+)が存在する場合は括弧内の数値をprecisionに設定します
		if ("Integer".equals(attributeDesc.getAttributeClass().getSimpleName())) {
		    Matcher matcher = pattern.matcher(attributeDesc.getComment());
		    if (matcher.matches()) {
			attr.setPrecision(NumberUtil.toInteger(matcher.group(1), INTEGER_LENGTH));
		    } else {
			attr.setPrecision(INTEGER_LENGTH);
		    }
		} else {
		    attr.setPrecision(attributeDesc.getPrecision());
		}
		attr.setComment(attributeDesc.getComment());
		attr.setAttributeClass(attributeDesc.getAttributeClass());
		attr.setColumnName(desc.getTableName() + "_" + attributeDesc.getColumnName());
		result.addAttributeDesc(attr);
	    }
	}

	generateEntity(result);
    }
}
