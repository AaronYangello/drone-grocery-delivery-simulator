package edu.gatech.cs6310.repository;

import org.springframework.data.repository.CrudRepository;

import edu.gatech.cs6310.model.Item;

public interface ItemRepository extends CrudRepository<Item, Long> {
	
	Item findByNameAndStoreName(String name, String storeName);
	Iterable<Item> findAllByStoreNameOrderByNameAsc(String storeName);
}
