package com.isystk.sample.common.gen;

import java.io.File;

import com.isystk.sample.common.gen.properties.GenerateProperteisEnumCommand;

/**
 * properteisファイルからキーを持ったenum を作成します。
 * 
 * @author iseyoshitaka
 * 
 */

public class GenerateAppConfigTask {
	public static void main(String[] args) {
		GenerateProperteisEnumCommand entity = new GenerateProperteisEnumCommand();
		entity.setShortClassName("AppConfigNames");
		entity.setPackageName("com.isystk.sample.common.config");
		entity.setTemplateFileName("appConfigProperties.ftl");
		entity.setOverwrite(true);

		File file = new File("src/main/resources/application-config.properties");
		entity.setPropertiesFile(file);
		entity.execute();
	}
}
