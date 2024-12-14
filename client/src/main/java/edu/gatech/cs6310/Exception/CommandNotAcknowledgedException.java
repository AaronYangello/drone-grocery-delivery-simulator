package edu.gatech.cs6310.Exception;

public class CommandNotAcknowledgedException extends Exception{
	public CommandNotAcknowledgedException () {
		super("Command Not Acknowledged");
	}
}
