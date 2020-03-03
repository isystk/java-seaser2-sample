package com.isystk.sample.common.tool;

import java.util.Map;

public class DataGeneratorConfigTable {

	public String tableName;
	public String primaryKeyName;
	public Map<String, String> countUpColumnAndTableMap;
	public Map<String, String> columnAndSuffuxMap;

	public DataGeneratorConfigTable(String tableName, String primaryKeyName,
			Map<String, String> countUpColumnAndTableMap, Map<String, String> columnAndSuffuxMap) {
		this.tableName = tableName;
		this.primaryKeyName = primaryKeyName;
		this.countUpColumnAndTableMap = countUpColumnAndTableMap;
		this.columnAndSuffuxMap = columnAndSuffuxMap;
	}

}
