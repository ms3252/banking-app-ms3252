package com.revature.project.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    public static final String url = "jdbc:postgresql://localhost:5432/BankDB";
    public static final String user = "postgres";
    public static final String pw = "postgres";
   
    public static Connection getConnection(){
		try {
			 	Connection connection = DriverManager.getConnection(url,user,pw);
				return connection;
      } catch (SQLException ex) {
          throw new RuntimeException("Error connecting to the database", ex);
      }
    }
	
}
