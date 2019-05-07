package com.revature.project.domain;

import java.sql.Connection;
import java.util.Scanner;

import com.revature.project.jdbc.ConnectionFactory;

public class Menus {
	Scanner sc = new Scanner(System.in);

	Connection conn = ConnectionFactory.getConnection();
	UserDAO udao = new UserDAO(conn);
	CustomerDAO cdao = new CustomerDAO(conn);
	EmployeeDAO edao = new EmployeeDAO(conn);
	AdminDAO adao = new AdminDAO(conn);
	AccountDAO acdao = new AccountDAO(conn);
	ApplicationDAO apdao = new ApplicationDAO(conn);
	customerAccountsDAO cadao = new customerAccountsDAO(conn);

	Customer currCust;
	Administrator currAdmin;
	Employee currEmpl;
	String customerName = "";
	String userName = "";
	String pw = "";

	private void editAccountMenu() {
		char option = '\0';
		System.out.println("-----------------------------------------");
		System.out.println("=======Administration Menu=======");
		System.out.println("Welcome, " + currAdmin.first_name + " \n username: " + currAdmin.username);
		System.out.println("\n");
		System.out.println("A. Change Account Status");
		System.out.println("B. Change Account Type");
		System.out.println("C. Withdraw");
		System.out.println("D. Deposit");
		System.out.println("E. Transfer");
		System.out.println("F. Add Customer to Joint Account.");
		System.out.println("G. Exit");
		do {
			System.out.println("==========================================================");
			System.out.println("Enter an option. \n");
			System.out.println("==========================================================");
			option = sc.next().charAt(0);
			System.out.println("\n");

			switch (option) {
			case 'A':
				System.out.println("Enter an account number: ");
				long account = sc.nextLong();
				System.out.println("Enter new account status: ");
				String stat = sc.next();
				acdao.updateAccountStatus(account, stat);
				break;
			case 'B':
				System.out.println("Enter an account number: ");
				long act = sc.nextLong();
				System.out.println("Enter new account type: ");
				String tp = sc.next();
				acdao.updateAccountStatus(act, tp);
				break;
			case 'C':
				// withdraw
				System.out.println("Account To Withdraw From: ");
				long actNum = sc.nextLong();
				System.out.println("Enter the amount to be Deposited: ");
				double amnt = sc.nextDouble();
				acdao.withdrawFromAccount(actNum, amnt);
				break;
			case 'D':
				// deposit
				System.out.println("Account To Deposit To: ");
				long acctNum = sc.nextLong();
				System.out.println("Enter the amount to be Deposited: ");
				double amount = sc.nextDouble();
				acdao.depositToAccount(acctNum, amount);
				break;
			case 'E':
				// transfer
				System.out.println("Account Number of Source Account: ");
				long sAcctNum = sc.nextLong();
				System.out.println("Account Number of Destination Account: ");
				long dAcctNum = sc.nextLong();
				break;
			case 'F':
				System.out.println("User to be Added: ");
				Customer c = cdao.getCustomer(sc.next());
				System.out.println("Account to be Updated: ");
				Account a = acdao.getAccount(sc.nextLong());
				acdao.addAccount(c, a);
			default:
				System.out.println("Invalid option! Please try again.");
				break;
			}

		} while (option != 'G');
		System.out.println("-----------------------------------------");
		showAdminMenu();
	}

	private void showEmployeeMenu() {
		char option = '\0';
		System.out.println("Welcome, " + currEmpl.getFirst_name() + "! \n username: " + currEmpl.getUsername());
		System.out.println("-----------------------------------------");
		System.out.println("=======Employee Menu=======");
		System.out.println("A. View All Accounts");
		System.out.println("B. View All Customers");
		System.out.println("C. View All Applications");
		System.out.println("D. Approve Application");
		System.out.println("E. Deny Applications");
		System.out.println("F. View Customer Info");
		System.out.println("G. View Account Info");
		System.out.println("H. Exit");
		apdao = new ApplicationDAO(conn);
		do {
			System.out.println("==========================================================");
			System.out.println("Enter an option. \n");
			System.out.println("==========================================================");
			option = sc.next().charAt(0);
			System.out.println("\n");

			switch (option) {
			case 'A':
				acdao.printAllAccounts();
				break;
			case 'B':
				adao.printAllCustomers();
				break;
			case 'C':
				apdao.printAllApplications();
				break;
			case 'D':
				System.out.println("Enter Application Number for Approval: ");
				apdao.approveApplication(sc.nextInt());
				break;
			case 'E':
				System.out.println("Enter Application Number for Denial: ");
				apdao.denyApplication(sc.nextInt());
				break;
			case 'F':
				System.out.println("Enter User Name for more Customer Info: ");
				cdao.printCustomerInfo(sc.next());
				break;
			case 'G':
				System.out.println("Enter Account Number for more Account Info: ");
				acdao.printAccountInfo(sc.nextLong());
				break;
			default:
				System.out.println("Thank you for using our banking services! Please come again.");
				showMainMenu();
			}
		} while (option != 'H');
	}

