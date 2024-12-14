package edu.gatech.cs6310.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class FavoriteLineItem {
	@Id
	@GeneratedValue
	private Long id;
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "item_id", nullable = false)
	private Item item;
	private Integer quantity;
	
	public FavoriteLineItem(){}
	public FavoriteLineItem(LineItem lineItem) {
		this.item = lineItem.getItem();
		this.quantity = lineItem.getItemQuantity();
	}
	public Item getItem() {
		return item;
	}
	public Integer getQuantity() {
		return quantity;
	}
}
