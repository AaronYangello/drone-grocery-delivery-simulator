package edu.gatech.cs6310.model;

import java.io.Serializable;
import java.util.*;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.*;

@SuppressWarnings("serial")
@Entity
public class Customer extends User implements Serializable {
	private int rating;
	private int credit;

	@OneToMany(fetch = FetchType.EAGER)
	private Set<FavoriteOrder> favoriteOrders;

	@ManyToMany(fetch = FetchType.EAGER)
	private Set<Item> priceAlertItems;

	@JsonIgnoreProperties({ "customer" })
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "customer")
	private Set<Order> orders;

	public Customer() {}

	public int getRating() {
		return rating;
	}

	public int getCredit() {
		return credit;
	}

	public void subtractCredit(int credit) {
		this.credit -= credit;
	}

	public Set<FavoriteOrder> getFavoriteOrders(){
		return favoriteOrders;
	}

	public Set<Order> getOrders() {
		return orders;
	}

	public void addFavoriteOrder(FavoriteOrder order) {
		favoriteOrders.add(order);
	}

	public Boolean canAfford(LineItem lineItem) {
		int spending = 0;
		for (Order order : getOrders()) {
			spending += order.getTotalCost();
		}
		spending += lineItem.getTotalCost();
		return spending <= credit;
	}

	public Set<Item> getPriceAlertItems(){
		return priceAlertItems;
	}

	public void addPriceAlertItem(Item item) {
		priceAlertItems.add(item);
	}
}
