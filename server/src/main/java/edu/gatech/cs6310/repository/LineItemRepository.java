package edu.gatech.cs6310.repository;

import org.springframework.data.repository.CrudRepository;

import edu.gatech.cs6310.model.LineItem;

public interface LineItemRepository extends CrudRepository<LineItem, Long> {
}
