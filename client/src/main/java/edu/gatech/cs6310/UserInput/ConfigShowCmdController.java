package edu.gatech.cs6310.UserInput;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import edu.gatech.cs6310.RestService;
import edu.gatech.cs6310.Exception.IncorrectNumArgsException;

public class ConfigShowCmdController implements CmdControllerInterface {
	private final RestService restService;
	
	
	public ConfigShowCmdController(RestService restService) {
		this.restService = restService;
	}
	
	public String droneAlert(String[] args) throws Exception {
		if(args.length != 4) {
			throw new IncorrectNumArgsException(CHECK_CMD + "config show drone-alert [store-name]");
		}
		String storeName = args[3];
		String path = "stores/" + storeName;
		JSONObject o = new JSONObject(restService.get(path));
		String response = o.get("droneMaintenanceAlertThreshold").toString() + System.lineSeparator();
		response += DISPLAY_COMPLETED;
		
		return response;
	}
	
	public String errorText(String[] args) throws Exception {
		if(args.length != 3) {
			throw new IncorrectNumArgsException(CHECK_CMD + "config show error-message");
		}
		String path = "errors/";
		
		String response = "";
		JSONArray errors  = new JSONArray(restService.get(path));
		for(int i = 0; i < errors.length(); i++) {
			JSONObject error = errors.getJSONObject(i);
			response += "name:" + error.get("name") + ",error-message:" + error.get("errorText") + System.lineSeparator();
		}
		response += DISPLAY_COMPLETED;
		
		return response;
	}
	
	public String favoriteOrder(String[] args) throws Exception {
		if(args.length != 4) {
			throw new IncorrectNumArgsException(CHECK_CMD + "config show favorite-order [customer-account]");
		}
		String customerAccount = args[3];
		String path = "customers/" + customerAccount;
		
		String response = "";
		JSONObject o = new JSONObject(restService.get(path));
		JSONArray favoriteOrders = o.getJSONArray("favoriteOrders");
		for(int i = 0; i < favoriteOrders.length(); i++) {
			JSONObject favoriteOrder = favoriteOrders.getJSONObject(i);
			response += "name:" + favoriteOrder.get("name") + ",lines:" + System.lineSeparator();
			JSONObject[] favoriteLineItems = sortFavoritLineItems(favoriteOrder.getJSONArray("favoriteLineItems"));
			for(JSONObject favoriteLineItem : favoriteLineItems) {
				JSONObject item = favoriteLineItem.getJSONObject("item");
				response += "\titem:" + item.get("name") + ",quantity:" + favoriteLineItem.get("quantity") + System.lineSeparator();
			}
		}
		response += DISPLAY_COMPLETED;
		
		return response;
	}
	
	public String minCredit(String[] args) throws Exception {
		if(args.length != 4) {
			throw new IncorrectNumArgsException(CHECK_CMD + "config show min-credit [store-name]");
		}
		String storeName = args[3];
		String path = "stores/" + storeName;
		JSONObject o = new JSONObject(restService.get(path));
		String response = o.get("minRequiredCredit").toString() + System.lineSeparator();
		response += DISPLAY_COMPLETED;
		
		return response;
	}
	
	public String minRating(String[] args) throws Exception {
		if(args.length != 4) {
			throw new IncorrectNumArgsException(CHECK_CMD + "config show min-rating [store-name]");
		}
		String storeName = args[3];
		String path = "stores/" + storeName;
		JSONObject o = new JSONObject(restService.get(path));
		String response = o.get("minRequiredRating").toString() + System.lineSeparator();
		response += DISPLAY_COMPLETED;
		
		return response;
	}
	
	public String pilotProbation(String[] args) throws Exception {
		if(args.length != 4) {
			throw new IncorrectNumArgsException(CHECK_CMD + "config show pilot-probation [store-name]");
		}
		String storeName = args[3];
		String path = "stores/" + storeName;
		JSONObject o = new JSONObject(restService.get(path));
		String response = o.get("pilotProbationDuration").toString() + System.lineSeparator();
		response += DISPLAY_COMPLETED;
		
		return response;
	}
	
	public String pilotVacation(String[] args) throws Exception {
		if(args.length != 4) {
			throw new IncorrectNumArgsException(CHECK_CMD + "config show pilot-vacation [pilot-account]");
		}
		String account = args[3];
		String path = "pilots/" + account;
		JSONObject o = new JSONObject(restService.get(path));
		String response = "";
		
		JSONArray vacationDates = o.getJSONArray("vacationDates");
		for(int i = 0; i < vacationDates.length(); i++) {
			response += vacationDates.get(i).toString() + System.lineSeparator();
		}
		
		response += DISPLAY_COMPLETED;
		
		return response;
	}
		
	public String priceAlert(String[] args) throws Exception {
		if(args.length != 4) {
			throw new IncorrectNumArgsException(CHECK_CMD + "config show price-alert [customer-account]");
		}
		String customerAccount = args[3];
		String path = "customers/" + customerAccount;
		
		String response = "";
		JSONObject o = new JSONObject(restService.get(path));
		
		JSONArray priceAlertItems = o.getJSONArray("priceAlertItems");
		
		
		
		List<JSONObject> sorted = new ArrayList<JSONObject>();
	    for (int i = 0; i < priceAlertItems.length(); i++) {
	        sorted.add(priceAlertItems.getJSONObject(i));
	    }
		
		Collections.sort(sorted, new Comparator<JSONObject>() {
	        @Override
	        public int compare(JSONObject item1, JSONObject item2) {
	            String item1name = item1.get("name").toString();
	            String item2name = item2.get("name").toString();
	            
	            return item1name.compareTo(item2name);
	        }
	    });		
		
		for(JSONObject item : sorted) {
			response += "item:" + item.get("name") + System.lineSeparator();
		}
		response += DISPLAY_COMPLETED;
		
		return response;
	}
	
	private JSONObject[] sortFavoritLineItems(JSONArray lineItems) {
		List<JSONObject> jsonValues = new ArrayList<JSONObject>();
	    for (int i = 0; i < lineItems.length(); i++) {
	        jsonValues.add(lineItems.getJSONObject(i));
	    }
		
		Collections.sort(jsonValues, new Comparator<JSONObject>() {
	        @Override
	        public int compare(JSONObject lineItem1, JSONObject lineItem2) {
	            String item1name = lineItem1.getJSONObject("item").get("name").toString();
	            String item2name = lineItem2.getJSONObject("item").get("name").toString();
	            
	            return item1name.compareTo(item2name);
	        }
	    });

		
		return jsonValues.toArray(new JSONObject[jsonValues.size()]);
	}
}
