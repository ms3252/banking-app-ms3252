package com.revature.project.domain;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicLong;

public class Customer extends User {
	private long manager;
	protected ArrayList<Account> customer_accounts = new ArrayList<Account>();
	protected ArrayList<Application> pending_applications = new ArrayList<Application>();

	public Customer() {
		super();
	}

	public Customer(String userName, String pw) {
		super();
		this.username = userName;
		this.password = pw;
	}

	public Customer(Connection conn, String userName, String passWord, String customerName, String last_Name,
			String email_Address, String street_Address, String city, String state, long zip_Code, long phone_Num) {
		this.first_name = customerName;
		this.last_name = last_Name;
		this.email_address = email_Address;
		this.street_address = street_Address;
		this.city = city;
		this.state = state;
		this.zip_code = zip_Code;
		this.phone_number = phone_Num;
		this.username = userName;
		this.password = passWord;
		CustomerDAO custDAO = new CustomerDAO(conn);
		custDAO.addCustomer(userName, passWord, customerName, last_Name, email_Address, street_Address, city, state,
				zip_Code, phone_Num);

	}

	public Customer(String userName, String passWord, String customerName, String last_Name, String email_Address,
			String street_Address, String city, String state, long zip_Code, long phone_Num) {
		this.first_name = customerName;
		this.last_name = last_Name;
		this.email_address = email_Address;
		this.street_address = street_Address;
		this.city = city;
		this.state = state;
		this.zip_code = zip_Code;
		this.phone_number = phone_Num;
		this.username = userName;
		this.password = passWord;
	}

	public Customer(long id, String userName, String passWord, String customerName, String last_Name,
			String email_Address, String street_Address, String city, String state, long zip_Code, long phone_Num,
			int authtype) {
		this.id = id;
		this.first_name = customerName;
		this.last_name = last_Name;
		this.email_address = email_Address;
		this.street_address = street_Address;
		this.city = city;
		this.state = state;
		this.zip_code = zip_Code;
		this.phone_number = phone_Num;
		this.username = userName;
		this.password = passWord;
		this.authtype = authtype;
	}

	/*
	 * public void tranferFunds(Account sourceAccount, Account destAccount, double
	 * amount) { if (sourceAccount.account_status == "open" &&
	 * destAccount.account_status == "open") { if
	 * (customer_accounts.contains(sourceAccount)) {
	 * sourceAccount.withdrawFunds(amount); destAccount.depositFunds(amount); } else
	 * { System.out.println("Unable to transfer funds from this account."); // log
	 * this attempt } } else
	 * System.out.println("Account(s) still pending, try again later."); }
	 * 
	 * public void withdrawFunds(Account account, double amount) { if
	 * (account.account_status == "open") { if (customer_accounts.contains(account))
	 * account.withdrawFunds(amount); else {
	 * System.out.println("Unable to withdraw funds from this account."); // log
	 * this attempt } } else {
	 * System.out.println("Account still pending, try again later."); } }
	 * 
	 * public void depositFunds(Account account, double amount) { if
	 * (account.account_status == "open") { String amt = String.format("%2f",
	 * amount); if (Double.toString(amount) == amt) account.depositFunds(amount);
	 * else { System.out.println("Will be depositing: $ " + amt);
	 * account.depositFunds(Double.parseDouble(amt)); } } else
	 * System.out.println("Account still pending, try again later."); }
	 */
	public long getManager() {
		return manager;
	}

	public void setManager(long employee_id) {
		this.manager = employee_id;
	}

	public ArrayList<Account> getCustomer_accounts() {
		return customer_accounts;
	}

	public void setCustomer_accounts(ArrayList<Account> customer_accounts) {
		this.customer_accounts = customer_accounts;
	}

	public void addAccount(Account newAccount) {
		this.customer_accounts.add(newAccount);
	}

	public void applyForAccount(String type) {
		Application newApp = new Application(this, new Account(type, this));
		pending_applications.add(newApp);
		System.out.println("Thank you for apply for a new account! \n Your application is currently pending.");
	}

	public boolean loginAcct(String userName, String pw, Connection conn) {
		CustomerDAO cdao = new CustomerDAO(conn);
		return cdao.getIfUserExists(userName, pw);
	}

	public void printAccounts() {
		for (Account i : customer_accounts) {
			System.out.println("[ACCOUNT #" + i.account_num + "; BALANCE: " + i.account_balance + "; STATUS: "
					+ i.account_status + "; TYPE: " + i.account_type + "]");
		}
	}
}