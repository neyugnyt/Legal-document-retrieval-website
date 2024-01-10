package com.JavaWebApplication.Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyDb {
    private static final String url = "jdbc:mysql://localhost:3306/vanbandb";
    private static final String user = "root";
    private static final String password = "Tynguyen1412!"; 
    public Connection con = null;
	public Connection getCon() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");;
			con = DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException("unhandled", e);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException("unhandled", e);
		}
		return con;
	}
}
