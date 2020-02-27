/**
 * Copyright(c) isystk.com</br>
 */
package com.isystk.sample.common.gen;

import org.seasar.extension.jdbc.gen.internal.command.GenerateEntityCommand;

/**
 * Entityを自動生成する
 *
 * @author iseyoshitaka
 */
public class GenerateEntityTask {
    public static void main(String[] args) {
	GenerateEntityCommand command = new GenerateEntityCommand();
	command.setEnv("ut");
	command.setRootPackageName("com.isystk.sample.common.s2");
	// ドレスジュエリーから扱う場合でも、commonのエンティティはmynaviスキーマ参照するように
	command.setSchemaName("sample");
	// 上書きしない場合はfalseに
	command.setUseTemporalType(true);
	command.setUseAccessor(true);
	command.setEntityTemplateFileName("java/entity.ftl");
	command.setOverwrite(true);
	command.execute();

    }
}
