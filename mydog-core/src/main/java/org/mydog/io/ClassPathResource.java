package org.mydog.io;

import java.io.File;

public class ClassPathResource {
	
	
	public static String getPath(String path){
		File file = new File(path);
		if(file.exists()){
			return file.getAbsolutePath();
		}
		return ClassPathResource.class.getResource(path).getPath() ;
	}
	
}
