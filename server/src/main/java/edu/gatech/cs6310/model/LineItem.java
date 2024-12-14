package edu.gatech.cs6310.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.*;

@Table(
	name="line_item",
	uniqueConstraints=
		@UniqueConstraint(columnNames={"order_id", "item_id"})
)

@Entity
public class LineItem {

	@Id
	@GeneratedValue
	private Long id;

	@JsonIgnoreProperties({ "lineItems" })
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "order_id", nullable = false)
	private Order order;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "item_id", nullable = false)
	private Item item;

	private int itemQuantity;
	private int itemPrice;

	public LineItem() {}

	public Long getId() {
		return this.id;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public int getItemQuantity() {
		return itemQuantity;
	}

	public void setItemQuantity(int itemQuantity) {
		this.itemQuantity = itemQuantity;
	}

	public int getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(int itemPrice) {
		this.itemPrice = itemPrice;
	}

	public int getTotalCost() {
		return itemPrice * itemQuantity;
	}

	public int getTotalWeight() {
		return item.getWeight() * itemQuantity;
	}
}
