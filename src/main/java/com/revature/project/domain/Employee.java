package com.revature.project.domain;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicLong;

public class Employee extends User {
	private AtomicLong empl_id = new AtomicLong(1010000L);
	// protected static // supposed to be list of applications
	ArrayList<Customer> myCustomers = new ArrayList<Customer>();

	public Employee() {
		id = empl_id.getAndIncrement();
	}

	public Employee(String userName, String passWord) {
		id = empl_id.getAndIncrement();
		this.username = userName;
		this.password = passWord;
	}

	public Employee(long id, String firstname, String lastname, String username, String password, int authtype) {
		super();
		this.id = id;
		this.first_name = firstname;
		this.last_name = lastname;
		this.username = username;
		this.password = password;
		this.authtype = authtype;
	}

	protected void addCustomerAccount(Customer newCustomer, String accountType, Account newAccount) {
		myCustomers.add(newCustomer);
		newCustomer.setManager(this.id);
		newAccount = new Account(accountType, newCustomer);
		newAccount.user_names.add(newCustomer);
		newCustomer.customer_accounts.add(newAccount);
		newAccount.account_status = "open";
	}

	protected void denyCustomerAccount(Customer newCustomer, Account newAccount) {
		newAccount.account_status = "denied";
		newAccount.user_names.remove(newCustomer);
		newCustomer.customer_accounts.remove(newAccount);
		// System.out.println("Sorry, we are unable to process your account application
		// at this time.");
	}

	public long getID() {
		return this.id;
	}

	public void setID(long employee_id) {
		this.id = employee_id;
	}

	public ArrayList<Customer> getCustomers() {
		return myCustomers;
	}

	public void setCustomers(ArrayList<Customer> customers) {
		this.myCustomers = customers;
	}

	public void showMenu() {
		char option = '\0';
		Scanner sc = new Scanner(System.in);

		System.out.println("Welcome to the Revature Bank! \n");


		System.out.println("A. View Customer Information");
		System.out.println("B. View Account Applications");
		System.out.println("C. Exit");

		do {
			System.out.println("==========================================================");
			System.out.println("Enter an option. \n");
			System.out.println("==========================================================");
			option = sc.next().charAt(0);
			System.out.println("\n");

			switch (option) {
			case 'A':
				showCustomerInfo();
				break;
			case 'B':
				showAccountApplications();
			case 'C':
				showMenu();
			}

		} while (option != 'C');

		System.out.println("Thank you for using our banking services! Please come again.");
		sc.close();

	}

	private void showAccountApplications() {
		// TODO Auto-generated method stub

	}

	void showCustomerInfo() {
		for (Customer i : myCustomers) {
			System.out.println("-----------------------------------------");
			System.out.println("Customer: " + i.getFirst_name() + " " + i.getLast_name() + " ");
			System.out.println("Address: " + i.getStreet_address() + " " + i.getCity() + " " + i.getState() + " "
					+ i.getZip_code());
			System.out.println("Phone: " + i.getPhone_number());
			System.out.println("Email: " + i.getEmail_address());
			for (Account j : i.customer_accounts) {
				System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
				System.out.println("Account Number: " + j.account_num);
				System.out.println("Routing Number: " + j.routing_num);
				System.out.println("Account Type: " + j.account_type);
				System.out.println("Balance: " + j.account_balance);
				System.out.println("Account Status: " + j.account_status);
				System.out.println("Date Opened: " + j.date_opened);
				System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			}
			System.out.println("-----------------------------------------");
			System.out.println("\n");
		}
	}

	public boolean loginAcct(String userName, String pw, Connection conn) {
		EmployeeDAO edao = new EmployeeDAO(conn);
		return edao.getIfUserExists(userName, pw);	
	}

	public void showMainMenu() {
		// TODO Auto-generated method stub

	}

	public void showApplicationMenu(Customer... apps) {
		// TODO Auto-generated method stub

	}

}