package com.isystk.sample.common.gen.properties;

import java.io.File;
import java.util.ArrayList;

import javax.annotation.Generated;

import org.seasar.extension.jdbc.gen.generator.GenerationContext;
import org.seasar.extension.jdbc.gen.generator.Generator;
import org.seasar.extension.jdbc.gen.internal.command.AbstractCommand;
import org.seasar.extension.jdbc.gen.internal.command.GenerateEntityCommand;
import org.seasar.extension.jdbc.gen.internal.factory.Factory;
import org.seasar.extension.jdbc.gen.internal.util.FileUtil;
import org.seasar.extension.jdbc.gen.internal.util.ReflectUtil;
import org.seasar.extension.jdbc.gen.model.ClassModel;
import org.seasar.framework.log.Logger;
import org.seasar.framework.util.StringUtil;

import com.isystk.sample.common.gen.GenerateAppConfigTask;

/**
 * プロパティーファイルからEnumクラスを生成します。
 * 
 * @author iseyoshitaka
 * 
 */
public class GenerateProperteisEnumCommand extends AbstractCommand {
	String packageName = "";
	public boolean overwrite = false;
	public String javaFileEncoding = "UTF-8";
	public File javaFileDestDir = new File("src/main/java");
	/** 作成されるクラス */
	protected String shortClassName;

	/** 読み込むファイル名 */
	protected File propertiesFile;

	/** エンティティクラスのテンプレート名 */
	protected String templateFileName;

	/** テンプレートファイルのエンコーディング */
	protected String templateFileEncoding = "UTF-8";

	/** テンプレートファイルを格納するプライマリディレクトリ */
	protected File templateFilePrimaryDir = new File("src/test/resources/gen-template");

	public class Model extends ClassModel {
		private PropertiesReader prop;

		public Model(PropertiesReader prop) {
			this.prop = prop;
		}

		public String getFileName() {
			return GenerateProperteisEnumCommand.this.propertiesFile.toString();
		}

		public class DisplayProperty {
			PropertiesReader.Property comm;

			DisplayProperty(PropertiesReader.Property comm) {
				this.comm = comm;
			}

			public String getComment() {
				String comment = comm.getComment();
				if (comment != null) {
					comment = comment.replaceFirst("^\\s*[#!]", "");
					comment = comment.replaceAll("#", "     *");
				}
				comment = comment.trim();
				if (comment.indexOf("\n") != -1) {
					comment = "\n     * " + comment + "\n    ";
				}
				if (StringUtil.isBlank(comment)) {
					comment = getValue();
				}
				return uncomment(comment);
			}

			public String getName() {
				return getKey().toUpperCase().replace(".", "_");
			}

			public String getKey() {
				return comm.getKey();
			}

			public String getValue() {
				return comm.getValue();
			}

			String uncomment(String comment) {
				return comment.replaceAll("\\*\\/", "* /").replaceAll("&", "&amp;").replaceAll("<", "&lt;")
						.replaceAll(">", "&gt;");
			}
		}

		public ArrayList<DisplayProperty> getValues() {
			ArrayList<DisplayProperty> ret = new ArrayList<DisplayProperty>();
			for (PropertiesReader.Property c : prop.entitySet()) {
				ret.add(new DisplayProperty(c));
			}
			return ret;
		}
	}

	/**
	 * {@link Generator}の実装を作成します。
	 * 
	 * @return {@link Generator}の実装
	 */
	protected Generator createGenerator() {
		if (!templateFilePrimaryDir.exists())
			throw new RuntimeException(templateFilePrimaryDir.getAbsolutePath());
		return factory.createGenerator(this, templateFileEncoding, templateFilePrimaryDir);
	}

	/** ロガー */
	protected Logger logger = Logger.getLogger(GenerateEntityCommand.class);

	@Override
	protected void doDestroy() {
	}

	@Override
	protected void doExecute() throws Throwable {
		PropertiesReader prop = new PropertiesReader();
		System.out.println("read file " + propertiesFile.getAbsolutePath());
		prop.load(propertiesFile);
		Model model = new Model(prop);
		model.setPackageName(this.packageName);
		model.setShortClassName(this.shortClassName);
		model.addImportName(Generated.class.getName());
		model.addGeneratedInfo(GenerateAppConfigTask.class.getName());
		GenerationContext context = createGenerationContext(model, templateFileName);
		createGenerator().generate(context);
		System.out.println("version = " + propertiesFile.lastModified());
	}

	/**
	 * {@link GenerationContext}の実装を作成します。
	 * 
	 * @param model
	 *            モデル
	 * @param templateName
	 *            テンプレート名
	 * @return {@link GenerationContext}の実装
	 */
	protected GenerationContext createGenerationContext(final ClassModel model, final String templateName) {
		final File file = FileUtil.createJavaFile(javaFileDestDir, model.getPackageName(), model.getShortClassName());
		return factory.createGenerationContext(this, model, file, templateName, javaFileEncoding, overwrite);
	}

	@Override
	protected void doInit() {
		factory = ReflectUtil.newInstance(Factory.class, factoryClassName);
	}

	@Override
	protected void doValidate() {
	}

	@Override
	protected Logger getLogger() {
		return logger;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public boolean isOverwrite() {
		return overwrite;
	}

	public void setOverwrite(boolean overwrite) {
		this.overwrite = overwrite;
	}

	public String getJavaFileEncoding() {
		return javaFileEncoding;
	}

	public void setJavaFileEncoding(String javaFileEncoding) {
		this.javaFileEncoding = javaFileEncoding;
	}

	public File getJavaFileDestDir() {
		return javaFileDestDir;
	}

	public void setJavaFileDestDir(File javaFileDestDir) {
		this.javaFileDestDir = javaFileDestDir;
	}

	public String getShortClassName() {
		return shortClassName;
	}

	public void setShortClassName(String shortClassName) {
		this.shortClassName = shortClassName;
	}

	public File getPropertiesFile() {
		return propertiesFile;
	}

	public void setPropertiesFile(File propertiesFile) {
		this.propertiesFile = propertiesFile;
	}

	public String getTemplateFileName() {
		return templateFileName;
	}

	public void setTemplateFileName(String templateFileName) {
		this.templateFileName = templateFileName;
	}

	public String getTemplateFileEncoding() {
		return templateFileEncoding;
	}

	public void setTemplateFileEncoding(String templateFileEncoding) {
		this.templateFileEncoding = templateFileEncoding;
	}

	public File getTemplateFilePrimaryDir() {
		return templateFilePrimaryDir;
	}

	public void setTemplateFilePrimaryDir(File templateFilePrimaryDir) {
		this.templateFilePrimaryDir = templateFilePrimaryDir;
	}
}