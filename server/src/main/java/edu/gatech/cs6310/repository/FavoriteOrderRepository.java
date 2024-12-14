package edu.gatech.cs6310.repository;

import org.springframework.data.repository.CrudRepository;

import edu.gatech.cs6310.model.FavoriteOrder;

public interface FavoriteOrderRepository extends CrudRepository<FavoriteOrder, String> {
	
}
