package edu.gatech.cs6310.repository;

import org.springframework.data.repository.CrudRepository;

import edu.gatech.cs6310.model.ConfigOptions;

public interface ConfigOptionsRepository extends CrudRepository<ConfigOptions, String> {
	ConfigOptions findByName(String name);
}
