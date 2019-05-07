package com.revature.project.domain;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class AccountDAO {
	private Connection conn;

	public AccountDAO(Connection conn) {
		this.conn = conn;
	}
	
	public void updateAccountType(long acctNum, String type) {
		String sql = "UPDATE public.accounts "
					+"SET acct_type = ?"
					+"WHERE acct_num = ?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, type);
			ps.setLong(2, acctNum);
			ps.executeQuery();
			ps.close();
		} catch (SQLException ex) {
			System.out.println("Unable to update this account.");
			System.out.println(ex.getMessage());
		}
	}
	public void updateAccountStatus(long acctNum, String status) {
		String sql = "UPDATE public.accounts "
				+"SET acct_status = ?"
				+"WHERE acct_num = ?";
	try {
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, status);
		ps.setLong(2, acctNum);
		ps.executeQuery();
		ps.close();
	} catch (SQLException ex) {
		System.out.println("Unable to update this account.");
		System.out.println(ex.getMessage());
	}
	}
	
	public void printAccountInfo(long acctNumber) {
		String sql = "SELECT * FROM public.accounts WHERE acct_num=?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setLong(1, acctNumber);
			ResultSet rs = ps.executeQuery();
			System.out.println("Routing_Number:\t\t\t\tAccount_Number:\t\t\t\tBalance:\t\tAccount_Type:\t\tAccount_Status:\t\tDate_Opened:");
			System.out.println(rs.getLong("rout_num")+ rs.getLong("acct_num")+ rs.getDouble("acct_balance")+ rs.getString("acct_type")+ rs.getString("acct_status")+ rs.getDate("date_opened"));
		} catch (SQLException ex) {
			System.out.println("Unable to fetch this account.");
			System.out.println(ex.getMessage());
		}
	}

	public Account getAccount(long id) {
		String sql = "SELECT * FROM public.accounts WHERE acct_num=" + id;
		Account acct = null;
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			rs.next();
			acct = new Account(rs.getLong("acct_num"), rs.getDouble("acct_balance"), rs.getString("acct_type"),
					rs.getString("acct_status"));
			if (acct != null) {
				return acct;
			}
		} catch (SQLException ex) {
			System.out.println("Unable to fetch this account.");
			System.out.println(ex.getMessage());
			return null;
		}
		return acct;
	}

	public long getAccountId(Account acct) {
		String sql = "SELECT * FROM public.accounts WHERE acct_num=" + acct.getAcctNum();
		long id = 0;
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			id = rs.getLong("acct_num");
		} catch (SQLException ex) {
			System.out.println("Unable to fetch this account.");
			System.out.println(ex.getMessage());
		}
		return id;
	}

	public ArrayList<Account> getAllAccounts() {

		ArrayList<Account> accounts = new ArrayList<Account>();
		String sql = "SELECT * FROM public.accounts";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			Account acct = null;
			rs.next();
			while (rs.next()) {
				acct = new Account(rs.getLong("acct_num"), rs.getDouble("acct_balance"), rs.getString("acct_type"),
						rs.getString("acct_status"));
				accounts.add(acct);
				acct = null;
			}
			rs.close();
			return accounts;
		} catch (SQLException ex) {
			System.out.println("Unable to load Database.");
			System.out.println(ex.getMessage());
			return accounts;
		}
	}

	public boolean addAccount(User curUser, Account acct) {
		try {
			String sql = "INSERT INTO public.accounts (acct_num, acct_balance, acct_type, acct_status) VALUES ("
					+ acct.getAcctNum() + "', " + acct.getBalance() + ", '" + acct.getType() + ", " + acct.getStatus()
					+ ")";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			customerAccountsDAO cad = new customerAccountsDAO(conn);
			cad.insertCustAccount(curUser, acct.getAcctNum());
			return true;
		} catch (SQLException ex) {
			System.out.println("Unable to add this account to the database.");
			System.out.println(ex.getMessage());
			return false;
		}
	}

	public ArrayList<Account> getCustAccounts(Customer currentCust) {
		ArrayList<Account> accounts = new ArrayList<Account>();
		try {
			String sql = "WITH customer_accounts as(SELECT acct_num FROM customer_accounts WHERE user_name="
					+ currentCust.getUsername()
					+ ") SELECT * FROM customer_accounts INNER JOIN accounts ON customer_accounts.acct_num = accounts.acct_num";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Account acct = new Account(rs.getLong("acct_num"), rs.getDouble("acct_balance"),
						rs.getString("acct_type"), rs.getString("acct_status"));
				accounts.add(acct);
			}
			return accounts;
		} catch (SQLException ex) {
			System.out.println("DB ERROR! This customer's accounts could not be found.");
			System.out.println(ex.getMessage());
		}
		return accounts;
	}

	public ArrayList<Account> getPendingAccounts() {

		ArrayList<Account> accounts = new ArrayList<Account>();
		try {
			String sql = "SELECT * FROM public.accounts WHERE acct_status='pending'";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Account acct = new Account(rs.getLong("acct_num"), rs.getDouble("acct_balance"),
						rs.getString("acct_type"), rs.getString("acct_status"));
				accounts.add(acct);
			}
			return accounts;
		} catch (SQLException ex) {
			System.out.println("DB ERROR! Unable to find pending accounts.");
			System.out.println(ex.getMessage());
		}
		return accounts;
	}

	public ArrayList<Account> getOpenAccounts() {
		ArrayList<Account> accounts = new ArrayList<Account>();
		try {
			String sql = "SELECT * FROM public.accounts WHERE acct_status='open'";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Account acct = new Account(rs.getLong("acct_num"), rs.getDouble("acct_balance"),
						rs.getString("acct_type"), rs.getString("acct_status"));
				accounts.add(acct);
			}
			return accounts;
		} catch (SQLException ex) {
			System.out.println("DB ERROR! Unable to find open accounts");
			System.out.println(ex.getMessage());
		}
		return accounts;
	}

	public ArrayList<Account> getCanceledDeniedAccounts() {
		ArrayList<Account> accounts = new ArrayList<Account>();
		try {
			String sql = "SELECT * FROM public.accounts WHERE acct_status='canceled'";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Account acct = new Account(rs.getLong("acct_num"), rs.getDouble("acct_balance"),
						rs.getString("acct_type"), rs.getString("acct_status"));
				accounts.add(acct);
			}
			return accounts;
		} catch (SQLException ex) {
			System.out.println("DB ERROR! Unable to find canceled accounts");
			System.out.println(ex.getMessage());
		}
		return accounts;
	}

	public double getBalance(long acctNum) {
		double balance = 0.0;
		try {
			String sql = "SELECT acct_balance FROM public.accounts WHERE acct_num= " + acctNum;
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			balance = rs.getDouble("acct_balance");
		} catch (SQLException ex) {
			System.out.println("DB ERROR! Unable to find canceled accounts");
			System.out.println(ex.getMessage());
		}
		return balance;
	}

	public void depositToAccount(long acct, double amount) {
		if (validateAccount(acct)) {
			double amt = getBalance(acct) + amount;
			try {
				String sql = "UPDATE public.accounts " + "SET acct_balance =?" + " WHERE acct_num=?";
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setDouble(1, amt);
				ps.setLong(2, acct);
				ps.executeUpdate();
				// log here
			} catch (SQLException ex) {
				System.out.println("DB ERROR! Unable to complete this deposit.");
				System.out.println(ex.getMessage());
			}
		}
	}

	public void withdrawFromAccount(long acct, double amount) {
		boolean isValid = validateAccount(acct);
		System.out.println("Account id: "+ acct);
		if (isValid) {
			double amt = getBalance(acct) - amount;
			try {
				String sql = "UPDATE public.accounts " + "SET acct_balance=? " + " WHERE acct_num=?";
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setDouble(1, amt);
				ps.setLong(2, acct);
				ps.executeUpdate();
				// log here
			} catch (SQLException ex) {
				System.out.println("DB ERROR! Unable to complete this deposit.");
				System.out.println(ex.getMessage());
			}
		}
	}

	public boolean transferBetweenAccounts(Account source, Account destination, double amount) {
		long sourceNum = getAccountId(source);
		long destNum = getAccountId(destination);

		if (this.validateAccount(sourceNum) && this.validateAccount(destNum)) {
			withdrawFromAccount(sourceNum, amount);
			depositToAccount(destNum, amount);
			// should log here
			return true;
		} else {
			System.out.println("This transfer has failed!");
			return false;
		}
	}

	public boolean validateAccount(long id) {
		if (this.getAccount(id).getStatus() == "open") {
			return true;
		} else {
			return false;
		}
	}

	public void printPendingAccounts() {
		try {
			String sql = "SELECT * FROM public.accounts WHERE acct_status='open'";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			System.out.println("AccountNumber\t\tBalance\t\tAccountType\t\tAccountStatus");
			while (rs.next()) {
				System.out
						.println(rs.getLong("acct_num") + "\t\t$" + String.format("%.2f", rs.getDouble("acct_balance"))
								+ "\t\t" + rs.getString("acct_type") + "\t\t" + rs.getString("acct_status"));
			}
		} catch (SQLException ex) {
			System.out.println("DB ERROR! Unable to find pending accounts");
			System.out.println(ex.getMessage());
		}
	}

	public void printAllAccounts() {
		try {
			String sql = "SELECT * FROM public.accounts";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			System.out.println("AccountNumber\t\tBalance\t\tAccountType\t\tAccountStatus");
			while (rs.next()) {
				System.out
						.println(rs.getLong("acct_num") + "\t\t$" + String.format("%.2f", rs.getDouble("acct_balance"))
								+ "\t\t" + rs.getString("acct_type") + "\t\t" + rs.getString("acct_status"));
			}
			ps.close();
		} catch (SQLException ex) {
			System.out.println("DB failed to find accounts!");
			System.out.println(ex.getMessage());
		}
	}
// do view/procedure here
	public void printAllAccountsWithNamesView() {
		try {
			String sql = "SELECT * FROM customer_accounts LEFT JOIN users ON (users.user_name = customer_accounts.user_name) RIGHT JOIN accounts ON (accounts.acct_num = customer_accounts.acct_num)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			System.out.println("FirstName\t\tLastName\t\tAccountNumber\t\tAccountType\t\tBalance\t\t\tAccountStatus");
			while (rs.next()) {
				System.out.println(rs.getString("first_name") + "\t\t" + rs.getString("last_name") + "\t\t"
						+ rs.getLong("acct_num") + "\t" + rs.getString("acct_type") + "\t\t\t$"
						+ String.format("%.2f", rs.getDouble("acct_balance")) + "\t\t\t" + rs.getString("acct_status"));
			}
			ps.close();
		} catch (SQLException ex) {
			System.out.println("DB did not work initializing bank accounts!");
			System.out.println(ex.getMessage());
		}
	}
}
