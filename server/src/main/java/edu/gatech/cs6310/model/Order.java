package edu.gatech.cs6310.model;

import java.util.*;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.*;

@Table(
	name="orders",
	uniqueConstraints=
		@UniqueConstraint(columnNames={"name", "store_name"})
)

@Entity
public class Order {
	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false)
	private String name;

	@JsonIgnoreProperties({ "orders" })
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "store_name", nullable = false)
	private Store store;

	@JsonIgnoreProperties({ "orders" })
	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "drone_id", nullable = true)
	private Drone drone;

	@JsonIgnoreProperties({ "orders" })
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "customer_account", nullable = false)
	private Customer customer;

	@JsonIgnoreProperties({ "order" })
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "order")
	private Set<LineItem> lineItems = new HashSet<LineItem>();

	public Order() {}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	public Drone getDrone() {
		return drone;
	}

	public void setDrone(Drone drone) {
		this.drone = drone;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Set<LineItem> getLineItems() {
		return lineItems;
	}
	
	public boolean containsItem(String itemName) {
		boolean itemExists = false;
		for(LineItem lineItem : lineItems) {
			if(lineItem.getItem().getName().equals(itemName)) {
				itemExists = true;
				break;
			}
		}
		return itemExists;
	}

	public int getTotalCost() {
		int totalCost = 0;
		for (LineItem lineItem : getLineItems()) {
			totalCost += lineItem.getTotalCost();
		}
		return totalCost;
	}

	public int getTotalWeight() {
		int totalWeight = 0;
		for (LineItem lineItem : getLineItems()) {
			totalWeight += lineItem.getTotalWeight();
		}
		return totalWeight;
	}
}
