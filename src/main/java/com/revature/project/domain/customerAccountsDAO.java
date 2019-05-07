package com.revature.project.domain;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import com.revature.project.jdbc.ConnectionFactory;;

public class customerAccountsDAO {
	private Connection conn;

	public customerAccountsDAO(Connection conn) {
		this.conn = conn;
	}
	public boolean insertCustAccount(User curUser, long row) {
		try {
			String un = curUser.getUsername();
			System.out.println("User Name: " + un + " Account ID: " + row);
			String sql = "INSERT INTO public.customer_accounts (user_name, acct_num) VALUES ( ?, ? )";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, curUser.getUsername());
			ps.setLong(2, row);
			int rs = ps.executeUpdate();
			System.out.println("Account has been added to this customer_accounts table!");
			return true;
		} catch (SQLException ex) {
			System.out.println("DB did not save new bank account and user into joint table!");
			System.out.println(ex.getMessage());
			return false;
		}
	}
}
