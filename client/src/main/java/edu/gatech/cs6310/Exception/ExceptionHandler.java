package edu.gatech.cs6310.Exception;

import org.json.JSONObject;

import edu.gatech.cs6310.RestService;

public class ExceptionHandler {
	private final RestService restService;
	private boolean debugMode = false;
	
	public ExceptionHandler(RestService restService, boolean debugMode) {
		this.restService = restService;
		this.debugMode = debugMode;
	}
	
	public String handle(Exception e) {
		
		String response = "Error: Something went wrong. Please try again."; 
		String errorName = "generic";
		String details = "";
		
		if(debugMode == true) {
			e.printStackTrace(); 
		}
		
		if(e instanceof IndexOutOfBoundsException || e instanceof IncorrectNumArgsException) {
			errorName = "incorrect-num-args";
			details = e.getMessage();
		}
		else if(e instanceof CommandNotAcknowledgedException) {
			errorName = "cmd-not-found";
		}
		else if(e instanceof ServerResponseException) {
			errorName = "server-error";
			details = e.getMessage();
		}
		else if(e instanceof NumberFormatException) {
			errorName = "incorrect-arg-type";
		}
		else if(e.getMessage().contains("too many authentication attempts")) {
			response = "Error: You do not have permission to perform this action.";
		}
		
		try {
			response = "Error: " + getErrorText(errorName) + " " + details;
		} catch (Exception e1) {
			if(debugMode == true) {
				System.out.println("====");
				e1.printStackTrace();
				response += System.lineSeparator() + "\tException Message: " + e.getMessage();
				response += System.lineSeparator() + "\tException Message: " + e1.getMessage();
			}
		}		
		return response;
	}
	
	public String getErrorText(String errorName) throws Exception {
		String path = "errors/" + errorName;
		return new JSONObject(restService.get(path)).get("errorText").toString();
	}
}
