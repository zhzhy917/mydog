package org.mydog.dao;

import java.util.List;
import java.util.Map;

import org.mydog.config.utils.KeyMap;
import org.mydog.loader.xml.XmlConfigLoader;

public class SpringJdbcTest {

	public static void main(String[] args) throws Exception {
		String schemaFile = "C:\\Users\\Administrator\\git\\mydog-autocode\\mydog-test\\src\\main\\resources\\generatorConfig.xml" ;
		XmlConfigLoader.getLoader().setSchemaFile(schemaFile).load(); 
		SpringJdbc springJdbc = new SpringJdbc(); 
		List<Map<String, Object>> datas = springJdbc.query("SHOW FULL COLUMNS FROM ttt");
		Map<String,String> keyMap = new KeyMap<>();
		for(Map<String, Object> data:datas){
			System.out.println( data );
			String type = data.get("type").toString(); 
			int index = type.indexOf("(") ;
			if(index>0){
				type = type.substring(0, index);
			}
			keyMap.put(data.get("field").toString(), type); 
		}
		
		Map<String, Class<?>> metas = springJdbc.queryTableMeta("ttt");
		
		System.out.println(metas); 
		
		for(String key:keyMap.keySet()){
			String classType = metas.get(key).getName() ;
			if("[B".equalsIgnoreCase(classType)){
				classType = "byte[]" ;
			}
			System.out.println("DATA_TYPE.put(\"" + keyMap.get(key) + "\",\""
					+ classType + "\");"); 
			
			
		}
		
	}
}
