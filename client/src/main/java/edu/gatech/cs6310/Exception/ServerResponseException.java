package edu.gatech.cs6310.Exception;

public class ServerResponseException extends Exception{
	public ServerResponseException(String errorText) {
		super(errorText);
	}

}
