package org.mydog.config.utils;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.mydog.loader.xml.XmlConfigLoader;
import org.mydog.model.JdbcConfig;

public class DataSourceTools {

	protected static BasicDataSource dataSource;
	
	public static DataSource getDataSource() {
		synchronized (Thread.class) {
			if (null == dataSource) {
				JdbcConfig jdbcConfig = XmlConfigLoader.getLoader().getJdbcConfig();
				dataSource = new BasicDataSource();
				dataSource.setUrl(jdbcConfig.getConnectionUrl());
				dataSource.setUsername(jdbcConfig.getUsername());
				dataSource.setPassword(jdbcConfig.getPassword());
				dataSource.setDriverClassName(jdbcConfig.getDriverClass());
			}
		}
		return dataSource;
	}
}
