package com.isystk.sample.common.gen;

import java.io.File;

import com.isystk.sample.common.gen.properties.GenerateProperteisEnumCommand;

/**
 * properteisファイルからキーを持ったenum を作成します。
 * 
 * @author iseyoshitaka
 * 
 */

public class GenerateAppMessageTask {
    public static void main(String[] args) {
	GenerateProperteisEnumCommand entity = new GenerateProperteisEnumCommand();
	entity.setShortClassName("AppMessageNames");
	entity.setPackageName("com.isystk.sample.common.config");
	entity.setTemplateFileName("appMessageProperties.ftl");
	entity.setOverwrite(true);

	File file = new File("src/main/resources/application-message.properties");
	entity.setPropertiesFile(file);
	entity.execute();
    }
}
