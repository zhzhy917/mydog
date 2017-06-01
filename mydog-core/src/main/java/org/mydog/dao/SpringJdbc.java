package org.mydog.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.mydog.config.utils.DataSourceTools;
import org.mydog.config.utils.KeyMap;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class SpringJdbc {

	protected JdbcTemplate jdbc = null;

	protected DataSource dataSource;

	public SpringJdbc() {
		TransactionSynchronizationManager.initSynchronization();
		dataSource = DataSourceTools.getDataSource();
		jdbc = new JdbcTemplate(dataSource);
	}

	public List<Map<String, Object>> query(String sql) {
		try {
			List<Map<String, Object>> query = jdbc.query(sql, new RowMapper<Map<String, Object>>() {
				@Override
				public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
					Map<String, Object> data = new KeyMap<Object>();
					ResultSetMetaData rsmd = rs.getMetaData();
					int length = rsmd.getColumnCount();
					for (int x = 0; x < length; x++) {
						data.put(rsmd.getColumnLabel(x + 1), rs.getObject(x + 1));
					}
					return data;
				}

			});
			return query;
		} finally {
			DataSourceUtils.releaseConnection(DataSourceUtils.getConnection(dataSource), dataSource);
		}
	}

	public Map<String, Class<?>> queryTableMeta(String tableName) {
		Map<String, Class<?>> data = new KeyMap<Class<?>>();
		Connection connection = DataSourceUtils.getConnection(dataSource);
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = connection.prepareStatement(String.format("select * from %s", tableName));
			rs = ps.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int cloumnLength = rsmd.getColumnCount(); 
			while(rs.next()){
				for(int x=0;x<cloumnLength;x++){
					data.put(rsmd.getColumnLabel(x+1), rs.getObject(x+1).getClass()) ;
				}
			}
		} catch (Exception e) {
			e.printStackTrace(); 
		} finally {
			JdbcUtils.closeResultSet(rs);
			JdbcUtils.closeStatement(ps);
			DataSourceUtils.releaseConnection(connection, dataSource);
		}
		return data;
	}

}
