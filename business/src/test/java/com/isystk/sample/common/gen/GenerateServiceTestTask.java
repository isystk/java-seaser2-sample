/**
 * Copyright(c) isystk.com</br>
 */
package com.isystk.sample.common.gen;

import java.io.File;

import org.seasar.extension.jdbc.gen.internal.command.GenerateServiceTestCommand;

/**
 * ServiceTestを自動生成する
 * 
 * @author iseyoshitaka
 */
public class GenerateServiceTestTask {
	public static void main(String[] args) {
		GenerateServiceTestCommand command = new GenerateServiceTestCommand();
		command.setEnv("ut");
		File testDir = new File("src/test/java");
		command.setJavaFileDestDir(testDir);
		command.setRootPackageName("com.isystk.sample.common.s2");
		command.setIgnoreEntityClassNamePattern("^(?!.*GaReportSearch)(?=.*Ga).*");
		File dir = new File("target/classes");
		command.setClasspathDir(dir);
		command.setTemplateFileName("java/servicetest.ftl");
		// 上書きしない場合はfalseに
		command.setOverwrite(true);
		command.setUseS2junit4(true);
		command.execute();

	}
}
