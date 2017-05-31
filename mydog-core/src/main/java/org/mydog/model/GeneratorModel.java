package org.mydog.model;

import java.io.File;
import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

public class GeneratorModel implements Serializable {
	
	private static final long serialVersionUID = 4762267857646618785L;

	private String type ;
	
	private String targetPackage ;
	
	private String root ;
	
	private String tmplate; 
	public String getType() {
		return type;
	}


	public String getTmplate() {
		return tmplate;
	}



	public void setTmplate(String tmplate) {
		this.tmplate = tmplate;
	}



	public void setType(String type) {
		this.type = type;
	}

	public String getTargetPackage() {
		return targetPackage;
	}

	public void setTargetPackage(String targetPackage) {
		this.targetPackage = targetPackage;
	}

	public String getRoot() {
		return root;
	}

	public void setRoot(String root) {
		this.root = root;
	}
	
	public String getFolder(){
		return StringUtils.replace( targetPackage, ".", File.separator) ; 
	}
}
