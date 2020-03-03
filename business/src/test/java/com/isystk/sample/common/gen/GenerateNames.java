/**
 * Copyright(c) isystk.com</br>
 */
package com.isystk.sample.common.gen;

import java.io.File;

import org.seasar.extension.jdbc.gen.internal.command.GenerateNamesCommand;

/**
 * Namesを自動生成する
 * 
 * @author iseyoshitaka
 */
public class GenerateNames {

	public static void main(String[] args) {
		GenerateNamesCommand command = new GenerateNamesCommand();
		command.setEnv("ut");
		command.setClasspathDir(new File("target/classes"));
		command.setRootPackageName("com.isystk.sample.common.s2");
		command.setNamesPackageName("entity.names");
		command.setIgnoreEntityClassNamePattern("^(?!.*GaReportSearch)(?=.*Ga).*");
		// command.setIgnoreEntityClassNamePattern(".*Ga.*&^(GaReportSearch)");
		command.setNamesTemplateFileName("java/names.ftl");
		command.setNamesAggregateTemplateFileName("java/names-aggregate.ftl");
		// 上書きしない場合はfalseに
		command.setOverwrite(true);
		command.execute();

	}
}
