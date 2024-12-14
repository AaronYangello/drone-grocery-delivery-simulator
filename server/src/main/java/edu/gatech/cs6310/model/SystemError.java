package edu.gatech.cs6310.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class SystemError {
	@Id
	private String name;
	private String errorText;
	
	public SystemError(){}
	
	public String getName() {
		return name;
	}
	
	public String getErrorText() {
		return errorText;
	}
	
	public void setErrorText(String errorText) {
		this.errorText = errorText;
	}
}
