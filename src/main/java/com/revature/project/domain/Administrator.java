package com.revature.project.domain;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Scanner;

public class Administrator extends User{

	private int authtype;
	private long id;

	public Administrator(){
		super();
	}
	Administrator(long id, String firstname, String lastname, String username, String password, int authtype){
		super(authtype, password, password, password, password, authtype);
		this.id = id;
		this.first_name = firstname;
		this.last_name = lastname;
		this.username = username;
		this.password = password;
		this.authtype = authtype;
	}

	public Administrator(String userName, String pw) {
		this.username = userName;
		this.password = pw;
	}
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public int getAuthtype() {
		return authtype;
	}

	public void setAuthtype(int authtype) {
		this.authtype = authtype;
	}
	
	public ArrayList<Account> seeAllAccounts(Connection conn) {
		AccountDAO atd = new AccountDAO(conn);
		return atd.getAllAccounts();
	}
	
	public ArrayList<Customer> seeAllCustomers(Connection conn) {
		CustomerDAO csd = new CustomerDAO(conn);
		return csd.getAll();
	}
	public boolean loginAcct(String userName, String pw, Connection conn) {
		AdminDAO adao = new AdminDAO(conn);
		return adao.getIfUserExists(userName, pw);
	}
}
