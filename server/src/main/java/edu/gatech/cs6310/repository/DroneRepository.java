package edu.gatech.cs6310.repository;

import org.springframework.data.repository.CrudRepository;

import edu.gatech.cs6310.model.Drone;

public interface DroneRepository extends CrudRepository<Drone, Long> {
	Drone findByIdentifier(int identifier);
	Iterable<Drone> findAllByStoreNameOrderByIdentifierAsc(String storeName);
	Drone findByStoreNameAndIdentifier(String storeName, int identifier);
}
