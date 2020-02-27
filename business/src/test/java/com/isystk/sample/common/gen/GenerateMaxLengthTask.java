/**
 * Copyright(c) isystk.com</br>
 */
package com.isystk.sample.common.gen;

import com.isystk.sample.common.gen.command.GenerateMaxLengthCommand;

/**
 * MaxLengthを自動生成する
 * 
 * @author iseyoshitaka
 */
public class GenerateMaxLengthTask {
    public static void main(String[] args) {
	GenerateMaxLengthCommand command = new GenerateMaxLengthCommand();
	command.setEnv("ut");
	command.setRootPackageName("com.isystk.sample.common.constants.entity");
	command.setEntityPackageName("");
	command.setApplyDbCommentToJava(true);
	// 上書きしない場合はfalseに
	command.setUseTemporalType(true);
	command.setEntityTemplateFileName("java/entity-maxlength.ftl");
	command.setOverwrite(true);
	command.execute();

    }
}
