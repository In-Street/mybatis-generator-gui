package com.zzg.mybatis.generator.plugins;

import java.util.List;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

public class ColumnSelfPlusMinusPlugin extends PluginAdapter {

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 */
	public boolean validate(List<String> warnings) {
		return true;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public boolean clientSelectByExampleWithBLOBsMethodGenerated(Method method, Interface interfaze,
			IntrospectedTable introspectedTable) {

		interfaze.addMethod(generateDeleteLogicByIds(method, introspectedTable));
		interfaze.addMethod(generateDeleteLogicByPrimaryKey(method, introspectedTable));
		return true;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public boolean clientSelectByExampleWithoutBLOBsMethodGenerated(Method method, Interface interfaze,
			IntrospectedTable introspectedTable) {

		interfaze.addMethod(generateDeleteLogicByIds(method, introspectedTable));
		interfaze.addMethod(generateDeleteLogicByPrimaryKey(method, introspectedTable));
		return true;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public boolean clientSelectByExampleWithBLOBsMethodGenerated(Method method, TopLevelClass topLevelClass,
			IntrospectedTable introspectedTable) {

		topLevelClass.addMethod(generateDeleteLogicByIds(method, introspectedTable));
		topLevelClass.addMethod(generateDeleteLogicByPrimaryKey(method, introspectedTable));
		return true;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public boolean clientSelectByExampleWithoutBLOBsMethodGenerated(Method method, TopLevelClass topLevelClass,
			IntrospectedTable introspectedTable) {

		topLevelClass.addMethod(generateDeleteLogicByIds(method, introspectedTable));
		topLevelClass.addMethod(generateDeleteLogicByPrimaryKey(method, introspectedTable));
		return true;
	}

	@Override
	public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {

		String tableName = introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime();// 数据库表名

		XmlElement parentElement = document.getRootElement();

		// 产生分页语句前半部分

		XmlElement columnSelfPlusMinusElement = new XmlElement("update");
		columnSelfPlusMinusElement.addAttribute(new Attribute("id", "selfPlusMinus"));

		columnSelfPlusMinusElement.addElement(new TextElement("update " + tableName
				+ "\r\n \t  set ${columnName} = ${columnName} ${operator} " + " ${count} "
				+ "\r\n\t  <if test=\"_parameter != null\">\r\n\t\t<include refid=\"Update_By_Example_Where_Clause\" />\r\n\t  </if>"));

		parentElement.addElement(columnSelfPlusMinusElement);
		
		XmlElement columnSelfPlusMinusByPrimaryKeyElement = new XmlElement("update");
		columnSelfPlusMinusByPrimaryKeyElement.addAttribute(new Attribute("id", "selfPlusMinusByPrimaryKey"));

        List<IntrospectedColumn> primaryKeyColumns = introspectedTable.getPrimaryKeyColumns();
        IntrospectedColumn introspectedColumn = primaryKeyColumns.get(0);
        String primaryKey = introspectedColumn.getActualColumnName();

        columnSelfPlusMinusByPrimaryKeyElement.addElement(new TextElement("update " + tableName
				+ "\r\n \t  set ${columnName} = ${columnName} ${operator} " + " ${count} "
				+ "\r\n\t  where "+primaryKey+" = ${id}"));
		parentElement.addElement(columnSelfPlusMinusByPrimaryKeyElement);
		
		return super.sqlMapDocumentGenerated(document, introspectedTable);
	}

	private Method generateDeleteLogicByIds(Method method, IntrospectedTable introspectedTable) {

		Method m = new Method("selfPlusMinus");

		m.setVisibility(method.getVisibility());

		m.setReturnType(FullyQualifiedJavaType.getIntInstance());
		m.addParameter(
				new Parameter(FullyQualifiedJavaType.getStringInstance(), "columnName", "@Param(\"columnName\")"));
		m.addParameter(new Parameter(FullyQualifiedJavaType.getStringInstance(), "operator", "@Param(\"operator\")"));
		m.addParameter(new Parameter(FullyQualifiedJavaType.getIntInstance(), "count", "@Param(\"count\")"));
		m.addParameter(new Parameter(FullyQualifiedJavaType.getObjectInstance(), "example", "@Param(\"example\")"));
		context.getCommentGenerator().addGeneralMethodComment(m, introspectedTable);
		return m;
	}

	private Method generateDeleteLogicByPrimaryKey(Method method, IntrospectedTable introspectedTable) {

		Method m = new Method("selfPlusMinusByPrimaryKey");

		m.setVisibility(method.getVisibility());

		m.setReturnType(FullyQualifiedJavaType.getIntInstance());
		m.addParameter(
				new Parameter(FullyQualifiedJavaType.getStringInstance(), "columnName", "@Param(\"columnName\")"));
		m.addParameter(new Parameter(FullyQualifiedJavaType.getStringInstance(), "operator", "@Param(\"operator\")"));
		m.addParameter(new Parameter(FullyQualifiedJavaType.getIntInstance(), "count", "@Param(\"count\")"));
		m.addParameter(new Parameter(FullyQualifiedJavaType.getIntInstance(), "id", "@Param(\"id\")"));
		context.getCommentGenerator().addGeneralMethodComment(m, introspectedTable);
		return m;
	}
}