package edu.gatech.cs6310.model;

import java.util.*;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.*;

@Entity
public class Store {
	@Id
	private String name;
	private int revenue;
	private int droneMaintenanceAlertThreshold = 5;
	private int minRequiredCredit;
	private int minRequiredRating;
	private int pilotProbationDuration;

	@JsonIgnore
	@OneToMany(mappedBy = "store")
	private Set<Item> items;

	@JsonIgnore
	@OneToMany(mappedBy = "store")
	private Set<Drone> drones;

	@JsonIgnore
	@OneToMany(mappedBy = "store")
	private Set<Order> orders;

	public Store() {}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getRevenue() {
		return this.revenue;
	}

	public void addRevenue(int revenue) {
		this.revenue += revenue;
	}

	public Set<Item> getItems() {
		return items;
	}

	public Item getItemByName(String name) {
		for (Item item : getItems()) {
			if (name.equals(item.getName())) {
				return item;
			}
		}
		return null;
	}

	public Set<Drone> getDrones() {
		return drones;
	}

	public Drone getDroneByIdentifier(int identifier) {
		for (Drone drone : getDrones()) {
			if (drone.getIdentifier() == identifier) {
				return drone;
			}
		}
		return null;
	}

	public Set<Order> getOrders() {
		return orders;
	}

	public Order getOrderByName(String name) {
		for (Order order : getOrders()) {
			if (name.equals(order.getName())) {
				return order;
			}
		}
		return null;
	}

	public int getDroneMaintenanceAlertThreshold() {
		return droneMaintenanceAlertThreshold;
	}

	public void setDroneMaintenanceAlertThreshold(int droneMaintenanceAlertThreshold) {
		this.droneMaintenanceAlertThreshold = droneMaintenanceAlertThreshold;
	}

	public int getMinRequiredCredit() {
		return minRequiredCredit;
	}

	public void setMinRequiredCredit(int minRequiredCredit) {
		if(minRequiredCredit < 0) {
			this.minRequiredCredit = 0;
		}
		else {
			this.minRequiredCredit = minRequiredCredit;
		}
	}

	public int getMinRequiredRating() {
		return minRequiredRating;
	}

	public void setMinRequiredRating(int minRequiredRating) {
		if(minRequiredRating > 5) {
			this.minRequiredRating = 5;
		}
		else if(minRequiredRating < 0) {
			this.minRequiredRating = 0;
		}
		else {
			this.minRequiredRating = minRequiredRating;
		}
	}

	public int getPilotProbationDuration() {
		return pilotProbationDuration;
	}

	public void setPilotProbationDuration(int pilotProbationDuration) {
		if(pilotProbationDuration < 0) {
			this.pilotProbationDuration = 0;
		}
		else {
			this.pilotProbationDuration = pilotProbationDuration;
		}
	}
}
