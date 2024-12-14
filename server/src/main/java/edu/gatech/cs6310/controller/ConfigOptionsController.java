package edu.gatech.cs6310.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.*;

import edu.gatech.cs6310.model.*;
import edu.gatech.cs6310.repository.*;

@RestController
public class ConfigOptionsController extends DeliveryServiceController {

	@Autowired
	private ConfigOptionsRepository configOptionsRepository;

	@GetMapping("config")
	public Iterable<ConfigOptions> getConfigOptions() throws Exception {
		return configOptionsRepository.findAll();
	}

}
