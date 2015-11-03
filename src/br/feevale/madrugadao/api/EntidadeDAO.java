package br.feevale.madrugadao.api;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
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
		PreparedStatement preparedStatement = null;
		try {
			JSONArray arr = new JSONArray();
	        preparedStatement = conn.prepareStatement(sql);
	        resultSet = preparedStatement.executeQuery();
	        ResultSetMetaData resultSetMeta = resultSet.getMetaData();
	        while(resultSet.next()) {
	        	int numColumns = resultSetMeta.getColumnCount();
	        	JSONObject obj = new JSONObject();
	            for (int i=1; i<numColumns+1; i++) {
	            	obj.put(resultSetMeta.getColumnName(i), resultSet.getObject(i));
	            }
	        }
			return arr.toString();
		} catch (SQLException e) {
			throw e;
		} finally {
			resultSet.close();
			preparedStatement.close();
		}
	}
}
