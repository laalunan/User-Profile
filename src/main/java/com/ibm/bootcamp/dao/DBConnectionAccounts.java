package com.ibm.bootcamp.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConnectionAccounts implements DBConnectionFactory {
	
	Properties prop = new Properties();
	InputStream input = null;
	
	@Override
	public Connection getConnection() {
		
		try {

			input = new FileInputStream("config/application.properties");

			prop.load(input);

			if (prop.getProperty("use.database").equals("true")) {

				Class.forName(prop.getProperty("db.mysql.drivername"));
				Connection conn = DriverManager.getConnection(prop.getProperty("accounts.mysql.url"),
						prop.getProperty("db.mysql.username"), prop.getProperty("db.mysql.password"));
				return conn;
			}

		} catch (ClassNotFoundException | SQLException | IOException io) {
			Logger.getLogger(DBConnectionAccounts.class.getName()).log(Level.SEVERE, null, io);
		}
		return null;
	}
	
	@Override
	public void closeConnection(Connection conn, PreparedStatement ps) {
		if (conn != null) {
			try {
				ps.close();
				conn.close();
			} catch (Exception e) {
				System.out.println("DATABASE ERROR: CLOSING");
			}
		}
	}

	@Override
	public void closeConnection(Connection conn, Statement s) {
		// TODO Auto-generated method stub
		
	}
}