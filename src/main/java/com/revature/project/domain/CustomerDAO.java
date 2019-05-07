package com.revature.project.domain;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.revature.project.jdbc.ConnectionFactory;

public class CustomerDAO {
	private Connection conn;

	public CustomerDAO(Connection conn) {
		this.conn = conn;
	}

	public Customer getCustomer(String userName) {
		try {
			String sql = "SELECT * FROM public.customers WHERE user_name = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, userName);
			ResultSet rs = ps.executeQuery();
			Customer cust = new Customer(rs.getLong("id"), rs.getString("user_name"), rs.getString("password"),
					rs.getString("first_name"), rs.getString("last_name"), rs.getString("email_address"),
					rs.getString("street_address"), rs.getString("city"), rs.getString("state"), rs.getLong("zip_code"),
					rs.getLong("phone_num"), rs.getInt("authtype"));
			ps.close();
			return cust;
		} catch (SQLException ex) {
			System.out.println("DB did not work initializing bank accounts!");
			System.out.println(ex.getMessage());
			return null;
		}
	}

	public void printCustomerInfo(String userName) {
		try {
			String sql = "SELECT * FROM public.users where user_name = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, userName);
			ResultSet rs = ps.executeQuery();
			System.out.println(
					"ID:\t\tUsername:\t\t\tPasswords:\t\t\tFirstName:\t\t\tLastName:\t\t\tEmail:\t\t\tAddress\t\t\t\t\t\tCity\t\t\t"
							+ "State\t\t\tZip_Code\t\tPhone_Number\t\t\tAuthType");
			System.out.println(rs.getLong("id") + "\t\t" + rs.getString("user_name") + "\t\t\t"
					+ rs.getString("password") + "\t\t\t" + rs.getString("first_name") + "\t\t\t"
					+ rs.getString("last_name") + "\t\t\t" + rs.getString("email_address") + "\t\t\t"
					+ rs.getString("street_address") + "\t\t\t\t\t\t" + rs.getString("city") + "\t\t\t"
					+ rs.getString("state") + "\t\t\t" + rs.getLong("zip_code") + "\t\t" + rs.getLong("phone_num")
					+ "\t\t\t" + rs.getInt("authtype"));
			ps.close();
		} catch (SQLException ex) {
			System.out.println("DB did not work initializing Customers!");
			System.out.println(ex.getMessage());
		}
	}

	public void showCustomerAccounts(String userName) {
		try {
			String sql = "SELECT * FROM public.accounts WHERE acct_num in (SELECT acct_num FROM customer_accounts where user_name=?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, userName);
			ResultSet rs = ps.executeQuery();
			Customer cust = getCustomer(userName);
			System.out.println(
					"Routing_Number:\t\t\t\tAccount_Number:\t\t\t\tBalance:\t\tAccount_Type:\t\tAccount_Status:\t\tDate_Opened:");
			while (rs.next()) {
				System.out.println(rs.getLong("rout_num") + "\t\t\t\t"+rs.getLong("acct_num")+"\t\t\t\t" + rs.getDouble("acct_balance")+"\t\t\t"
						+ rs.getString("acct_type")+"\t\t" + rs.getString("acct_status")+"\t\t\t" + rs.getDate("date_opened"));
			}
			ps.close();
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
	}

	public boolean getIfUserExists(String username, String password) {
		try {
			String sql = "SELECT * FROM public.customers WHERE user_name=? AND password=?";

			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			boolean returned = rs.next();
			ps.close();
			if (returned) {
				return true;
			} else {
				return false;
			}
		} catch (Exception ex) {
			System.out.println("Could not find user!");
			// System.out.println(ex.getMessage());
			return false;
		}
	}

	public ArrayList<Customer> getAll() {
		String sql = "SELECT * FROM public.customers";
		ArrayList<Customer> customers = new ArrayList<Customer>();
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Customer cust = new Customer(conn, rs.getString("user_name"), rs.getString("password"),
						rs.getString("first_name"), rs.getString("last_name"), rs.getString("email"),
						rs.getString("street_address"), rs.getString("city"), rs.getString("state"),
						rs.getLong("zip_code"), rs.getLong("phone_num"));
				customers.add(cust);
			}
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return customers;
	}

	public void addCustomer(String un, String pw, String fn, String ln, String em, String st_add, String ct, String st,
			long zp, long pn) {
		try {
			String sql = "INSERT INTO public.customers (user_name, password, first_name, last_name, email, street_address, city, state, zip_code, phone_num) "
					+ "VALUES (un, pw, fn, ln, em, st_add, ct, st, zp, pn)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			ps.close();
		} catch (SQLException e) {
			System.out.println("Failed to add a new Customer to database.");
			e.printStackTrace();
		}
	}

	public void addCustomer(Customer c) {
		try {
			String sql = "INSERT INTO public.customers (user_name, password, first_name, last_name, email, street_address, city, state, zip_code, phone_num) "
					+ "VALUES (?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, c.getUsername());
			ps.setString(2, c.getPassword());
			ps.setString(3, c.getFirst_name());
			ps.setString(4, c.getLast_name());
			ps.setString(5, c.getEmail_address());
			ps.setString(6, c.getStreet_address());
			ps.setString(7, c.getCity());
			ps.setString(8, c.getState());
			ps.setLong(9, c.getZip_code());
			ps.setLong(10, c.getPhone_number());

			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			System.out.println("Failed to add a new Customer to database.");
			e.printStackTrace();
		}
	}
}
