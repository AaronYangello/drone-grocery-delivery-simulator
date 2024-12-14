package edu.gatech.cs6310.repository;

import org.springframework.data.repository.CrudRepository;

import edu.gatech.cs6310.model.Customer;

public interface CustomerRepository extends CrudRepository<Customer, String> {
	Customer findByAccount(String account);
	Iterable<Customer> findAllByOrderByAccountAsc();
}
