/**
 * Copyright(c) isystk.com</br>
 */
package com.isystk.sample.common.gen;

import java.io.File;

import org.seasar.extension.jdbc.gen.internal.command.GenerateServiceCommand;

/**
 * Serviceを自動生成する
 * 
 * @author iseyoshitaka
 */
public class GenerateServiceTask {
    public static void main(String[] args) {
	GenerateServiceCommand command = new GenerateServiceCommand();
	command.setEnv("ut");
	command.setRootPackageName("com.isystk.sample.common.s2");
	File dir = new File("target/classes");
	command.setClasspathDir(dir);
	command.setNamesPackageName("entity.names");
	command.setIgnoreEntityClassNamePattern("^(?!.*GaReportSearch)(?=.*Ga).*");
	command.setServiceTemplateFileName("java/service.ftl");
	// 上書きしない場合はfalseに
	command.setOverwrite(false);
	command.execute();

    }
}
