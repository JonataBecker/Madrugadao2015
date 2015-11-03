package br.feevale.madrugadao.api;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.JSONArray;
import org.json.JSONObject;

import br.feevale.madrugadao.db.SQLCreator;

public class EntidadeDAO {

	public final Connection conn;
	public final String sql;

	public EntidadeDAO(Connection conn, SQLCreator sql) {
		this(conn, sql.create());
	}

	public EntidadeDAO(Connection conn, String sql) {
		this.conn = conn;
		this.sql = sql;
	}

	public String busca() throws SQLException {
		ResultSet resultSet = null;
		Statement statement = null;
		try {
			JSONArray arr = new JSONArray();
	        statement = conn.createStatement();
	        resultSet = statement.executeQuery(sql);
	        ResultSetMetaData resultSetMeta = resultSet.getMetaData();
	        while(resultSet.next()) {
	        	int numColumns = resultSetMeta.getColumnCount();
	        	JSONObject obj = new JSONObject();
	            for (int i=1; i<numColumns+1; i++) {
	            	obj.put(resultSetMeta.getColumnName(i), resultSet.getObject(i));
	            }
	            arr.put(obj);
	        }
	        return arr.toString();
		} catch (SQLException e) {
			throw e;
		} finally {
			if (resultSet != null) {
				resultSet.close();
			}
			if (statement != null) {
				statement.close();
			}
		}
	}
}
