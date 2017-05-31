package ${package}; 

<#if '' != rootClass>import ${root};</#if>

public class ${objectname} <#if '' != rootClass>extends ${rootClass}</#if>{
	
	<#list tableInfos as info>
	/*
	* ${info.comment}
	*/
	private ${info.javaType} ${info.property};
	</#list>
	
	<#list tableInfos as info>
	/*
	* ${info.comment}
	*/
	private void ${info.writeMethod}(${info.javaType} ${info.property}){
		this.${info.property} = ${info.property} ;
	}
	</#list>
}