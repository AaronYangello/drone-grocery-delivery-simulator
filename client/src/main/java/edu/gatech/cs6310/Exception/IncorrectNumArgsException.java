package edu.gatech.cs6310.Exception;

public class IncorrectNumArgsException extends Exception{

	public IncorrectNumArgsException() {
		super("Incorrect number of arguments");
	}
	
	public IncorrectNumArgsException(String errorMessage) {
		super(errorMessage);
	}
}
