package edu.gatech.cs6310.UserInput;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.SortedMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import edu.gatech.cs6310.RestService;
import edu.gatech.cs6310.Exception.IncorrectNumArgsException;

public class StoreCmdController implements CmdControllerInterface {
	private final RestService restService;

	public StoreCmdController(RestService restService) {
		this.restService = restService;
	}

	public String makeStore(String[] args) throws Exception {
		String path = "stores";

		if(args.length != 3) {
			throw new IncorrectNumArgsException();
		}

		String store = args[1];
		int revenue = Integer.parseInt(args[2]);

		JSONObject o = new JSONObject();
		o.put("name", store);
		o.put("revenue", revenue);

		restService.post(path, o);

		return CHANGE_COMPLETED;
	}

	public String displayStores() throws JSONException, Exception {
		String response = "";
		String path = "stores";
		JSONArray stores = new JSONArray(restService.get(path));
		for(int i = 0; i < stores.length(); i++) {
			JSONObject store = stores.getJSONObject(i);
			response += "name:" + store.get("name") + ",revenue:" + store.get("revenue") + System.lineSeparator();
		}
		response += DISPLAY_COMPLETED;

		return response;
	}

	public String sellItem(String[] args) throws Exception {

		if(args.length != 4) {
			throw new IncorrectNumArgsException();
		}

		String store = args[1];
		String item = args[2];
		int weight = Integer.parseInt(args[3]);

		String path = "stores/" + store + "/items";

		JSONObject i = new JSONObject();
		i.put("name", item);
		i.put("weight", weight);
		JSONObject s = new JSONObject();
		s.put("name", store);
		i.put("store", s);

		restService.post(path, i);

		return CHANGE_COMPLETED;
	}

	public String displayItems(String[] args) throws JSONException, Exception {
		if(args.length != 2) {
			throw new IncorrectNumArgsException();
		}

		String response = "";

		String store = args[1];
		String path = "stores/" + store + "/items";

		JSONArray items = new JSONArray(new JSONArray(restService.get(path)));
		for(int i = 0; i < items.length(); i++) {
			JSONObject item = items.getJSONObject(i);
			response += item.get("name") + "," + item.get("weight") + System.lineSeparator();
		}
		response += DISPLAY_COMPLETED;

		return response;
	}

	public String makeDrone(String[] args) throws Exception {
		if(args.length != 5) {
			throw new IncorrectNumArgsException();
		}

		String store = args[1];
		int droneId = Integer.parseInt(args[2]);
		int capacity = Integer.parseInt(args[3]);
		int trips = Integer.parseInt(args[4]);

		String path = "stores/" + store + "/drones";

		JSONObject d = new JSONObject();
		d.put("identifier", droneId);
		d.put("totalCapacity", capacity);
		d.put("remainingTrips", trips);
		JSONObject s = new JSONObject();
		s.put("name", store);
		d.put("store", s);
		restService.post(path, d);

		return CHANGE_COMPLETED;
	}

	public String displayDrones(String[] args) throws JSONException, Exception {
		if(args.length != 2) {
			throw new IncorrectNumArgsException();
		}

		String response = "";
		String store = args[1];
		String path = "stores/" + store + "/drones";
		JSONArray drones = new JSONArray(restService.get(path));
		for(int i = 0; i < drones.length(); i++) {
			JSONObject drone = drones.getJSONObject(i);
			JSONArray orders = drone.getJSONArray("orders");
			
			response += "droneID:" + drone.get("identifier") +
					",total_cap:" + drone.get("totalCapacity") +
					",num_orders:" + orders.length() +
					",remaining_cap:" + drone.get("remainingCapacity") +
					",trips_left:" + drone.get("remainingTrips");
			if (drone.getBoolean("hasPilot") == true) {
				JSONObject pilot = drone.getJSONObject("pilot");
				response += ",flown_by:" + pilot.get("fullName");
			}

			response += System.lineSeparator();
		}
		response += DISPLAY_COMPLETED;

		return response;
	}

	public String flyDrone(String[] args) throws Exception {
		if(args.length != 4) {
			throw new IncorrectNumArgsException();
		}
		String store = args[1];
		int drone = Integer.parseInt(args[2]);
		String pilot = args[3];
		String path = "pilots/" + pilot + "/assignDrone";

		JSONObject o = new JSONObject();
		o.put("storeName", store);
		o.put("identifier", drone);
		restService.post(path, o);

		return CHANGE_COMPLETED;
	}

	public String displayOrders(String[] args) throws JSONException, Exception {
		if(args.length != 2) {
			throw new IncorrectNumArgsException();
		}
		String response = "";

		String store = args[1];
		String path = "stores/" + store + "/orders";

		JSONArray orders = new JSONArray(restService.get(path));
		for(int i = 0; i < orders.length(); i++) {
			JSONObject order = orders.getJSONObject(i);
			response += "orderID:" + order.get("name") + System.lineSeparator();
			JSONArray lineItems = order.getJSONArray("lineItems");
			JSONObject[] sortedLineItems = sortLineItems(lineItems);
			for(JSONObject lineItem : sortedLineItems) {
				JSONObject item = lineItem.getJSONObject("item");
				response += "item_name:" + item.get("name") + 
							",total_quantity:" + lineItem.get("itemQuantity") + 
							",total_cost:" + lineItem.get("totalCost") + 
							",total_weight:" + lineItem.get("totalWeight") + 
							System.lineSeparator();
			}
		}
		response += DISPLAY_COMPLETED;

		return response;
	}
	
	private JSONObject[] sortLineItems(JSONArray lineItems) {
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
