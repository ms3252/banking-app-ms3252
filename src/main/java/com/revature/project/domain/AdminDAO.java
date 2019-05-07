package com.revature.project.domain;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

import com.revature.project.jdbc.ConnectionFactory;

public class AdminDAO extends UserDAO {
	private Connection conn;

	public AdminDAO(Connection conn) {
		super(conn);
		this.conn = conn;
	}

	public boolean getIfAdminExists(String username, String password) {
		try {
			String sql = "SELECT * FROM public.administrators WHERE user_name=? AND password=?";

			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			boolean returned = rs.next();
			if (returned) {
				return true;
			} else {
				return false;
			}

		} catch (SQLException ex) {
			System.out.println("Could not find this Administrator!");
			// System.out.println(ex.getMessage());
			return false;
		}
	}

	public ArrayList<Customer> getAllCustomers() {
		ArrayList<Customer> customers = new ArrayList<Customer>();
		try {
			String sql = "SELECT * FROM public.customers";
			Customer cust = null;
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				cust = new Customer(rs.getLong("id"), rs.getString("user_name"), rs.getString("password"),
						rs.getString("first_name"), rs.getString("last_name"), rs.getString("email"),
						rs.getString("street_address"), rs.getString("city"), rs.getString("state"),
						rs.getLong("zip_code"), rs.getLong("phone_num"), rs.getInt("authtype"));
				customers.add(cust);
			}

			if (customers.size() > 0) {
				return customers;
			} else {
				System.out.println("Error: no customers please restart app");
				return null;
			}
		} catch (SQLException ex) {
			System.out.println("Could not find customers!");
			System.out.println(ex.getMessage());
			return null;
		}
	}

	public void printAllCustomers() {
		try {
			String sql = "SELECT * FROM public.customers";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			System.out.println("ID:\t\tUsername:\t\t\tPasswords:\t\t\tFirstName:\t\t\tLastName:\t\t\tEmail:\t\t\tAddress\t\t\t\t\t\tCity\t\t\t"
							+ "State\t\t\tZip_Code\t\tPhone_Number\t\t\tAuthType");
			while (rs.next()) {
				System.out.println(rs.getLong("id") + "\t\t" + rs.getString("user_name") + "\t\t\t"
						+ rs.getString("password") + "\t\t\t" + rs.getString("first_name") + "\t\t\t"
						+ rs.getString("last_name") + "\t\t\t" + rs.getString("email") + "\t\t\t"
						+ rs.getString("street_address") + "\t\t\t\t\t\t" + rs.getString("city") + "\t\t\t"
						+ rs.getString("state") + "\t\t\t" + rs.getLong("zip_code") + "\t\t" + rs.getLong("phone_num")
						+ "\t\t\t" + rs.getInt("authtype"));
			}
			ps.close();
		} catch (SQLException ex) {
			System.out.println("DB did not work initializing Customers!");
			System.out.println(ex.getMessage());
		}
	}

}
