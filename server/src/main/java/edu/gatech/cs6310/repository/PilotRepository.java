package edu.gatech.cs6310.repository;

import org.springframework.data.repository.CrudRepository;

import edu.gatech.cs6310.model.Pilot;

public interface PilotRepository extends CrudRepository<Pilot, String> {
	Pilot findByAccount(String account);
	Iterable<Pilot> findAllByOrderByAccountAsc();
	Pilot findByLicenseID(String licenseID);
}
