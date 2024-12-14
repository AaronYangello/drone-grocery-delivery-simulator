package edu.gatech.cs6310.UserInput;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import edu.gatech.cs6310.RestService;
import edu.gatech.cs6310.Exception.IncorrectNumArgsException;

public class SystemCmdController implements CmdControllerInterface {
	private final RestService restService;
	
	public SystemCmdController(RestService restService) {
		this.restService = restService;
	}
	
	public String makePilot(String[] args) throws Exception {
		if(args.length != 8) {
			throw new IncorrectNumArgsException();
		}
			
		String account = args[1];
		String firstName = args[2];
		String lastName = args[3];
		String phone = args[4];
		String taxId = args[5];
		String licenseNum = args[6];
		String experience = args[7];
		
		String path = "pilots";
		
		JSONObject o = new JSONObject();
		o.put("account", account);
		o.put("firstName", firstName);
		o.put("lastName", lastName);
		o.put("phoneNumber", phone);
		o.put("taxIdentifier", taxId);
		o.put("licenseID", licenseNum);
		o.put("experience", experience);
		restService.post(path, o);
		
		return CHANGE_COMPLETED;
	}
	
	public String displayPilots() throws JSONException, Exception {
		String response = "";
		String path = "pilots";
		
		JSONArray pilots = new JSONArray(restService.get(path));
		for(int i = 0; i < pilots.length(); i++) {
			JSONObject pilot = pilots.getJSONObject(i);
			response +=  "name:" + pilot.get("fullName") +
			             ",phone:" + pilot.get("phoneNumber") +
			             ",taxID:" + pilot.get("taxIdentifier") +
			             ",licenseID:" + pilot.get("licenseID") +
			             ",experience:" + pilot.get("experience") +
			             System.lineSeparator();
		}
		response += DISPLAY_COMPLETED;
		return response;
	}
}
