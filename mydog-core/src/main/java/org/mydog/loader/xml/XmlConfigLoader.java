package org.mydog.loader.xml;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.mydog.config.utils.ArrayMap;
import org.mydog.config.utils.KeyMap;
import org.mydog.config.utils.StringTools;
import org.mydog.config.utils.XmlUtil;
import org.mydog.core.JarTools;
import org.mydog.model.CommentModel;
import org.mydog.model.GeneratorModel;
import org.mydog.model.JdbcConfig;
import org.mydog.model.TableModel;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class XmlConfigLoader implements ConfigLoader {

	private static XmlConfigLoader configLoader;
	
	private Map<String,String> varMap = new KeyMap<>();
	public static XmlConfigLoader getLoader() {
		synchronized (Thread.class) {
			configLoader = null == configLoader ? new XmlConfigLoader() : configLoader;
		}
		return configLoader;
	}

	private String schemaFile;
	
	private File outputDirectory ;

	private CommentModel commentModel = new CommentModel();
	private JdbcConfig jdbcConfig = new JdbcConfig();

	private ArrayMap<String, GeneratorModel> generators = new ArrayMap<String, GeneratorModel>();
	private Map<String, TableModel> tables = new KeyMap<TableModel>();

	public void load() {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(schemaFile);
			Element root = XmlUtil.getDocument(fis).getDocumentElement();
			loadClassPathEntry(root);

			loadCommentModel(root);

			loadJdbcConnection(root);

			loadGenerators(root);

			loadTable(root);
			
			loadVars(root);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			IOUtils.closeQuietly(fis);
		}
	}

	private void loadVars(Element root) {
		Element e = XmlUtil.getElement(root, "properties");
		if(null != e){
			varMap = XmlUtil.loadProperty(e);
		}
	}

	private void loadTable(Element root) {
		Element e = XmlUtil.getElement(root, "tables");
		List<Element> elements = XmlUtil.getElements(e, "table");
		for (Element element : elements) {
			TableModel tableModel = new TableModel();
			Map<String, String> attributes = XmlUtil.loadAttribute(element);
			String tableName = attributes.get("tableName");
			String domainObjectName = attributes.get("domainObjectName");
			String name = attributes.get("name");

			tableModel.setDomainObjectName(
					StringUtils.isBlank(domainObjectName) ? StringTools.toClassName(tableName) : domainObjectName);
			tableModel.setName(StringUtils.isBlank(name) ? tableName : name);
			tableModel.setTableName(tableName);
			tables.put(tableName, tableModel);
		}
	}

	private void loadGenerators(Element root) {
		Element e = XmlUtil.getElement(root, "generators");
		List<Element> elements = XmlUtil.getElements(e, "generator");
		for (Element element : elements) {
			Map<String, String> attributes = XmlUtil.loadAttribute(element);
			GeneratorModel generator = new GeneratorModel();
			generator.setRoot(attributes.get("root"));
			generator.setTargetPackage(attributes.get("targetPackage"));
			generator.setType(attributes.get("type"));
			generator.setTmplate(attributes.get("tmplate"));
			generators.putItem(generator.getType(), generator);
		}
	}

	private void loadJdbcConnection(Element root) {
		Element e = XmlUtil.getElement(root, "jdbcConnection");
		Map<String, String> attributes = XmlUtil.loadAttribute(e);
		jdbcConfig.setConnectionUrl(attributes.get("connectionUrl"));
		jdbcConfig.setDriverClass(attributes.get("driverClass"));
		jdbcConfig.setPassword(attributes.get("password"));
		jdbcConfig.setUsername(attributes.get("username"));
	}

	private void loadClassPathEntry(Element root) {
		Element element = XmlUtil.getElement(root, "classPathEntry");
		Map<String, String> properties = XmlUtil.loadAttribute(element);
		String location = properties.get("location");
		if (StringUtils.isNoneBlank(location)) {
			String[] splits = StringUtils.split(location, ',');
			for (String split : splits) {
				JarTools.loadJar(split, null);
			}
		}
	}

	private void loadCommentModel(Element root) {
		NodeList nodeList = root.getElementsByTagName("commentGenerator");

		if (null != nodeList) {
			int length = nodeList.getLength();
			if (length > 0) {
				Element element = (Element) nodeList.item(0);
				Map<String, String> properties = XmlUtil.loadProperty(element);
				String suppressDate = properties.get("suppressDate");
				String suppressComment = properties.get("suppressComment");
				commentModel.setSuppressComment("true".equals(suppressComment));
				commentModel.setSuppressDate("true".equals(suppressDate));
			}
		}
	}

	@Override
	public CommentModel getCommentModel() {
		return commentModel;
	}

	@Override
	public Map<String, List<GeneratorModel>> getGeneratorModel() {
		return generators;
	}

	public JdbcConfig getJdbcConfig() {
		return jdbcConfig;
	}

	public Map<String, TableModel> getTables() {
		return tables;
	}

	public XmlConfigLoader setSchemaFile(String schemaFile) {
		this.schemaFile = schemaFile;
		return this;
	}

	public File getOutputDirectory() {
		return outputDirectory;
	}

	public XmlConfigLoader setOutputDirectory(File outputDirectory) {
		this.outputDirectory = outputDirectory;
		return this;
	}

	public Map<String, String> getVarMap() {
		return varMap;
	}

}
