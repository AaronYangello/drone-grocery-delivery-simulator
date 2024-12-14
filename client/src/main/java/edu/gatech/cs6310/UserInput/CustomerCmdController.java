package edu.gatech.cs6310.UserInput;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import edu.gatech.cs6310.RestService;
import edu.gatech.cs6310.Exception.IncorrectNumArgsException;

public class CustomerCmdController implements CmdControllerInterface {
	private final RestService restService;
	
	public CustomerCmdController(RestService restService) {
		this.restService = restService;
	}

	public String makeCustomer(String[] args) throws Exception {
		if(args.length != 7){
			throw new IncorrectNumArgsException();
		}
		String path ="customers";
		
		String account = args[1];
		String firstName = args[2];
		String lastName = args[3];
		String phone = args[4];
		String rating = args[5];
		String credit = args[6];
		
		JSONObject o = new JSONObject();
		o.put("account", account);
		o.put("firstName", firstName);
		o.put("lastName", lastName);
		o.put("phoneNumber", phone);
		o.put("rating", rating);
		o.put("credit", credit);
		
		restService.post(path, o);
		return CHANGE_COMPLETED;
	}
	
	public String displayCustomers() throws JSONException, Exception {
		String response = "";
		
		//TODO: Implement Server Side
		String path ="customers";
		
		JSONArray customers = new JSONArray(restService.get(path));
		for(int i = 0; i < customers.length(); i++) {
			JSONObject customer = customers.getJSONObject(i);
			response += "name:" + customer.get("fullName") +
			             ",phone:" + customer.get("phoneNumber") +
			             ",rating:" + customer.get("rating") +
			             ",credit:" + customer.get("credit") + 
			             System.lineSeparator();
		}
		response += DISPLAY_COMPLETED;
		return response;
	}
	
	public String startOrder(String[] args) throws Exception {
		if(args.length != 5){
			throw new IncorrectNumArgsException();
		}
		String store = args[1];
		String order = args[2];
		String drone = args[3];
		String customer = args[4];
		String path = "stores/" + store + "/orders";

		JSONObject o = new JSONObject();
		o.put("name", order);
		o.put("droneIdentifier", drone);
		o.put("customerAccount", customer);
		restService.post(path, o);
		
		return CHANGE_COMPLETED;
	}
	
	public String requestItem(String[] args) throws Exception {
		if(args.length != 6) {
			throw new IncorrectNumArgsException();
		}
		String store = args[1];
		String order = args[2];
		String item = args[3];
		int quantity = Integer.parseInt(args[4]);
		int price = Integer.parseInt(args[5]);
		String path = "stores/" + store + "/orders/" + order + "/lineItems";
		
		JSONObject o = new JSONObject();
		o.put("itemName", item);
		o.put("itemQuantity", quantity);
		o.put("itemPrice", price);
		restService.post(path, o);
		
		return CHANGE_COMPLETED;
	}
	
	public String purchaseOrder(String[] args) throws Exception {
		if(args.length != 3) {
			throw new IncorrectNumArgsException();
		}
		String store = args[1];
		String order = args[2];
		String path = "stores/" + store + "/orders/" + order + "/purchase";
		
		restService.post(path, new JSONObject());
		
		return CHANGE_COMPLETED;
	}
	
	public String cancelOrder(String[] args) throws Exception {
		if(args.length != 3) {
			throw new IncorrectNumArgsException();
		}
		String store = args[1];
		String order = args[2];
		String path = "stores/" + store + "/orders/" + order;
		restService.delete(path);
		
		return CHANGE_COMPLETED;
	}
}
