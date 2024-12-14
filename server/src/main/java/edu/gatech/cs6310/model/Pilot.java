package edu.gatech.cs6310.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@SuppressWarnings("serial")
@Entity
public class Pilot extends Employee implements Serializable {
	private String licenseID;
	private int experience;

	@ElementCollection
	private Set<LocalDate> vacationDates;

	@JsonIgnoreProperties({ "pilot" })
	@OneToOne(fetch = FetchType.EAGER, optional = true, mappedBy = "pilot")
	private Drone drone;

	public Pilot() {}

	public String getLicenseID() {
		return licenseID;
	}

	public int getExperience() {
		return experience;
	}

	public void incrementExperience() {
		experience++;
	}

	public Drone getDrone() {
		return drone;
	}

	public void setDrone(Drone drone) {
		this.drone = drone;
	}

	public Set<LocalDate> getVacationDates() {
		return vacationDates;
	}

	public boolean addVacationDate(LocalDate date) {
        return vacationDates.add(date);
	}
}
