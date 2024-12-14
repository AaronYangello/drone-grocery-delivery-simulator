package edu.gatech.cs6310.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class FavoriteOrder {
	@Id
	private String name;
	@OneToMany(fetch = FetchType.EAGER)
	private Set<FavoriteLineItem> favoriteLineItems;
	
	public FavoriteOrder() {}
	
	public FavoriteOrder(String name, Set<FavoriteLineItem> favoriteLineItems) {
		this.name = name;
		this.favoriteLineItems = favoriteLineItems;
	}
	
	public Set<FavoriteLineItem> getFavoriteLineItems() {
		return favoriteLineItems;
	}
	
	public String getName() {
		return name;
	}
}
