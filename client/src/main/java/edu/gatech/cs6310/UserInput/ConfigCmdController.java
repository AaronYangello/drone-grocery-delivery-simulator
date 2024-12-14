package edu.gatech.cs6310.UserInput;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import edu.gatech.cs6310.RestService;
import edu.gatech.cs6310.Exception.CommandNotAcknowledgedException;
import edu.gatech.cs6310.Exception.IncorrectNumArgsException;

public class ConfigCmdController implements CmdControllerInterface {
	private RestService restService;
	private String delim = " ";
	private ConfigSetCmdController setController;
	private ConfigShowCmdController showController;
	
	public ConfigCmdController(RestService restService, String delim) {
		this.restService = restService;
		this.delim = delim;
		this.setController = new ConfigSetCmdController(restService);
		this.showController = new ConfigShowCmdController(restService);
	}
	
	public String decode(String wholeLineInput) throws JSONException, CommandNotAcknowledgedException, Exception {
		String response = "";
		String[] tokens = wholeLineInput.split(delim);
		
		if(tokens[1].equals("list")) {
			if(tokens.length != 2) {
				throw new IncorrectNumArgsException(CHECK_CMD + "config list");
			}
			response = listConfigOptions();
		}
		else if(tokens[1].equals("set")) {
			if(tokens[2].equals("drone-alert")) {
				response = setController.droneAlert(tokens);
			}
			else if(tokens[2].equals("error-message")) {
				response = setController.errorText(tokens);
			}
			else if(tokens[2].equals("favorite-order")) {
				response = setController.favoriteOrder(tokens);
			}
			else if(tokens[2].equals("min-credit")) {
				response = setController.minCredit(tokens);		
			}
			else if(tokens[2].equals("min-rating")) {
				response = setController.minRating(tokens);
			}
			else if(tokens[2].equals("pilot-probation")) {
				response = setController.pilotProbation(tokens);
			}
			else if(tokens[2].equals("pilot-vacation")) {
				response = setController.pilotVacation(tokens);
			}
			else if(tokens[2].equals("price-alert")) {
				response = setController.priceAlert(tokens);
			}
			else {
				throw new CommandNotAcknowledgedException();
			}
		}
		else if(tokens[1].equals("show")) {
			if(tokens[2].equals("drone-alert")) {
				response = showController.droneAlert(tokens);
			}
			else if(tokens[2].equals("error-message")) {
				response = showController.errorText(tokens);
			}
			else if(tokens[2].equals("favorite-order")) {
				response = showController.favoriteOrder(tokens);
			}
			else if(tokens[2].equals("min-credit")) {
				response = showController.minCredit(tokens);		
			}
			else if(tokens[2].equals("min-rating")) {
				response = showController.minRating(tokens);
			}
			else if(tokens[2].equals("pilot-probation")) {
				response = showController.pilotProbation(tokens);
			}
			else if(tokens[2].equals("pilot-vacation")) {
				response = showController.pilotVacation(tokens);
			}
			else if(tokens[2].equals("price-alert")) {
				response = showController.priceAlert(tokens);
			}
			else {
				throw new CommandNotAcknowledgedException();
			}
		}
		else {
			throw new CommandNotAcknowledgedException();
		}
		
		return response;
	}
	
	public String listConfigOptions() throws JSONException, Exception {
		String response = "";
		String path = "config";
		
		JSONArray configOptions = new JSONArray(restService.get(path));
		for(int i = 0; i < configOptions.length(); i++) {
			JSONObject option = configOptions.getJSONObject(i);
			response += option.get("name") + "\t" + option.get("exampleString") + System.lineSeparator();
		}
		response += DISPLAY_COMPLETED;
		
		return response;
	}
}
