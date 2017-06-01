package org.mydog.core;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.mydog.config.utils.DataType;
import org.mydog.config.utils.KeyMap;
import org.mydog.config.utils.StringTools;
import org.mydog.dao.SpringJdbc;
import org.mydog.loader.xml.XmlConfigLoader;
import org.mydog.logs.LogManager;
import org.mydog.model.GeneratorModel;
import org.mydog.model.TableModel;
import org.mydog.tmplate.engine.FreemarkerEngine;

public class MydogDataHanlder {

	protected static MydogDataHanlder dataHanlder = new MydogDataHanlder();

	public static MydogDataHanlder getInstance() {
		return dataHanlder;
	}

	final String SHOW_TABLE_DESC_SQL = "SHOW FULL COLUMNS FROM %s";

	private Map<String, Object> dataMap = new KeyMap<Object>();

	public Map<String, Object> getData() {
		return dataMap;
	}

	public MydogDataHanlder generate() {
		XmlConfigLoader configLoader = XmlConfigLoader.getLoader();
		Map<String, TableModel> tableMap = configLoader.getTables();
		Map<String, List<GeneratorModel>> generatorModelMap = configLoader.getGeneratorModel();
		for (Map.Entry<String, TableModel> entry : tableMap.entrySet()) {
			SpringJdbc jdbc = new SpringJdbc();
			Map<String, Object> data = new KeyMap<Object>();
			List<Map<String, Object>> tableColumns = jdbc.query(String.format(SHOW_TABLE_DESC_SQL, entry.getKey()));
			for (Map<String, Object> tableColumn : tableColumns) {
				String type = toType(StringTools.toString(tableColumn.get("type")));
				String field = StringTools.toString(tableColumn.get("field"));
				String javaType = DataType.DATA_TYPE.get(type);
				tableColumn.put("javaType", javaType);
				tableColumn.put("property", StringTools.toProperty(field));
				tableColumn.put("readMethod", getMethodName(field, javaType, MethodType.READ));
				tableColumn.put("writeMethod", getMethodName(field, javaType, MethodType.WRITE));
				tableColumn.put("type", type.toUpperCase().replace("TINYINT1", "TINYINT"));
				tableColumn.remove("collation");
				tableColumn.remove("null");
				tableColumn.remove("default");
				tableColumn.remove("privileges");
				if(StringUtils.equalsIgnoreCase("PRI", StringTools.toString(tableColumn.get("key")))){
					data.put("pri", tableColumn);
				}
			}
			data.put("tableInfos", tableColumns);
			data.put("tableName", entry.getKey());
			data.putAll(configLoader.getVarMap());
			data.put("ObjectName", StringUtils.isBlank(entry.getValue().getDomainObjectName())
					? StringTools.toClassName(entry.getKey()) : entry.getValue().getDomainObjectName());
			for (Map.Entry<String, List<GeneratorModel>> generatorEntry : generatorModelMap.entrySet()) {
				for (GeneratorModel generatorModel : generatorEntry.getValue()) {
					generateFile(data, generatorModel);
				}
			}
		}
		return this;
	}

	protected void generateFile(Map<String, Object> data, GeneratorModel model) {
		XmlConfigLoader configLoader = XmlConfigLoader.getLoader();
		data.put("package", model.getTargetPackage()); 
		File destFile = new File(configLoader.getOutputDirectory(), "generate-source/" + model.getFolder());
		String objectname = StringTools.toString(data.get("objectname"));
		String thisClassName = null ;
		if (StringUtils.equalsAnyIgnoreCase(model.getType(), "model")) {
			thisClassName = objectname ;
			destFile = new File(destFile, thisClassName + ".java");
		} else if (StringUtils.equalsAnyIgnoreCase(model.getType(), "mappping", "service", "controller", "dao",
				"daoImpl")) {
			thisClassName =  objectname + StringTools.toClassName(model.getType())  ;
			
			destFile = new File(destFile, objectname + StringTools.toClassName(model.getType()) + ".java");
		} else {
			if(model.getType().indexOf(".") > -1){
				destFile = new File(destFile, objectname+ model.getType());
			}else{
				destFile = new File(destFile, objectname + "." +  model.getType());
			}
		}
		data.put("thisClassName", thisClassName);
		data.put(model.getType() + "Name", objectname);
		data.put(model.getType() + "Package", model.getTargetPackage());
		data.put("root", model.getRoot());
		if(StringUtils.isNoneBlank(model.getRoot())){
			data.put("rootClass", StringTools.getClassName(model.getRoot()) );
		}
		LogManager.info("当前数据：" + data);
		if (StringUtils.isNotEmpty(model.getTmplate())) {
			FreemarkerEngine.process(data, model.getTmplate(), destFile.getAbsolutePath());
		}
	}

	protected enum MethodType {
		WRITE, READ
	}

	protected String getMethodName(String field, String type, MethodType methodType) {
		if (methodType == MethodType.READ) {
			String readHead = "set";
			return readHead + StringTools.toClassName(field);
		}
		String writeHead = "get";
		if (StringUtils.equalsAnyIgnoreCase("Boolean", type)) {
			writeHead = "is";
		}
		return writeHead + StringTools.toClassName(field);
	}

	protected String toType(String type) {
		int index = type.indexOf("(");
		if (index > -1) {
			String headType = type.substring(0, index);
			if (StringUtils.equalsAnyIgnoreCase("tinyint", headType)) {
				int length = NumberUtils.toInt(type.substring(index + 1, type.length() - 1));
				if (length == 1) {
					headType = headType + length;
				}
			}
			type = headType;
		}
		return type;
	}

}
