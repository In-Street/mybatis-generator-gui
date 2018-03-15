package com.zzg.mybatis.generator.plugins;

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

import java.util.List;

public class ColumnSelfPlusMinusByMapPlugin extends PluginAdapter {

    /**
     * {@inheritDoc}
     */
    public boolean validate(List<String> warnings) {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean clientSelectByExampleWithBLOBsMethodGenerated(Method method, Interface interfaze,
                                                                 IntrospectedTable introspectedTable) {

        interfaze.addMethod(generateDeleteLogicByPrimaryKey(method, introspectedTable));
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean clientSelectByExampleWithoutBLOBsMethodGenerated(Method method, Interface interfaze,
                                                                    IntrospectedTable introspectedTable) {

        interfaze.addMethod(generateDeleteLogicByPrimaryKey(method, introspectedTable));
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean clientSelectByExampleWithBLOBsMethodGenerated(Method method, TopLevelClass topLevelClass,
                                                                 IntrospectedTable introspectedTable) {

        topLevelClass.addMethod(generateDeleteLogicByPrimaryKey(method, introspectedTable));
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean clientSelectByExampleWithoutBLOBsMethodGenerated(Method method, TopLevelClass topLevelClass,
                                                                    IntrospectedTable introspectedTable) {

        topLevelClass.addMethod(generateDeleteLogicByPrimaryKey(method, introspectedTable));
        return true;
    }

    @Override
    public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {

        String tableName = introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime();// 数据库表名

        XmlElement parentElement = document.getRootElement();

        XmlElement columnSelfPlusMinusByPrimaryKeyElement = new XmlElement("update");
        columnSelfPlusMinusByPrimaryKeyElement.addAttribute(new Attribute("id", "multiplePlusMinusByPrimaryKey"));

        List<IntrospectedColumn> primaryKeyColumns = introspectedTable.getPrimaryKeyColumns();
        IntrospectedColumn introspectedColumn = primaryKeyColumns.get(0);
        String primaryKey = introspectedColumn.getActualColumnName();

        columnSelfPlusMinusByPrimaryKeyElement.addElement(new TextElement("update " + tableName + System.lineSeparator() + "\t<set>" + System.lineSeparator() +
                "        <foreach collection=\"fieldMap.entrySet()\" index=\"key\" item=\"value\" separator=\",\">" + System.lineSeparator() +
                "            <if test=\"key != '" + primaryKey + "'.toString()\">" + System.lineSeparator() +
                "                ${key} =${key} ${value}" + System.lineSeparator() +
                "            </if>" + System.lineSeparator() +
                "        </foreach>" + System.lineSeparator() +
                "           where " + primaryKey + " = #{" + primaryKey + "}" + System.lineSeparator() +
                "    </set>"));

        parentElement.addElement(columnSelfPlusMinusByPrimaryKeyElement);

        return super.sqlMapDocumentGenerated(document, introspectedTable);
    }


    private Method generateDeleteLogicByPrimaryKey(Method method, IntrospectedTable introspectedTable) {

        Method m = new Method("multiplePlusMinusByPrimaryKey");

        m.setVisibility(method.getVisibility());

        m.setReturnType(FullyQualifiedJavaType.getIntInstance());
        FullyQualifiedJavaType mapInstance = FullyQualifiedJavaType.getNewMapInstance();
        mapInstance.addTypeArgument(FullyQualifiedJavaType.getStringInstance());
        mapInstance.addTypeArgument(FullyQualifiedJavaType.getObjectInstance());
        m.addParameter(new Parameter(mapInstance, "paramMap"));
        m.addJavaDocLine("/**" + System.lineSeparator() +
                "        eg:" + System.lineSeparator() +
                "        fieldMap 名称固定" + System.lineSeparator() +
                "        Map<String, Object> fieldMap = new HashMap<>();\n" +
                "        fieldMap.put(\"reply_num\", \"+1\");\n" +
                "        fieldMap.put(\"comments_num\", \"+1\");\n" +
                "\n" +
                "        Map<String, Object> paramMap = new HashMap<>();\n" +
                "        params.put(\"fieldMap\", fieldMap);\n" +
                "        params.put(\"id\", id);" + System.lineSeparator() + "*/");
        context.getCommentGenerator().addGeneralMethodComment(m, introspectedTable);
        return m;
    }
}