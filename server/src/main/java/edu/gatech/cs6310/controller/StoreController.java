package edu.gatech.cs6310.controller;

import java.util.*;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.access.prepost.*;

import edu.gatech.cs6310.dto.StoreUpdateDto;
import edu.gatech.cs6310.model.*;
import edu.gatech.cs6310.repository.*;

@RestController
public class StoreController extends DeliveryServiceController {
	@Autowired
	private StoreRepository storeRepository;

	private Store lookupStore(String storeName) throws Exception {
		Optional<Store> store = storeRepository.findById(storeName);
		if (!store.isPresent()) {
			throw new Exception("store_not_found");
		}
		return store.get();
	}

	@PostMapping("/stores")
	@PreAuthorize("hasAuthority('ADMIN')")
	public Store createStore(@RequestBody Store store) throws Exception {
		Optional<Store> s = storeRepository.findById(store.getName());
		if (s.isPresent()) {
			throw new Exception("store_already_exists");
		}
		return storeRepository.save(store);
	}

	@GetMapping("/stores")
	public Iterable<Store> getStores() throws Exception {
		return storeRepository.findAllByOrderByNameAsc();
	}
	
	@PostMapping("/stores/{storeName}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public Store updateStore(@PathVariable String storeName, @RequestBody StoreUpdateDto dto) throws Exception {
		Store store = lookupStore(storeName);
		if (dto.droneMaintenanceAlertThreshold != null) {
			store.setDroneMaintenanceAlertThreshold(dto.droneMaintenanceAlertThreshold);
		}
		if(dto.minRequiredCredit != null) {
			store.setMinRequiredCredit(dto.minRequiredCredit);
		}
		if(dto.minRequiredRating != null) {
			store.setMinRequiredRating(dto.minRequiredRating);
		}
		if(dto.pilotProbationDuration != null) {
			store.setPilotProbationDuration(dto.pilotProbationDuration);
		}
		return storeRepository.save(store);
	}
	
	@GetMapping("/stores/{storeName}")
	public Store getStore(@PathVariable String storeName) throws Exception {
		return lookupStore(storeName);
	}
}
