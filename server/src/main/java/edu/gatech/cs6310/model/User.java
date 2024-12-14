package edu.gatech.cs6310.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class User {
	@Id
	private String account;

	@JsonIgnore
	private String password = "password";
	@JsonIgnore
	private String role = "USER";

	private String firstName;
	private String lastName;
	private String phoneNumber;

	public User() {}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getFullName() {
		return getFirstName() + "_" + getLastName();
	}
}
