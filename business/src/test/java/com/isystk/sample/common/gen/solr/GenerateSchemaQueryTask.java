package com.isystk.sample.common.gen.solr;


public class GenerateSchemaQueryTask {


    /**
     * テンプレートを処理する。
     * @param args 引数
     * @throws Exception
     */
    public static void main(String[] args) {

	GenerateSchemaCommand command = new GenerateSchemaCommand();
	command.setSolrHomeDir("C:/isystk/docker-java/src/web-solr/search");
	command.setOutDir("src/main/java/com/isystk/sample/common/s2/solr/query");
	command.setFtlDir("src/test/java/com/isystk/sample/common/gen/solr");
	command.setFtlName("schemaQuery.ftl");
	command.setOutClassEndWrod("QueryBuilderBase");
	command.execute();

    }


}
