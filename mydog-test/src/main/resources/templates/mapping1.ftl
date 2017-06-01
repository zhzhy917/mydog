import ${package};

import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.type.JdbcType;
import ${modelPackage}.${modelName};
<#if '' != rootClass>import ${root};</#if>

public interface ${objectname} <#if '' != rootClass>extends ${rootClass}</#if>{
	@Delete({
		"delete from ${tableName}",
		"where id = ${r'#'}{${pri.property},jdbcType=${pri.type}}"
	})
	int deleteByPrimaryKey(${pri.javaType} ${pri.property});
	
	
	@Insert({
        "insert into ${tableName} (<#list tableInfos as info>${info.field}<#if info_has_next>,</#if></#list>)",
        "values (<#list tableInfos as info>${r'#'}{${info.property},jdbcType=${info.type}}<#if info_has_next>,</#if></#list>)"
    })
    int insert(${modelName} ${tableName});
    
    
     @Select({
        "select",
        "<#list tableInfos as info>${info.field}<#if info_has_next>,</#if></#list>",
        "from ${tableName}"
    })
    @Results({
    	<#list tableInfos as info>${r'@'}Result(column="${info.field}", property="${info.property}", jdbcType=JdbcType.${info.type} <#if 'PRI' == info.key>,id=true</#if>)
    	<#if info_has_next>,</#if></#list>
    })
    List<${modelName}> selectAll();
    
    
     @Select({
        "select",
        "<#list tableInfos as info>${info.field}<#if info_has_next>,</#if></#list>",
        "from ${tableName} where ${pri.field}=${r'#'}{${pri.property},jdbcType=${pri.type}}"
    })
    @Results({
    	<#list tableInfos as info>${r'@'}Result(column="${info.field}", property="${info.property}", jdbcType=JdbcType.${info.type} <#if 'PRI' == info.key>,id=true</#if>)
    	<#if info_has_next>,</#if></#list>
    })
    ${modelName} selectByPrimaryKey(${pri.javaType} ${pri.property});
    
     @Update({  
        "update ${tableName}" , 
        "set <#list tableInfos as info><#if 'PRI' != info.key>${info.field} = ${r'#'}{${info.property},jdbcType=${info.type}}<#if info_has_next>,</#if></#if></#list>",
        "where id = ${r'#'}{${pri.field},jdbcType=${pri.type}}" 
    })
    int updateByPrimaryKey(${modelName} ${tableName});
}