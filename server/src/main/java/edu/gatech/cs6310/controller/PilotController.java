package edu.gatech.cs6310.controller;

import java.util.*;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.access.prepost.*;

import edu.gatech.cs6310.dto.PilotUpdateDto;
import edu.gatech.cs6310.dto.AssignDroneDto;
import edu.gatech.cs6310.model.*;
import edu.gatech.cs6310.repository.*;

@RestController
public class PilotController extends DeliveryServiceController {
	@Autowired
	private PilotRepository pilotRepository;
	@Autowired
	private DroneRepository droneRepository;
	@Autowired
	private StoreRepository storeRepository;

	private Pilot lookupPilot(String account) throws Exception {
		Pilot pilot = pilotRepository.findByAccount(account);
		if (pilot == null) {
			throw new Exception("pilot_not_found");
		}
		return pilot;
	}

	@GetMapping("/pilots")
	public Iterable<Pilot> getPilots() throws Exception {
		return pilotRepository.findAllByOrderByAccountAsc();
	}

	@PostMapping("/pilots")
	@PreAuthorize("hasAuthority('ADMIN')")
	public Pilot createPilot(@RequestBody Pilot pilot) throws Exception {
		if(pilotRepository.findByAccount(pilot.getAccount()) != null) {
			throw new Exception("pilot_account_already_exists");
		}
		
		if(pilotRepository.findByLicenseID(pilot.getLicenseID()) != null) {
			throw new Exception("pilot_license_id_already_exists");
		}
		
		return pilotRepository.save(pilot);
	}

	@PostMapping("/pilots/{account}/assignDrone")
	@PreAuthorize("hasAuthority('ADMIN')")
	public Pilot assignDrone(@PathVariable String account, @RequestBody AssignDroneDto dto) throws Exception {
		Optional<Store> optStore = storeRepository.findById(dto.storeName);
		if (!optStore.isPresent()) {
			throw new Exception("store_not_found");
		}
		Store store = optStore.get();
		Drone drone = store.getDroneByIdentifier(dto.identifier);
		if (drone == null) {
			throw new Exception("drone_not_found");
		}
		Pilot pilot = pilotRepository.findByAccount(account);
		if (pilot == null) {
			throw new Exception("pilot_not_found");
		}
		Drone currentDrone = pilot.getDrone();
		if (currentDrone != null) {
			currentDrone.setPilot(null);
			droneRepository.save(currentDrone);
		}
		drone.setPilot(pilot);
		droneRepository.save(drone);
		pilot.setDrone(drone);
		return pilot;
	}

	@GetMapping("/pilots/{account}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public Pilot getPilot(@PathVariable String account) throws Exception {
		return lookupPilot(account);
	}

	@PostMapping("/pilots/{account}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public Pilot addPilotVacation(@PathVariable String account, @RequestBody PilotUpdateDto dto) throws Exception {
		Pilot pilot = lookupPilot(account);

		if(dto.vacationDate != null) {
			pilot.addVacationDate(dto.vacationDate);
		}

		return pilotRepository.save(pilot);
	}
}
