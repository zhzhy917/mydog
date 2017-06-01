${r'<'}?xml version="1.0" encoding="UTF-8"?${r'>'}
${r'<'}!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd"${r'>'}
<#-- 文档开始 -->
${r'<'}mapper namespace="${mappingPackage}.${mappingName}"${r'>'}
	<#-- 定义BaseResultMap 开始 -->
  ${r'<'}resultMap id="BaseResultMap" type="${modelPackage}.${modelNname}"${r'>'}
  	<#list tableInfos as info>
    	${r'<'}id column="${info.field}" jdbcType="${info.type}" property="${info.property}" /${r'>'}
    </#list>
  ${r'<'}/resultMap${r'>'}
  <#-- 定义BaseResultMap 结束-->
  
   <#-- 定义表字段信息 开始-->
  ${r'<'}sql id="Base_Column_List"${r'>'}
    <#list tableInfos as info>${info.field}<#if info_has_next>,</#if></#list>
  ${r'<'}/sql${r'>'}
   <#-- 定义表字段信息 结束-->
   <#-- 根据主键查询 开始-->
  ${r'<'}select id="selectByPrimaryKey" parameterType="${pri.javaType}" resultMap="BaseResultMap"${r'>'}
    select 
    ${r'<'}include refid="Base_Column_List" /${r'>'}
    from ${tableName} where ${pri.field} = ${r'#'}{${pri.property},jdbcType=${pri.type}}
  ${r'<'}/select${r'>'}
   <#-- 根据主键查询 结束 --> 
   
   <#-- 根据主键删除开始-->
  ${r'<'}delete id="deleteByPrimaryKey" parameterType="${pri.javaType}"${r'>'}
    delete from ${tableName} where ${pri.field} = ${r'#'}{${pri.property},jdbcType=${pri.type}}
  ${r'<'}/delete${r'>'}
   <#-- 根据主键删除结束-->
   <#-- 根据主键插入开始-->
  ${r'<'}insert id="insert" parameterType="${modelPackage}.${modelNname}"${r'>'}
    insert into users (<#list tableInfos as info>${info.field}<#if info_has_next>,</#if></#list>)
    values (<#list tableInfos as info>${r'#'}{${info.property},jdbcType=${info.type}}<#if info_has_next>,</#if></#list>)
  ${r'<'}/insert${r'>'}
  <#-- 根据主键插入结束-->
   <#-- 根据主键修改开始-->
     ${r'<'}update id="updateByPrimaryKey" parameterType="${modelPackage}.${modelNname}" ${r'>'}
     	 "update ${tableName}" , 
        "set <#list tableInfos as info><#if 'PRI' != info.key>${info.field} = ${r'#'}{${info.property},jdbcType=${info.type}}<#if info_has_next>,</#if></#if></#list>",
        "where id = ${r'#'}{${pri.field},jdbcType=${pri.type}}"
     ${r'<'}/update${r'>'}
   <#-- 根据主键修改结束-->
${r'<'}/mapper${r'>'}