	public void showAdminMenu() {
		char option = '\0';
		System.out.println("-----------------------------------------");
		System.out.println("=======Administration Menu=======");
		System.out.println("Welcome, " + currAdmin.first_name + " \n username: " + currAdmin.username);
		System.out.println("\n");
		System.out.println("A. View All Account Information");
		System.out.println("B. View All Customer Information");
		System.out.println("C. View All Applications");
		System.out.println("D. Edit Applications");
		System.out.println("E. Edit Accounts");
		System.out.println("F. Exit");
		do {
			System.out.println("==========================================================");
			System.out.println("Enter an option. \n");
			System.out.println("==========================================================");
			option = sc.next().charAt(0);
			System.out.println("\n");

			switch (option) {
			case 'A':
				for(Account a : acdao.getAllAccounts()) {
					System.out.println(a.toString());
				}
				break;
			case 'B':
				adao.printAllCustomers();
				break;
			case 'C':
				apdao.printAllApplications();
				break;
			case 'D':
				System.out.println("Enter Application Number for Modification: ");
				int app_id = sc.nextInt();
				System.out.println("Would you like to 1.)Approve this Application or 2.)Deny this Application");
				int choice = sc.nextInt();
				if(choice == 1) {
					apdao.approveApplication(app_id);
				}else if (choice == 2) {
					apdao.denyApplication(app_id);
				}else
					System.out.println("No action taken.");
				break;
			case 'E':
				editAccountMenu();
				break;
			default:
				System.out.println("Thank you for using our banking services! Please come again.");
				showMainMenu();
			}
		} while (option != 'F');
	}

	public void showMainMenu() {
		char option = '\0';
		System.out.println("=======Main Menu=======");
		if (currCust != null) {
			if (currCust.isLoggedIn) {
				customerName = currCust.getFirst_name();
				userName = currCust.getUsername();
				System.out.println("Welcome, " + customerName + "; \n username: " + userName);
				System.out.println("\n");
			}
		} else if (currAdmin != null) {
			if (currAdmin.isLoggedIn) {
				customerName = currAdmin.getFirst_name();
				userName = currAdmin.getUsername();
				System.out.println("Welcome, " + customerName + "; \n username: " + userName);
				System.out.println("\n");
			}
		} else if (currEmpl != null) {
			if (currEmpl.isLoggedIn) {
				customerName = currEmpl.getFirst_name();
				userName = currEmpl.getUsername();
				System.out.println("Welcome, " + customerName + "; \n username: " + userName);
				System.out.println("\n");
			}
		} else {
			System.out.println("Welcome to the Revature Bank! \n");
		}

		System.out.println("A. Login");
		System.out.println("B. Register");
		System.out.println("C. Exit");
		System.out.println("==========================================================");
		System.out.println("Enter an option. \n");
		System.out.println("==========================================================");
		option = sc.next().charAt(0);
		System.out.println("\n");

		switch (option) {
		case 'A':
			showLoginMenu();

			break;
		case 'B':
			currCust = new Customer();
			System.out.println("-----------------------------------------");
			System.out.println("=======Registration Menu=======");
			System.out.println("User Name: ");
			userName = (sc.next());
			currCust.setUsername(userName);
			System.out.println("Password: ");
			pw = (sc.next());
			currCust.setPassword(pw);
			System.out.println("First Name: ");
			customerName = sc.next();
			currCust.setFirst_name(customerName);
			System.out.println("Last Name: ");
			currCust.setLast_name(sc.next());
			System.out.println("E-mail: ");
			currCust.setEmail_address(sc.next());
			System.out.println("Phone Number: ");
			long phone_Num = (Long.parseLong(sc.next()));
			System.out.println("Street Address: ");
			String address = sc.next();
			String streetName = sc.next();
			String streetType = sc.next();
			String street_Address = (address + " " + streetName + " " + streetType);
			currCust.setStreet_address(street_Address);
			System.out.println("City: ");
			currCust.setCity(sc.next());
			System.out.println("State: ");
			currCust.setState(sc.next());
			System.out.println("Zip Code: ");
			currCust.setZip_code(Long.parseLong(sc.next()));

			System.out.println("-----------------------------------------");
			System.out.println("\n");

			System.out.println("Thank you for registering!");
			CustomerDAO cdao = new CustomerDAO(conn);
			cdao.addCustomer(currCust);
			showMainMenu();

			break;
		case 'C':
			System.out.println("-----------------------------------------");
			System.out.println("Thank you for using our banking services! Please come again.");
			System.out.println("-----------------------------------------");
		default:
			System.out.println("Goodbye.");
			break;

		}
	}

