package org.mydog.config.utils;

public class StringTools {

	public static String toString(Object obj){
		return null == obj ? "" : obj.toString().trim() ;
	}
	
	public static String toClassName(String text){
		String frist = text.substring(0 , 1) ;
		String end = text.substring(1);
		String[] splits = end.split("_");
		StringBuffer buffer = new StringBuffer();
		buffer.append(frist.toUpperCase()) ;
		for(int x=0;x<splits.length;x++){
			String split = splits[x];
			if(x>0){
				split = toClassName(split) ;
			}
			buffer.append(split);
		}
		return buffer.toString() ;
	}
	
	
	public static String getClassName(String className){
		int lastIndexOf = className.lastIndexOf(".");
		if(lastIndexOf > -1){
			return className.substring(lastIndexOf + 1) ;
		}
		return className;
	}
	
	public static String toProperty(String text){
		String frist = text.substring(0 , 1) ;
		String end = text.substring(1);
		String[] splits = end.split("_");
		StringBuffer buffer = new StringBuffer();
		buffer.append(frist.toLowerCase()) ;
		for(int x=0;x<splits.length;x++){
			String split = splits[x];
			if(x>0){
				split = toClassName(split) ;
			}
			buffer.append(split);
		}
		return buffer.toString() ;
	}
	
}
