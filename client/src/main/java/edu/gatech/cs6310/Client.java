package edu.gatech.cs6310;

import java.util.Scanner;

import edu.gatech.cs6310.Exception.CommandNotAcknowledgedException;
import edu.gatech.cs6310.Exception.ExceptionHandler;
import edu.gatech.cs6310.UserInput.ConfigCmdController;
import edu.gatech.cs6310.UserInput.CustomerCmdController;
import edu.gatech.cs6310.UserInput.StoreCmdController;
import edu.gatech.cs6310.UserInput.SystemCmdController;

public class Client {

	public static void main(String... args) throws Exception {
		Session session = new Session("admin", "admin");
		RestService restService = new RestService("http://api:8080/", session.getHttpClient());

		StoreCmdController storeController = new StoreCmdController(restService);
		SystemCmdController systemController = new SystemCmdController(restService);
		CustomerCmdController customerController = new CustomerCmdController(restService);
		ConfigCmdController configController = new ConfigCmdController(restService, " ");
		ExceptionHandler exceptionHandler = new ExceptionHandler(restService, false);
		
		Scanner commandLineInput = new Scanner(System.in);
		String wholeInputLine;
		String[] tokens;
		final String DELIMITER = ",";

		System.out.println("Welcome to the Grocery Express Delivery Service!");
		
		boolean running = true;
		
		while (running) {
			
			String response = "";
			try {
				wholeInputLine = commandLineInput.nextLine();
				tokens = wholeInputLine.split(DELIMITER);
				System.out.println("> " + wholeInputLine);

				if (tokens[0].equals("login")) {
					session = new Session(tokens[1], tokens[2]);
					restService.setHttpClient(session.getHttpClient());
					response = SystemCmdController.CHANGE_COMPLETED;

				} else if (tokens[0].equals("make_store")) {
					response = storeController.makeStore(tokens);

				} else if (tokens[0].equals("display_stores")) {
					response = storeController.displayStores();

				} else if (tokens[0].equals("sell_item")) {
					response = storeController.sellItem(tokens);

				} else if (tokens[0].equals("display_items")) {
					response = storeController.displayItems(tokens);

				} else if (tokens[0].equals("make_pilot")) {
					response = systemController.makePilot(tokens);

				} else if (tokens[0].equals("display_pilots")) {
					response = systemController.displayPilots();

				} else if (tokens[0].equals("make_drone")) {
					response = storeController.makeDrone(tokens);

				} else if (tokens[0].equals("display_drones")) {
					response = storeController.displayDrones(tokens);

				} else if (tokens[0].equals("fly_drone")) {
					response = storeController.flyDrone(tokens);

				} else if (tokens[0].equals("make_customer")) {
					response = customerController.makeCustomer(tokens);

				} else if (tokens[0].equals("display_customers")) {
					response = customerController.displayCustomers();

				} else if (tokens[0].equals("start_order")) {
					response = customerController.startOrder(tokens);

				} else if (tokens[0].equals("display_orders")) {
					response = storeController.displayOrders(tokens);

				} else if (tokens[0].equals("request_item")) {
					response = customerController.requestItem(tokens);

				} else if (tokens[0].equals("purchase_order")) {
					response = customerController.purchaseOrder(tokens);

				} else if (tokens[0].equals("cancel_order")) {
					response = customerController.cancelOrder(tokens);

				} else if (tokens[0].length() >= 6 && tokens[0].substring(0, 6).equals("config")) {
					response = configController.decode(wholeInputLine);

				} else if (tokens[0].equals("stop")) {
					response = "stop acknowledged";
					running = false;
					
				} else {
					throw new CommandNotAcknowledgedException();
				}

			} catch (Exception e) {
				response = exceptionHandler.handle(e);
			}

			System.out.println(response);
		}

		System.out.println("simulation terminated");
		commandLineInput.close();
	}
}
