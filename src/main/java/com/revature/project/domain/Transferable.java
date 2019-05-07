package com.revature.project.domain;

public interface Transferable {
	void transferFunds(Account source, Account destination, double amount);
}