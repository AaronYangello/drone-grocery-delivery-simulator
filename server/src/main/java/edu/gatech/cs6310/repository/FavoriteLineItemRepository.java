package edu.gatech.cs6310.repository;

import org.springframework.data.repository.CrudRepository;

import edu.gatech.cs6310.model.FavoriteLineItem;

public interface FavoriteLineItemRepository extends CrudRepository<FavoriteLineItem, Long> {

}
