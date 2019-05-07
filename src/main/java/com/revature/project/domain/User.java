package com.revature.project.domain;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

import com.revature.project.jdbc.ConnectionUtil;

public class User {

	protected String username;
	protected String password;
	protected String first_name;
	protected String last_name;
	protected String street_address;
	protected long zip_code;
	protected long phone_number;
	protected String email_address;
	protected String city;
	protected String state;
	protected long id;
	protected int authtype;

	protected boolean isLoggedIn;
	//public Scanner sc;

	public User() {
		username = "";
		password = "";
		first_name = "";
		last_name = "";
		street_address = "";
		zip_code = 00000;
		phone_number = 0000000000;
		email_address = "";
		city = "";
		state = "";
		isLoggedIn = false;
		authtype=0;
	}
	public User(long id, String un, String pw, String fn, String ln, int authtype) {
		username = un;
		password = pw;
		this.id = id;
		first_name = fn;
		last_name = ln;
		this.authtype = authtype;
	}
	public User(long id, String un, String pw, String fn, String ln, String em, String st_add,String ct,String st,long zp, long pn, int at) {
		username = un;
		password = pw;
		this.id = id;
		first_name = fn;
		last_name = ln;
		street_address = st_add;
		zip_code = zp;
		phone_number = pn;
		email_address = em;
		city = ct;
		state = st;
		isLoggedIn = false;
		authtype=at;
	}
	public int getAuthtype() {
		return authtype;
	}

	public void setAuthtype(int authtype) {
		this.authtype = authtype;
	}

	public long getID() {
		return id;
	}
	
	public void setID(long id) {
		this.id = id;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getStreet_address() {
		return street_address;
	}

	public void setStreet_address(String street_address) {
		this.street_address = street_address;
	}

	public long getZip_code() {
		return zip_code;
	}

	public void setZip_code(long zip_code) {
		this.zip_code = zip_code;
	}

	public long getPhone_number() {
		return phone_number;
	}

	public void setPhone_number(long phoneNum) {
		this.phone_number = phoneNum;
	}

	public String getEmail_address() {
		return email_address;
	}

	public void setEmail_address(String email_address) {
		this.email_address = email_address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
}