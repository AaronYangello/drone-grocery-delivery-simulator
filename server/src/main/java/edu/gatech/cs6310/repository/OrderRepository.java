package edu.gatech.cs6310.repository;

import org.springframework.data.repository.CrudRepository;

import edu.gatech.cs6310.model.Order;

public interface OrderRepository extends CrudRepository<Order, String> {
	Order findByName(String name);
	Order findByStoreNameAndName(String storeName, String name);
	Iterable<Order> findAllByStoreNameOrderByNameAsc(String storeName);
}
