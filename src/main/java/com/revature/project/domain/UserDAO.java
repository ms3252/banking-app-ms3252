package com.revature.project.domain;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.revature.project.jdbc.ConnectionFactory;

public class UserDAO {
	private Connection conn;

	public UserDAO(Connection conn) {
		this.conn = conn;
	}
	public User getUser(String un, String pw) {
		try {
			String sql = "SELECT * FROM public.users WHERE user_name='" + un + "' AND password='" + pw + "'";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			rs.next();
			long id = rs.getLong("id");
			String fname = rs.getString("first_name");
			String lname = rs.getString("last_name");
			String uname = rs.getString("user_name");
			String passw = rs.getString("password");
			int authtype = rs.getInt("authtype");

			if (authtype == 3) {
				User currentUser = new Administrator(id, fname, lname, uname, passw, authtype);
				ps.close();
				return currentUser;
			} else if (authtype == 2) {
				User currentUser = new Employee(id, fname, lname, uname, passw, authtype);
				ps.close();
				return currentUser;
			} else {
				User currentUser = new User(id, fname, lname, uname, passw, authtype);
				ps.close();
				return currentUser;
			}
		} catch (SQLException ex) {
			System.out.println("DB did not work!");
			System.out.println(ex.getMessage());
		}
		return null;
	}
	
	public ArrayList<User> getAllUsers() {
		ArrayList<User> users = new ArrayList<User>();
		try  { 
			String sql ="SELECT * FROM public.users";
			User user = null;
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				user = new User(rs.getLong("id"), rs.getString("first_name"), rs.getString("last_name"), rs.getString("user_name"), rs.getString("password"), rs.getInt("authtype"));
				users.add(user);
			}
			if(users.size() > 0) {
				return users;
			}else {
				System.out.println("Error no users please restart app");
				return null;
			}
		} catch (SQLException ex) {
			System.out.println("Could not find user!");
			System.out.println(ex.getMessage());
			return null;
		}
	}
	
	public boolean getIfUserExists(String username, String password) {
		try { 
			String sql ="SELECT * FROM public.users WHERE user_name='"+username+"' AND password='"+password+"'";

			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			boolean returned = rs.next();
			if(returned) {
				ps.close();
				return true;
			}else {
				ps.close();
				return false;
			}

		} catch (SQLException ex) {
			System.out.println("Could not find user!");
			//System.out.println(ex.getMessage());
			return false;
		}
	}
	
	public boolean addUser(User user) {		
		try{ 
			String sql = "INSERT INTO public.users(first_name, last_name, user_name, password, authtype, email, phone_num, street_address, city, state, zip_code) "
					+ "VALUES ('"+user.getFirst_name()+"', '"+user.getLast_name()+"', '"+user.getUsername()+"', '"+user.getPassword()+"', "+user.getAuthtype()+", '"
					+user.getEmail_address()+"', "+user.getPhone_number()+", '"+user.getStreet_address()+"', '"+user.getCity()+"', '"+user.getState()+"', "+user.getZip_code()+")";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.executeUpdate();
			ps.close();
			return true;
		} catch (SQLException ex) {
			System.out.println("DB did not work saving user account!");
			System.out.println(ex.getMessage());
			return false;
		}
	}
	
	public boolean updateUser(User user) {
		try { 
			String sql = "UPDATE public.users SET first_name='"+user.getFirst_name()+"', last_name='"+user.getLast_name()+"', user_name='"+user.getUsername()+"', password='"+user.getPassword()+"', authtype='"+user.getAuthtype()+"' WHERE user_name="+user.getUsername();
			int response = 0;
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.executeQuery();
			if(response != 0) {
				ps.close();
				return true;
			}else {
				ps.close();
				return false;
			}

		} catch (SQLException ex) {
			System.out.println("DB did not work!");
			System.out.println(ex.getMessage());
			return false;
		}
	}
	
	public boolean authUser(String username, String password) {
		try { 
			String sql ="SELECT * FROM public.users WHERE user_name='"+username+"' AND password='"+password+"'";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			boolean returned = rs.next();
			if(returned) {
				ps.close();
				return true;
			}else {
				ps.close();
				return false;
			}

		} catch (SQLException ex) {
			System.out.println("DB did not work!");
			System.out.println(ex.getMessage());
			return false;
		}
	}

	public void printAllUsers() {
		try{ 
			String sql = "SELECT * FROM public.users";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			System.out.println("ID\tFirstName\tLastName\tUsername");
			while(rs.next()) {
				if(rs.getInt("authtype") != 3) {
					System.out.println(rs.getInt("id")+"\t"+rs.getString("firstname")+"\t\t"+rs.getString("lastname")+"\t\t"+rs.getString("username"));
				}
			}
			ps.close();
		} catch (SQLException ex) {
			System.out.println("DB did not work initializing bank accounts!");
			System.out.println(ex.getMessage());
		}
	}

	public void printAllUsersFullView() {
		try { 
			String sql = "SELECT * FROM public.users";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			System.out.println("ID\tFirstName\t\tLastName\tUsername\tPasswords\tAuthType");
			while(rs.next()) {
					System.out.println(rs.getLong("id")+"\t"+rs.getString("first_name")+"\t\t\t"+rs.getString("last_name")+"\t\t"+rs.getString("username")+"\t\t"+rs.getString("password")+"\t\t"+rs.getString("authtype"));
			}
			ps.close();
		} catch (SQLException ex) {
			System.out.println("DB did not work initializing bank accounts!");
			System.out.println(ex.getMessage());
		}
	}

	//@Override
	public boolean deleteUser(long id) {
		try { 
			String sql = "DELETE FROM public.users WHERE id="+id;
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			ps.close();
		} catch (SQLException ex) {
			System.out.println("DB did not work initializing bank accounts!");
			System.out.println(ex.getMessage());
		}
		return false;
	}

	public void printOneUser(long id) {
		try { 
			String sql = "SELECT * FROM public.users WHERE id="+id;
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			System.out.println("ID\t\tFirstName\t\tLastName\t\tUsername\t\tPasswords\t\tE-Mail\t\tPhone Number\t\tStreet Address\t\t\tCity\t\t\tState\t\tZip Code\t\tAuthType");
			while(rs.next()) {
					System.out.println(rs.getLong("id")+"\t\t"+rs.getString("first_name")+"\t\t"+rs.getString("last_name")+"\t\t"+rs.getString("user_name")+"\t\t"+rs.getString("password")+"\t\t"+rs.getString("email")+"\t\t"+rs.getLong("phone_num")+"\t\t"+rs.getString("street_address")+"\t\t\t"+rs.getString("city")+"\t\t\t"+rs.getString("state")+"\t\t"+rs.getLong("zip_code")+"\t\t"+rs.getInt("authtype"));
	}
		ps.close();
		} catch (SQLException ex) {
			System.out.println("DB did not work initializing bank accounts!");
			System.out.println(ex.getMessage());
		}
	}

	public User getOneUser(long id) {
		try { 
			String sql = "SELECT * FROM public.users WHERE id="+id;
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			rs.next();
			User user= new User(rs.getLong("id"), rs.getString("first_name"), rs.getString("last_name"), rs.getString("username"), rs.getString("password"), rs.getInt("authtype"));
			ps.close();
			return user;
		} catch (SQLException ex) {
			System.out.println("DB did not work initializing bank accounts!");
			System.out.println(ex.getMessage());
			return null;
		}
	}
}
