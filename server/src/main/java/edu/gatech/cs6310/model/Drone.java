package edu.gatech.cs6310.model;

import java.util.*;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.*;

@Table(
	name="drone",
	uniqueConstraints=
		@UniqueConstraint(columnNames={"store_name", "identifier"})
)

@Entity
public class Drone {
	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false)
	private int identifier;
	private int totalCapacity;
	private int remainingTrips;

	@JsonIgnoreProperties({ "drones" })
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "store_name", nullable = false)
	private Store store;

	@JsonIgnoreProperties({ "drone" })
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "drone")
	private Set<Order> orders;

	@JsonIgnoreProperties({ "drone" })
	@OneToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "pilot_account", unique = true, nullable = true)
	private Pilot pilot;

	public Drone() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getIdentifier() {
		return identifier;
	}

	public void setIdentifier(int identifier) {
		this.identifier = identifier;
	}

	public int getTotalCapacity() {
		return totalCapacity;
	}

	public int getRemainingCapacity() {
		int currentLoad = 0;
		for(Order order : orders) {
			currentLoad += order.getTotalWeight();
		}
		return totalCapacity - currentLoad;
	}

	public int getRemainingTrips() {
	 	return remainingTrips;
	}

	public void decrementTrips() {
		remainingTrips--;
	}

	public Pilot getPilot() {
		return pilot;
	}

	public Boolean getHasPilot() {
		return pilot != null;
	}

	public void setPilot(Pilot pilot) {
		this.pilot = pilot;
	}

	public void unsetPilot() {
		pilot = null;
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	public Set<Order> getOrders() {
		return orders;
	}

	public Boolean canCarry(LineItem lineItem) {
		int totalWeight = 0;
		for (Order order : getOrders()) {
			totalWeight += order.getTotalWeight();
		}
		totalWeight += lineItem.getTotalWeight();
		return totalWeight <= totalCapacity;
	}
}
