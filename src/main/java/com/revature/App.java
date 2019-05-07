package com.revature;

// trying to connect to gitHub

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;



import com.revature.project.domain.*;

public class App {
	final static Logger logger = Logger.getLogger(App.class);

	public static void main(String[] args) {
		logger.assertLog(true, "Test");
		BasicConfigurator.configure();
		logger.info("Application Start");
		boolean run = true;
		Menus myMenu = new Menus();
		myMenu.showMainMenu();
	}
}