package org.mydog.model;

import java.io.Serializable;

public class TableModel implements Serializable {
	
	private static final long serialVersionUID = 1766187215300907854L;

	private String domainObjectName  ;
	
	private String tableName ;
	
	private String name ; 
	
	public String getDomainObjectName() {
		return domainObjectName;
	}
	
	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public void setDomainObjectName(String domainObjectName) {
		this.domainObjectName = domainObjectName;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	@Override
	public String toString() {
		return "TableModel [domainObjectName=" + domainObjectName + ", tableName=" + tableName + ", name=" + name + "]";
	} 
}
