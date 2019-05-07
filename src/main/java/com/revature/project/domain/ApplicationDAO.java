package com.revature.project.domain;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ApplicationDAO {
	private Connection conn;
	private AccountDAO ad = new AccountDAO(conn);
	private CustomerDAO cd = new CustomerDAO(conn);

	public ApplicationDAO(Connection conn) {
		this.conn = conn;
	}
	
	public boolean approveApplication(int appID) {
		boolean completed = false;
		try {
			String sql = "UPDATE public.applications "+ "SET app_status=? "+"WHERE app_id=?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, "approved");
			ps.setInt(2, appID);
			ps.executeUpdate();
			completed=true;
		}catch(SQLException e) {
			System.out.println("ERROR: Unable to approve account.");
			e.printStackTrace(); 
			completed=false;
		}
		System.out.println("Application Approved.");
		return completed;
	}
	public boolean denyApplication(int appID) {
		boolean completed = false;
		try {
			String sql = "UPDATE public.applications "+ "SET app_status=? "+"WHERE app_id=?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, "denied");
			ps.setInt(2, appID);
			ps.executeUpdate();
			completed=true;
		}catch(SQLException e) {
			System.out.println("ERROR: Unable to deny account.");
			e.printStackTrace(); 
			completed=false;
		}
		System.out.println("Application Denied.");
		return completed;
	}
	
	public Application getApplication(int id) {
		Application app = null;
		try {
			String sql = "SELECT * FROM public.applications where app_id=?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			app = new Application(cd.getCustomer(rs.getString("user_name")),ad.getAccount(rs.getLong("acct_num")));
		} catch (SQLException e) {
			System.out.println("ERROR: Failed to add application");
			e.printStackTrace();
		}
		return app;
	}

	public List<Application> getAll() {
		String sql = "SELECT * FROM public.applications";
		List<Application> applications = new ArrayList<Application>();
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				applications.add(new Application(cd.getCustomer(rs.getString("user_name")), ad.getAccount(rs.getLong("acct_num"))));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return applications;
	}
	
	public int getTotalNumRows() {
		int total = 0;
		String sql = "CREATE OR REPLACE FUNCTION total_applications ()" + 
				"RETURNS integer as $total$" + 
				"DECLARE " + 
				"	total integer; " + 
				"BEGIN " + 
				"	select count(*) into total from public.applications;" + 
				"	return total;" + 
				"END;" + 
				" $total$ language plpgsql;";
		try {
			PreparedStatement ps = conn.prepareCall(sql);
			ResultSet rs = ps.executeQuery();
		}catch(SQLException e) {
			System.out.println("Unable to load stored procedure.");
		}
		return total;
	}
	
	public void printAllApplications() {
		String sql = "SELECT * FROM public.applications";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			System.out.println("APPLICATION_NUMBER: \t\t\tACCOUNT_NUMBER: \t\t\tACCOUNT_APPLICANT: \t\t\tACCOUNT_STATUS: ");
			while(rs.next()) {
				System.out.println(rs.getInt("app_id")+ " \t\t\t\t\t"+ rs.getLong("acct_num")+" \t\t\t"+rs.getString("user_name")+" \t\t\t\t"+rs.getString("app_status"));
			}
		}catch(SQLException e) {
			System.out.println("ERROR: Failed to find Applications");
			e.printStackTrace();
		}
	}

	public void addApplication(Application a) {
		try {
			String sql = "INSERT INTO public.applications (user_name, acct_num, app_status) "
						+ "VALUES (?,?,?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, a.getApplicant().getUsername());
			ps.setLong(2, a.getAppliedAccount().getAcctNum());
			ps.setString(3, a.getApplicationStatus());
			ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("ERROR: Failed to add application.");
			e.printStackTrace();
		}
	}
	
	public void addApplication(String userName, long accountNum, String appStatus) {
		try {
			String sql = "INSERT INTO public.applications (user_name, acct_num, app_status) "
		+ "VALUES (?,?,?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, userName);
			ps.setLong(2, accountNum);
			ps.setString(3, appStatus);
			ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("ERROR: Failed to add application");
			e.printStackTrace();
		}
	}
}