	void showLoginMenu() {
		UserDAO udao = new UserDAO(conn);
		char option = '\0';
		Scanner sc = new Scanner(System.in);
		System.out.println("-----------------------------------------");
		System.out.println("=======Login Options=======");
		System.out.println("A. Customer Login");
		System.out.println("B. Employee Login");
		System.out.println("C. Admin Login");
		System.out.println("D. Exit");
		System.out.println("==========================================================");
		System.out.println("Enter an option. \n");
		System.out.println("==========================================================");
		option = sc.next().charAt(0);
		System.out.println("\n");

		switch (option) {
		case 'A':
			System.out.println("-----------------------------------------");
			System.out.println("=======Customer Login=======");
			System.out.println("User Name: ");
			userName = sc.next();
			System.out.println("Password: ");
			pw = sc.next();
			this.currCust = new Customer(userName,pw);
			this.currCust.isLoggedIn = this.currCust.loginAcct(userName, pw, conn);
			if (this.currCust.isLoggedIn)
				System.out.println("Login successful.");
			
			System.out.println("\n");
			System.out.println("-----------------------------------------");
			System.out.println("\n");
			showCustomerMenu();
			break;
		case 'B':
			System.out.println("-----------------------------------------");
			System.out.println("=======Employee Login=======");
			System.out.println("User Name: ");
			String userName = sc.next();
			System.out.println("Password: ");
			String pw = sc.next();
			currEmpl = new Employee(userName, pw);
			currEmpl.isLoggedIn = currEmpl.loginAcct(userName, pw, conn);
			System.out.println("\n");
			System.out.println("-----------------------------------------");
			System.out.println("\n");
			showEmployeeMenu();
			break;
		case 'C':
			System.out.println("-----------------------------------------");
			System.out.println("=======Administrator Login=======");
			System.out.println("User Name: ");
			userName = sc.next();
			System.out.println("Password: ");
			pw = sc.next();
			currAdmin = new Administrator(userName, pw);
			currAdmin.isLoggedIn = currAdmin.loginAcct(userName, pw, conn);
			System.out.println("\n");
			System.out.println("-----------------------------------------");
			System.out.println("\n");
			showAdminMenu();
			break;
		default:
			showMainMenu();
		}
	}

	private void showCustomerMenu() {
		char option = '\0';
		cdao = new CustomerDAO(conn);

		System.out.println("Welcome, " + currCust.first_name + " \n username: " + currCust.username);
		System.out.println("\n");
		System.out.println("A. View All Accounts");
		System.out.println("B. Apply for New Account");
		System.out.println("C. Edit Accounts");
		System.out.println("D. Exit");
		do {
			System.out.println("==========================================================");
			System.out.println("Enter an option. \n");
			System.out.println("==========================================================");
			option = sc.next().charAt(0);
			System.out.println("\n");

			switch (option) {
			case 'A':		
				cdao.showCustomerAccounts(currCust.username);
				break;
			case 'B':
				String type = "";
				System.out.println("Enter 1 to apply for new SAVINGS account or 2 to apply for new CHECKING account.");
				if(sc.nextInt() == 1)
					type = "savings";
				else type = "checking";
				System.out.println(type);
				Account nAcc = new Account(type, currCust);
				Application app = new Application(currCust,nAcc);
				String un = currCust.getUsername();
				long actNum = nAcc.getAcctNum();
				acdao.addAccount(currCust, nAcc); //add account to db
				cadao.insertCustAccount(currCust, actNum); //add account-user pair to joint table
				apdao.addApplication(un, actNum, "pending");
				//apdao.addApplication(app);
				break;
			case 'C':
				showAccountMenu();
				break;
			default:
				showMainMenu();
			}
		} while (option != 'D');

	}

