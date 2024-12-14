package edu.gatech.cs6310.repository;

import org.springframework.data.repository.CrudRepository;

import edu.gatech.cs6310.model.SystemError;

public interface SystemErrorRepository extends CrudRepository<SystemError, String> {
	SystemError findByName(String name);
}
