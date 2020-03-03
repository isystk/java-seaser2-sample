package com.isystk.sample.common.tool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataGenerator {

	private Connection connection;

	private Map<String, Long> maxTableIdMap = new HashMap<String, Long>();

	public DataGenerator(String url, String userName, String pass) {
		try {
			this.connection = DriverManager.getConnection(url, userName, pass);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void execute(DataGeneratorConfig config) {
		try {
			connection.setAutoCommit(false);

			LockTables(config);

			// MaxIdの取得
			for (DataGeneratorConfigTable configTable : config.tables) {
				if (configTable.primaryKeyName != null) { // プライマリキーがないテーブルがある
					maxTableIdMap.put(configTable.tableName, getMaxId(configTable));
				}
			}

			for (DataGeneratorConfigTable configTable : config.tables) {
				generateTableData(configTable);
			}

			connection.commit();
			// connection.rollback(); // for test

			System.out.println("Data Generate Success.");
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
	}

	private void LockTables(DataGeneratorConfig config) {
		try {
			Statement st = connection.createStatement();
			StringBuffer sql = new StringBuffer();
			sql.append("LOCK TABLES ");

			boolean isFirst = true;
			for (DataGeneratorConfigTable configTable : config.tables) {
				if (isFirst == false) {
					sql.append(", ");
				} else {
					isFirst = false;
				}

				sql.append(configTable.tableName);
				sql.append(" WRITE ");
				sql.append(", ");
				sql.append(configTable.tableName);
				sql.append(" as readt_");
				sql.append(configTable.tableName);
				sql.append(" WRITE ");
			}

			sql.append(";");

			System.out.println(sql.toString());
			st.execute(sql.toString());
			st.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	private void generateTableData(DataGeneratorConfigTable configTable) {
		try {

			Statement st = connection.createStatement();
			StringBuffer sql = new StringBuffer();
			sql.append("insert into ");
			sql.append(configTable.tableName);
			sql.append(" (select  ");
			sql.append(createInsertColumnSQL(configTable));
			sql.append(" from ");
			sql.append(configTable.tableName);
			sql.append(" as readt_");
			sql.append(configTable.tableName);
			sql.append(");");

			System.out.println(sql.toString());
			st.execute(sql.toString());
			st.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	private String createInsertColumnSQL(DataGeneratorConfigTable configTable) {
		List<String> columnList = getColumnList(configTable);

		StringBuffer result = new StringBuffer();

		for (String column : configTable.countUpColumnAndTableMap.keySet()) {
			if (columnList.contains(column) == false) {
				throw new RuntimeException(
						"Column is Not Found.TABLE=" + configTable.tableName + "' COLUMN='" + column + "'");
			}
		}

		boolean isFirst = true;
		for (String columName : columnList) {
			if (isFirst == false) {
				result.append(", ");
			} else {
				isFirst = false;
			}

			if (columName.equalsIgnoreCase(configTable.primaryKeyName)) {
				result.append(columName);
				result.append(" + ");
				result.append(maxTableIdMap.get(configTable.tableName));
				result.append(" ");
				result.append(" as ");
			} else if (configTable.countUpColumnAndTableMap.get(columName) != null) {
				result.append(columName);
				result.append(" + ");

				Long plusCount = maxTableIdMap.get(configTable.countUpColumnAndTableMap.get(columName));
				if (plusCount == null) {
					System.out.println(
							configTable.countUpColumnAndTableMap.get(columName) + " is Not Found. No Use Count Up.");
					plusCount = (long) 0;
				}

				result.append(plusCount);
				result.append(" ");
				result.append(" as ");
			} else if (configTable.columnAndSuffuxMap.get(columName) != null) {
				result.append(" concat(");
				result.append(configTable.primaryKeyName);
				result.append(" + ");
				result.append(maxTableIdMap.get(configTable.tableName));
				result.append(" , ");
				result.append(configTable.columnAndSuffuxMap.get(columName));
				result.append(") ");
				result.append(" as ");
			}
			result.append(columName);
		}
		// "prefecture_id + 105
		// , name
		// , sort "

		return result.toString();
	}

	private List<String> getColumnList(DataGeneratorConfigTable configTable) {
		try {
			Statement st = connection.createStatement();
			String sql = "select * from " + configTable.tableName;
			ResultSet rs = st.executeQuery(sql);

			List<String> result = new ArrayList<String>();

			for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
				result.add(rs.getMetaData().getColumnName(i));
			}

			rs.close();
			st.close();
			return result;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	private Long getMaxId(DataGeneratorConfigTable configTable) {
		try {
			Statement st = connection.createStatement();
			String sql = "select max(" + configTable.primaryKeyName + ") as mx from " + configTable.tableName;
			System.out.println(sql);
			ResultSet rs = st.executeQuery(sql);
			rs.next();
			Long result = rs.getLong("mx");
			rs.close();
			st.close();
			return result;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
