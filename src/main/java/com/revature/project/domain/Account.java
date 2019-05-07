package com.revature.project.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicLong;

public class Account {
	protected long routing_num;
	AtomicLong bankNum = new AtomicLong(915000000000L); // possibly make this static
	protected long account_num;
	AtomicLong accountNum = new AtomicLong(711000000000000L);
	protected String account_type;
	protected String account_status;
	protected double account_balance;
	protected double transaction_amount;
	protected String date_opened;
	protected ArrayList<Customer> user_names;

	public Account(String type, Customer customer) {
		account_status = "pending";
		account_balance = 0.0;
		transaction_amount = 0.0;
		account_type = type;
		account_num = accountNum.getAndIncrement();
		routing_num = bankNum.get();
		Date date = new Date();
		date_opened = date.toString();
		user_names = new ArrayList<Customer>();
		user_names.add(customer);
	}

	public Account() {
		account_status = "pending";
		account_balance = 0.0;
		transaction_amount = 0.0;
		account_num = accountNum.getAndIncrement();
		routing_num = bankNum.get();
		Date date = new Date();
		date_opened = date.toString();
		user_names = new ArrayList<Customer>();
	}

	public Account(long acct_num, double balance, String acct_type,String acct_status) {
		this.account_status = acct_status;
		this.account_balance = balance;
		transaction_amount = 0.0;
		this.account_type = acct_type;
		account_num = this.account_num;
		routing_num = bankNum.get();
		Date date = new Date();
		date_opened = date.toString();
	}

	public Account(long rout_num, long acct_num, double bal, String type, String status, Date date) {
		this.routing_num = rout_num;
		this.account_num = acct_num;
		this.account_balance = bal;
		this.account_type = type;
		this.account_status = status;
		this.date_opened = date.toString();
	}

	void setRoutingNum(long routing_num) {
		this.routing_num = routing_num;
	}

	public void withdrawFunds(double amount) {
		if (account_status == "open") {
			if ((amount > 0) && (amount < account_balance)) {
				this.account_balance -= amount;
				this.transaction_amount = -amount;
				getPreviousTransaction();
			} else
				System.out.println("Invalid amount, unable to withdraw" + amount);
		} else
			System.out.println("Invalid transaction.");

	}

	public void depositFunds(double amount) {
		if (account_status == "open") {
			if (amount > 0) {
				this.account_balance += amount;
				this.transaction_amount = amount;
				getPreviousTransaction();
			}
		} else
			System.out.println("Invalid transaction.");
	}

	void getPreviousTransaction() {
		if (transaction_amount > 0) {
			System.out.println(account_num + " Deposited: " + transaction_amount);
		} else if (transaction_amount < 0) {
			System.out.println(account_num + " Withdrawn: " + Math.abs(transaction_amount));
		} else
			System.out.println("No transaction occured.");
	}

	public void transferFunds(Account destination, double amount) {
		if (this.account_status == "open" && destination.account_status == "open") {
			this.withdrawFunds(amount);
			destination.depositFunds(amount);
		}
	}

	public boolean isJoint() {
		if (user_names.size() > 1)
			return true;
		else
			return false;
	}

	public double getBalance() {
		return account_balance;
	}
	public String getType() {
		return account_type;
	}
	public String getStatus() {
		return account_status;
	}
	public long getAcctNum() {
		return account_num;
	}
	@Override
	public String toString() {
		return "Account #" +account_num + ": [Balance: $" + account_balance + "; routing number: " + routing_num + "; status: " + account_status + "; type: "+ account_type+"]";
	}

}
