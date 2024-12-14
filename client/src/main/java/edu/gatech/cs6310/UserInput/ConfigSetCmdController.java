package edu.gatech.cs6310.UserInput;

import java.time.LocalDate;
import java.util.Arrays;

import org.json.JSONObject;

import edu.gatech.cs6310.RestService;
import edu.gatech.cs6310.Exception.IncorrectNumArgsException;

public class ConfigSetCmdController implements CmdControllerInterface {
	private final RestService restService;
	
	public ConfigSetCmdController(RestService restService) {
		this.restService = restService;
	}
	
	public String droneAlert(String[] args) throws Exception {
		if(args.length != 5) {
			throw new IncorrectNumArgsException();
		}
		String storeName = args[3];
		int numberOfTrips = Integer.parseInt(args[4]);
		String path = "stores/" + storeName;
		JSONObject o = new JSONObject();
		o.put("droneMaintenanceAlertThreshold", numberOfTrips);
		restService.post(path, o);
		return CHANGE_COMPLETED;
	}
	
	public String errorText(String[] args) throws Exception {
		if(args.length < 5) {
			throw new IncorrectNumArgsException(CHECK_CMD + "config set error-message [error] [error-message-text]");
		}
		String errorName = args[3];
		String errorText = String.join(" ", Arrays.copyOfRange(args, 4, args.length));
		String path = "errors/" + errorName;
		JSONObject o  = new JSONObject();
		o.put("errorText", errorText);
		restService.post(path, o);
		
		return CHANGE_COMPLETED;
	}
	
	public String favoriteOrder(String[] args) throws Exception {
		if(args.length != 5) {
			throw new IncorrectNumArgsException(CHECK_CMD + "config set favorite-order [customer-account] [order]");
		}
		String customerAccount = args[3];
		String orderId = args[4];
		String path = "customers/" + customerAccount;
		JSONObject o = new JSONObject();
		o.put("favoriteOrderId", orderId);
		restService.post(path, o);
		
		return CHANGE_COMPLETED;
	}
	
	public String minCredit(String[] args) throws Exception {
		if(args.length != 5) {
			throw new IncorrectNumArgsException(CHECK_CMD + "config set min-credit [store-name] [min-credit-required]");
		}
		String storeName = args[3];
		int minRequiredCredit = Integer.parseInt(args[4]);
		String path = "stores/" + storeName;
		JSONObject o = new JSONObject();
		o.put("minRequiredCredit", minRequiredCredit);
		restService.post(path, o);
		
		return CHANGE_COMPLETED;
	}
	
	public String minRating(String[] args) throws Exception {
		if(args.length != 5) {
			throw new IncorrectNumArgsException(CHECK_CMD + "config set min-rating [store-name] [min-rating-required]");
		}
		String storeName = args[3];
		int minRequiredRating = Integer.parseInt(args[4]);
		String path = "stores/" + storeName;
		JSONObject o = new JSONObject();
		o.put("minRequiredRating", minRequiredRating);
		restService.post(path, o);
		
		return CHANGE_COMPLETED;
	}
	
	public String pilotProbation(String[] args) throws Exception {
		if(args.length != 5) {
			throw new IncorrectNumArgsException(CHECK_CMD + "config set pilot-probation [store-name] [num-trips-to-complete]");
		}
		String storeName = args[3];
		int pilotProbationDuration = Integer.parseInt(args[4]);
		String path = "stores/" + storeName;
		JSONObject o = new JSONObject();
		o.put("pilotProbationDuration", pilotProbationDuration);
		restService.post(path, o);
		
		return CHANGE_COMPLETED;
	}
	
	public String pilotVacation(String[] args) throws Exception {
		if(args.length != 5) {
			throw new IncorrectNumArgsException(CHECK_CMD + "config set pilot-vacation [pilot-account] [yyyy-mm-dd]");
		}
		String account = args[3];
		String dateStr = args[4];
		String response = "Please select a future date.";
		String path = "pilots/" + account;
		LocalDate date = LocalDate.parse(dateStr);
	
		if(date.compareTo(LocalDate.now()) > 0) {
			JSONObject o = new JSONObject();
			o.put("vacationDate", date);
			restService.post(path, o);
			response = CHANGE_COMPLETED;
		}
		
		return response;
	}
		
	public String priceAlert(String[] args) throws Exception {
		if(args.length != 6) {
			throw new IncorrectNumArgsException(CHECK_CMD + "config set price-alert [customer-account] [store-name] [item]");
		}
		String customerAccount = args[3];
		String storeName = args[4];
		String itemName = args[5];
		String path = "customers/" + customerAccount;
		JSONObject o = new JSONObject();
		o.put("priceAlertItem", itemName);
		o.put("priceAlertStoreName", storeName);
		restService.post(path, o);
		
		return CHANGE_COMPLETED;
	}
}
