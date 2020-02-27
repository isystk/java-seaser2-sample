package com.isystk.sample.common.gen.solr;


public class GenerateSchemaDtoTask {


    /**
     * テンプレートを処理する。
     * @param args 引数
     * @throws Exception
     */
    public static void main(String[] args) {

	GenerateSchemaCommand command = new GenerateSchemaCommand();
	command.setSolrHomeDir("C:/teamlab/projects/docker-java7/src/web-solr/search");
	command.setOutDir("src/main/java/com/isystk/sample/common/s2/solr/dto");
	command.setFtlDir("src/test/java/com/isystk/sample/common/gen/solr");
	command.setFtlName("schemaDto.ftl");
	command.setOutClassEndWrod("SearchDto");
	command.execute();

    }


}
