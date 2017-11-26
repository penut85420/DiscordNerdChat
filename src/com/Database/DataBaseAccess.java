package com.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressWarnings("unused")
public class DataBaseAccess {
	private final String localHostDataSource = "jdbc:mysql://127.0.0.1:3306/bot?user=newuser&password=971233&useSSL=false";
	private final String severDataSource = "jdbc:mysql://140.121.199.228:3306/bot?user=newuser&password=971233&useSSL=false";
	private Connection conn = null;
	private Statement st;
	private ResultSet rs;
	private TargetTable table;

	public static enum TargetTable {
		USER, GAME, FAVORITE, MESSAGE
	};

	public DataBaseAccess() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		try {
			conn = DriverManager.getConnection(localHostDataSource);
		} catch (Exception e) {
			conn = DriverManager.getConnection(severDataSource);
		}
		st = conn.createStatement();
	}

	public void insert(String table, String value) throws SQLException {
		st.execute("INSERT INTO " + table + " value " + value);
	}

	public ResultSet select(String targetCol, String targetTable, String condition, String order) throws SQLException {
		st.execute("SELECT " + targetCol + "FROM" + targetTable + " WHERE " + condition + " ORDER BY " + order);
		return st.getResultSet();
	}

	public ResultSet select(String targetCol, String targetTable, String order) throws SQLException {
		String query = "SELECT " + targetCol + " FROM " + targetTable;

		st.execute(query + (order.isEmpty() ? "" : (" ORDER BY " + order)));
		return st.getResultSet();
	}

	public static void main(String[] args) throws Exception {
		DataBaseAccess db = new DataBaseAccess();
		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = sdf.format(dt);
		db.insert("users(user_id)", "(\'Leon\')");
		ResultSet rs = db.select("*", "users","");
		while (rs.next())
			System.out.println(rs.getString("user_id"));
	}
}
