package edu.gatech.cs6310.repository;

import org.springframework.data.repository.CrudRepository;

import edu.gatech.cs6310.model.User;

public interface UserRepository extends CrudRepository<User, String> {
}
