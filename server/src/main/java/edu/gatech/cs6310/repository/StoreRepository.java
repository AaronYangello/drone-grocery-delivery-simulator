package edu.gatech.cs6310.repository;

import org.springframework.data.repository.CrudRepository;

import edu.gatech.cs6310.model.Store;

public interface StoreRepository extends CrudRepository<Store, String> {
	Iterable<Store> findAllByOrderByNameAsc();
}
