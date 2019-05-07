package com.revature.project.domain;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class EmployeeDAO extends UserDAO{
	private Connection conn;

	public EmployeeDAO(Connection conn) {
		super(conn);
		this.conn = conn;
	}
	public boolean getIfUserExists(String username, String password) {
		try {
			String sql ="SELECT * FROM public.employees WHERE user_name=? AND password=?";
			PreparedStatement ps = conn.prepareStatement(sql); 
			ps.setString(1, username);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			boolean returned = rs.next();
			if(returned) {
				return true;
			}else {
				return false;
			}
		} catch (SQLException ex) {
			System.out.println("Could not find this Employee!");
			//System.out.println(ex.getMessage());
			return false;
		}
	}
}