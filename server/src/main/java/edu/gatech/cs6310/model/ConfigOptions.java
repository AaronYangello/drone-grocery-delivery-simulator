package edu.gatech.cs6310.model;

import javax.persistence.*;

@Entity
public class ConfigOptions {
	@Id
	private String name;
	private String exampleString;
	private String permissions;

	public ConfigOptions() {}

	public ConfigOptions(String name, String exampleString, String permissions) {
		this.name = name;
		this.exampleString = exampleString;
		this.permissions = permissions;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getExampleString() {
		return exampleString;
	}

	public void setExampleString(String exampleString) {
		this.exampleString = exampleString;
	}

	public String getPermissions() {
		return permissions;
	}

	public void setPermissions(String permissions) {
		this.permissions = permissions;
	}
}