	public void showApplicationMenu(Customer cust) {
		char option = '\0';

		System.out.println("Welcome, " + cust.getFirst_name() + "! \n username: " + cust.getUsername());
		System.out.println("-----------------------------------------");
		System.out.println("=======Account Application Menu=======");
		System.out.println("A. Savings");
		System.out.println("B. Checking");
		System.out.println("C. Joint Savings");
		System.out.println("D. Joint Checking");
		System.out.println("E. Exit");
		System.out.println("===================================================================");
		System.out.println("Enter an option for the type of account you are applying for. \n");
		System.out.println("===================================================================");
		option = sc.next().charAt(0);
		System.out.println("\n");
		Account newAcct;
		Application app;

		switch (option) {
		case 'A':
			newAcct = new Account("savings", cust);
			cust.customer_accounts.add(newAcct);
			newAcct.user_names.add(cust);
			new Application(cust, newAcct);
			app = new Application(cust, newAcct);
			showCustomerMenu();
			break;
		case 'B':
			newAcct = new Account("checking", cust);
			cust.customer_accounts.add(newAcct);
			newAcct.user_names.add(cust);
			app = new Application(cust, newAcct);
			showCustomerMenu();
			break;
		case 'C':
			int tries = 0;
			Customer secUser = null;
			do {
				System.out.println("Username for second person to add to this account: ");
				String secUN = sc.next();
				secUser = cdao.getCustomer(secUN);
				if (secUser == null) {
					System.out.println("This is not a valid user name, please try again!");
				}
				tries++;
			} while (tries < 3);
			if (secUser == null) {
				System.out.println(
						"This joint account application will only have one customer. Please contact an employee to add second customer later.");
				newAcct = new Account("savings", cust);
				newAcct.user_names.add(cust);
				new Application(cust, newAcct);
			} else {
				newAcct = new Account("savings", cust);
				newAcct.user_names.add(cust);
				newAcct.user_names.add(secUser);
				cust.customer_accounts.add(newAcct);
				secUser.customer_accounts.add(newAcct);
				new Application(cust, newAcct);
				new Application(secUser, newAcct);
			}
			showCustomerMenu();
			break;
		case 'D':
			int attempts = 0;
			Customer secndUser = null;
			do {
				System.out.println("Username for second person to add to this account: ");
				String secUN = sc.nextLine();
				secndUser = cdao.getCustomer(secUN);
				if (secndUser == null) {
					System.out.println("This is not a valid user name, please try again!");
				}
				attempts++;
			} while (attempts < 3);
			if (secndUser == null) {
				System.out.println(
						"This joint account application will only have one customer. Please contact an employee to add second customer later.");
				newAcct = new Account("checking", cust);
				newAcct.user_names.add(cust);
				new Application(cust, newAcct);
			} else {
				newAcct = new Account("checking", cust);
				newAcct.user_names.add(cust);
				newAcct.user_names.add(secndUser);
				cust.customer_accounts.add(newAcct);
				secndUser.customer_accounts.add(newAcct);
				new Application(cust, newAcct);
				new Application(secndUser, newAcct);
			}
			showCustomerMenu();
			break;
		}
		System.out.println("-----------------------------------------");
		showCustomerMenu();
	}

	public void showAccountMenu() {
		char option = '\0';
		System.out.println("Welcome, " + currCust.getFirst_name() + "! \nUsername: " + currCust.getUsername());
		System.out.println("-----------------------------------------");
		System.out.println("=======Account Menu=======");
		System.out.println("A. View Account Information In Detail");
		System.out.println("B. Deposit");
		System.out.println("C. Withdraw");
		System.out.println("D. Transfer");
		System.out.println("E. Exit");
		System.out.println("===================================================================");
		System.out.println("Enter an option for the type of account you are applying for. \n");
		System.out.println("===================================================================");
		do {
			option = sc.next().charAt(0);
			System.out.println("\n");
			switch (option) {
			case 'A':
				System.out.println("Enter an account number: ");
				long account = sc.nextLong();
				Account i = acdao.getAccount(account);
				System.out.println("-----------------------------------------");
				System.out.println("Account Number: " + i.account_num);
				System.out.println("Routing Number: " + i.routing_num);
				System.out.println("Balance: " + i.account_balance);
				System.out.println("Account Type: " + i.account_type);
				System.out.println("Account Status: " + i.account_status);
				System.out.println("Date Opened: " + i.date_opened);
				System.out.println("-----------------------------------------");
				System.out.println("\n");
				break;
			case 'B':
				// deposit
				System.out.println("Account Number of Targeted Account: ");
				long acctNum = sc.nextLong();
				System.out.println("Deposit to this account: " + acctNum);
				System.out.println("Enter the amount to be Deposited: ");
				double amount = sc.nextDouble();
				System.out.println("Depositing this amount: "+ amount);
				acdao = new AccountDAO(conn);
				acdao.depositToAccount(acctNum, amount);
				break;
			case 'C':
				// withdraw
				System.out.println("Account Number of Targeted Account: ");
				long actNum = sc.nextLong();
				System.out.println("Enter the amount to be Deposited: ");
				double amnt = sc.nextDouble();
				acdao.withdrawFromAccount(actNum, amnt);
				break;
			case 'D':
				// transfer
				System.out.println("Account Number of Source Account: ");
				long sAcctNum = sc.nextLong();
				System.out.println("Account Number of Destination Account: ");
				long dAcctNum = sc.nextLong();
				break;
			default:
				System.out.println("Invalid option! Please try again.");
				break;
			}

		} while (option != 'E');
		showCustomerMenu();
	}
}
