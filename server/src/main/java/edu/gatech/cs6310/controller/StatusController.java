package edu.gatech.cs6310.controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class StatusController extends DeliveryServiceController {
	@GetMapping("/status")
	public String getStatus() {
		return "OK";
	}
}
