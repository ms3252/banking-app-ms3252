package com.revature.project.domain;

public class Application {
	private Customer applicant;
	private Account appliedAccount;
	private int applicationNum = 0;
	private String applicationStatus;
	
	public Application(Customer applicant, Account acct) {
		this.applicant = applicant;
		this.appliedAccount = acct;
		applicationNum++;
		applicationStatus = "pending";
		System.out.println("Application is now pending!");
	}
	
	@Override
	public String toString() {
		return "Application #" +applicationNum + ": [applicant name: " + applicant.first_name + "; account number: " + appliedAccount.account_num + "; status: " + applicationStatus + "]";
	}

	public Customer getApplicant() {
		return applicant;
	}

	public void setApplicant(Customer applicant) {
		this.applicant = applicant;
	}

	public Account getAppliedAccount() {
		return appliedAccount;
	}

	public void setAppliedAccount(Account appliedAccount) {
		this.appliedAccount = appliedAccount;
	}

	public int getApplicationNum() {
		return applicationNum;
	}

	public void setApplicationNum(int applicationNum) {
		this.applicationNum = applicationNum;
	}

	public String getApplicationStatus() {
		return applicationStatus;
	}

	public void setApplicationStatus(String applicationStatus) {
		this.applicationStatus = applicationStatus;
	}
}