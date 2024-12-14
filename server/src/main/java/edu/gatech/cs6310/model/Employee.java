package edu.gatech.cs6310.model;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Employee extends User {
	private String taxIdentifier;

	public Employee() {}

	public String getTaxIdentifier() {
		return taxIdentifier;
	}
}
