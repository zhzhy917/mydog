package org.mydog.core;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;

public class JarTools {
	
	protected static Method addURL = null ;
	static{
		try {
			addURL = URLClassLoader.class.getDeclaredMethod("addURL", new Class[] { URL.class }) ; 
			addURL.setAccessible(true);
		} catch (Exception e) {
		}
	}
	
	/**
	 * 动态加载Jar包到内存中
	 * */
	public static Object loadJar(String jarFile, String className) {
		try {
			File file = new File( jarFile ); 
			if (!file.exists()) {
				return null ; 
			}
			addURL.invoke(ClassLoader.getSystemClassLoader(), new Object[] { file.toURI().toURL() }); 
			if(!StringUtils.isBlank(className)){
			return Class.forName( className ,false , ClassLoader.getSystemClassLoader() ).newInstance();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
		}
		return null;
	}

	public static void main(String[] args) throws Exception {
		Object loadJar = loadJar("C:/commons-io-2.4.jar", "org.apache.commons.io.Charsets") ;
		Field field = loadJar.getClass().getField("UTF_8"); 
		System.out.println(FieldUtils.readDeclaredStaticField(loadJar.getClass(), field.getName()));
	}
}
