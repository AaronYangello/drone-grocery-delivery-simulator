package edu.gatech.cs6310.controller;

import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import org.springframework.beans.factory.annotation.*;
import org.springframework.security.access.prepost.*;

import edu.gatech.cs6310.model.*;
import edu.gatech.cs6310.repository.*;

@RestController
public class DroneController extends DeliveryServiceController {
	@Autowired
	private DroneRepository droneRepository;
	@Autowired
	private StoreRepository storeRepository;

	@GetMapping("/stores/{storeName}/drones")
	public Iterable<Drone> getDrones(@PathVariable String storeName) throws Exception {
		if (!storeRepository.findById(storeName).isPresent()) {
			throw new Exception("store_not_found");
		}
		return droneRepository.findAllByStoreNameOrderByIdentifierAsc(storeName);
	}

	@PostMapping("/stores/{storeName}/drones")
	@PreAuthorize("hasAuthority('ADMIN')")
	public Drone createDrone(@PathVariable String storeName, @RequestBody Drone drone) throws Exception {
		Optional<Store> store = storeRepository.findById(storeName);
		if (!store.isPresent()) {
			throw new Exception("store_not_found");
		}
		if(droneRepository.findByStoreNameAndIdentifier(storeName, drone.getIdentifier()) != null) {
			throw new Exception("drone_already_exists");
		}
		drone.setStore(store.get());
		return droneRepository.save(drone);
	}
}
