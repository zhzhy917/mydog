package org.mydog.config.utils;

import java.util.HashMap;
import java.util.Map;

public class DataType {
	public static Map<String, String> DATA_TYPE = new HashMap<>();

	static {
		DATA_TYPE.put("bigint", "Long");
		DATA_TYPE.put("binary", "byte[]");
		DATA_TYPE.put("binary", "byte[]");
		DATA_TYPE.put("blob", "byte[]");
		DATA_TYPE.put("char", "String");
		DATA_TYPE.put("date", "java.sql.Date");
		DATA_TYPE.put("datetime", "java.sql.Timestamp");
		DATA_TYPE.put("double", "Double");
		DATA_TYPE.put("enum", "String");
		DATA_TYPE.put("float", "Float");
		DATA_TYPE.put("int", "Integer");
		DATA_TYPE.put("longblob", "byte[]");
		DATA_TYPE.put("longtext", "String");
		DATA_TYPE.put("mediumblob", "byte[]");
		DATA_TYPE.put("mediumint", "Integer");
		DATA_TYPE.put("mediumtext", "String");
		DATA_TYPE.put("decimal", "java.math.BigDecimal");
		DATA_TYPE.put("double", "Double");
		DATA_TYPE.put("set", "String");
		DATA_TYPE.put("smallint", "Integer");
		DATA_TYPE.put("text", "String");
		DATA_TYPE.put("time", "java.sql.Time");
		DATA_TYPE.put("timestamp", "java.sql.Timestamp");
		DATA_TYPE.put("tinyblob", "byte[]");
		DATA_TYPE.put("tinyint", "Integer");
		DATA_TYPE.put("tinyint1", "Boolean");
		DATA_TYPE.put("tinytext", "String");
		DATA_TYPE.put("varbinary", "byte[]");
		DATA_TYPE.put("varchar", "String");
		DATA_TYPE.put("year", "java.sql.Date");
	}
}
