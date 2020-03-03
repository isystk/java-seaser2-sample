package com.isystk.sample.common.gen.solr;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;

import org.seasar.extension.jdbc.gen.internal.util.CloseableUtil;
import org.seasar.extension.jdbc.gen.internal.util.DeleteEmptyFileWriter;
import org.seasar.framework.util.StringUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class GenerateSchemaCommand {

	/** solrのホームディレクトリ */
	private String solrHomeDir;
	/** schemaから生成されるdtoのディレクトリ */
	private String outDir;
	/** FreeMakerのテンプレートを置いたディレクトリ */
	private String ftlDir;
	/** FreeMakerのテンプレートファイル名 */
	private String ftlName;
	/** 生成クラス名の語尾につける文字列 */
	private String outClassEndWrod;

	public void setSolrHomeDir(String solrHomeDir) {
		this.solrHomeDir = solrHomeDir;
	}

	public void setOutDir(String outDir) {
		this.outDir = outDir;
	}

	public void setFtlDir(String ftlDir) {
		this.ftlDir = ftlDir;
	}

	public void setFtlName(String ftlName) {
		this.ftlName = ftlName;
	}

	public void setOutClassEndWrod(String outClassEndWrod) {
		this.outClassEndWrod = outClassEndWrod;
	}

	/**
	 * 自動生成処理
	 */
	public void execute() {
		try {

			// コンフィグレーション
			Configuration cfg = new Configuration();
			// テンプレートを読み込み
			cfg.setDirectoryForTemplateLoading(new File(ftlDir));
			Template temp = cfg.getTemplate(ftlName);

			// solr.xmlからsolrのcore名を取得
			List<String> coreNameList = new ArrayList<String>();
			{
				Document geo = yRequest(solrHomeDir + "/solr.xml");
				// /solr
				Element solr = geo.getDocumentElement();
				checkElement(solr, "solr");
				// /solr /cores
				Element cores = firstElement(solr, "cores");
				// /solr /cores
				Element core = firstElement(cores, "core");
				while (core != null) {
					if (!"ring".equals(core.getAttribute("project"))) {
						coreNameList.add(core.getAttribute("instanceDir"));
					}
					core = nextElementLoop(core, "core");
				}
			}

			// schema.xmlからdtoを生成する
			for (String coreName : coreNameList) {

				// schema.xmlのパース
				Document geo = yRequest(solrHomeDir + "/" + coreName + "/conf/schema.xml");
				// /schema
				Element schema = geo.getDocumentElement();
				checkElement(schema, "schema");

				// /schema /types
				Element types = firstElement(schema, "types");
				// /schema /fields
				Element fields = nextElement(types, "fields");
				// /schema /fields /field
				Element field = firstElement(fields, "field");

				// テンプレートエンジンへ渡すDtoの作成
				GenerateSchemaCommand.SchemaDto schemaDto = new GenerateSchemaCommand.SchemaDto();
				schemaDto.coreName = firstToUpperCase(coreName);
				schemaDto.className = firstToUpperCase(coreName) + outClassEndWrod;

				List<GenerateSchemaCommand.SchemaDto.FieldDto> fieldDtoList = new ArrayList<GenerateSchemaCommand.SchemaDto.FieldDto>();
				while (field != null) {
					GenerateSchemaCommand.SchemaDto.FieldDto fieldDto = new GenerateSchemaCommand.SchemaDto.FieldDto();
					fieldDto.name = field.getAttribute("name");
					fieldDto.type = convertType(field.getAttribute("type"));
					fieldDto.indexed = Boolean.valueOf(field.getAttribute("indexed"));
					fieldDto.stored = Boolean.valueOf(field.getAttribute("stored"));
					fieldDto.multiValued = Boolean.valueOf(field.getAttribute("multiValued"));
					fieldDto.dynamicField = "dynamicField".equals(field.getTagName());
					fieldDtoList.add(fieldDto);

					field = nextElementLoop(field, "field", "dynamicField");
				}
				schemaDto.fieldDtoList = fieldDtoList;

				Map<String, SchemaDto> schemaDtoMap = new HashMap<String, SchemaDto>();
				schemaDtoMap.put("schemaDto", schemaDto);

				// テンプレート処理
				File file = new File(outDir + "/" + schemaDto.className + ".java");
				BufferedWriter bw = new BufferedWriter(new FileWriter(file));

				Writer writer = new DeleteEmptyFileWriter(bw, file);
				try {
					temp.process(schemaDtoMap, writer);
				} finally {
					CloseableUtil.close(writer);
				}
			}

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * schema.xmlからテンプレート生成に必要なパラメータ保持用
	 * 
	 * @author iseyoshitaka
	 * 
	 */
	public static class SchemaDto {

		/** core名 */
		public String coreName;
		/** クラス名 */
		public String className;
		/** schema.xmlのfieldリスト */
		public List<FieldDto> fieldDtoList;

		public String getCoreName() {
			return coreName;
		}

		public String getClassName() {
			return className;
		}

		public List<FieldDto> getFieldDtoList() {
			return fieldDtoList;
		}

		public static class FieldDto {
			/** field名 */
			public String name;
			/** field型 */
			public String type;
			/** indexed */
			public boolean indexed;
			/** stored */
			public boolean stored;
			/** multiValued true/false */
			public boolean multiValued;
			/** dynamicField true/false */
			public boolean dynamicField;

			public String getName() {
				return name;
			}

			public String getType() {
				return type;
			}

			public boolean isIndexed() {
				return indexed;
			}

			public boolean isStored() {
				return stored;
			}

			public boolean isMultiValued() {
				return multiValued;
			}

			public boolean isDynamicField() {
				return dynamicField;
			}

		}

	}

	/**
	 * parser
	 */
	private Element firstElement(Element e, String tagname) throws SAXException {
		Node n = e.getFirstChild();
		while (n != null && (n.getNodeType() != Node.ELEMENT_NODE || !n.getNodeName().equalsIgnoreCase(tagname))) {
			n = n.getNextSibling();
		}
		if (n == null)
			return e;
		return (Element) n;
	}

	private Element nextElement(Element e, String tagname) throws SAXException {
		Node n = e.getNextSibling();
		while (n != null && (n.getNodeType() != Node.ELEMENT_NODE || !n.getNodeName().equalsIgnoreCase(tagname))) {
			n = n.getNextSibling();
		}
		if (n == null)
			return e;
		return (Element) n;
	}

	private Element nextElementLoop(Element e, String tagname) throws SAXException {
		Node n = e.getNextSibling();
		while (n != null && (n.getNodeType() != Node.ELEMENT_NODE || !n.getNodeName().equalsIgnoreCase(tagname))) {
			n = n.getNextSibling();
		}
		return (Element) n;
	}

	private Element nextElementLoop(Element e, String tagname1, String tagname2) throws SAXException {
		Node n = e.getNextSibling();
		while (n != null && (n.getNodeType() != Node.ELEMENT_NODE
				|| (!n.getNodeName().equalsIgnoreCase(tagname1) && !n.getNodeName().equalsIgnoreCase(tagname2)))) {
			n = n.getNextSibling();
		}
		return (Element) n;
	}

	private void checkElement(Element e, String tagname) throws SAXException {
		if (!e.getTagName().equalsIgnoreCase(tagname)) {
			throw new SAXException(tagname);
		}
	}

	private Document yRequest(String url) throws SAXException, IOException {

		// LOG.info(url);
		try {
			return DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(url);
		} catch (ParserConfigurationException e) {
			throw new IOException(e);
		} catch (FactoryConfigurationError e) {
			throw new IOException(e);
		}
	}

	/**
	 * 1文字目を大文字に変換
	 * 
	 * @param str
	 * @return
	 */
	private String firstToUpperCase(String str) {
		if (StringUtil.isEmpty(str))
			return str;
		return str.substring(0, 1).toUpperCase() + str.substring(1);
	}

	/**
	 * schema.xmlのfieldTypeをjavaで使用する型に変換
	 * 
	 * @param type
	 * @return
	 */
	private String convertType(String type) {
		if ("string".equals(type) || "text_ja".equals(type) || "text_ws".equals(type) || "text_ws2".equals(type)) {
			return "String";
		} else if ("int".equals(type) || "sint".equals(type) || "tint".equals(type)) {
			return "Integer";
		} else if ("date".equals(type) || "sdate".equals(type) || "tdate".equals(type)) {
			return "Date";
		} else if ("double".equals(type) || "tdouble".equals(type) || "pdouble".equals(type)
				|| "sdouble".equals(type)) {
			return "BigDecimal";
		} else if ("random".equals(type)) {
			return "random";
		}
		return null;
	}

}
