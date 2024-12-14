package edu.gatech.cs6310.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.*;

@Table(
	name="item",
	uniqueConstraints=
		@UniqueConstraint(columnNames={"store_name", "name"})
)

@Entity
public class Item {

	@Id
	@GeneratedValue
	private Long id;

	@JsonIgnoreProperties({ "items" })
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "store_name", nullable = false)
	private Store store;
	@Column(nullable = false)
	private String 	name;
	private int 	weight;

	public Item() {}

	public Long getId() {
		return id;
	}
	public String getName() { 
		return name;
	}
	public int getWeight() {
		return weight;
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}
}
