package edu.gatech.cs6310.controller;

import java.util.*;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.access.prepost.*;
import org.springframework.transaction.annotation.Transactional;

import edu.gatech.cs6310.model.*;
import edu.gatech.cs6310.dto.OrderDto;
import edu.gatech.cs6310.repository.*;

@RestController
public class OrderController extends DeliveryServiceController {
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private StoreRepository storeRepository;
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private DroneRepository droneRepository;
	@Autowired
	private PilotRepository pilotRepository;

	private Order lookupOrder(String storeName, String orderName) throws Exception {
		Order order = orderRepository.findByStoreNameAndName(storeName, orderName);
		if(order == null) {
			throw new Exception("order_not_found");
		}

		return order;
	}

	@PostMapping("/stores/{storeName}/orders")
	@PreAuthorize("hasAuthority('ADMIN') OR #dto.customerAccount == authentication.name")
	public Order createOrder(
		@PathVariable String storeName,
		@RequestBody OrderDto dto
	) throws Exception {
		if(orderRepository.findByStoreNameAndName(storeName, dto.name) != null) {
			throw new Exception("order_id_already_exists");
		}
		
		Order order = new Order();
		order.setName(dto.name);
		Optional<Store> store = storeRepository.findById(storeName);
		if (!store.isPresent()) {
			throw new Exception("store_not_found");
		}
		order.setStore(store.get());
		Drone drone = store.get().getDroneByIdentifier(dto.droneIdentifier);
		if (drone == null) {
			throw new Exception("drone_not_found");
		}
		order.setDrone(drone);
		Customer customer = customerRepository.findByAccount(dto.customerAccount);
		if (customer == null) {
			throw new Exception("customer_not_found");
		}
		order.setCustomer(customer);
		return orderRepository.save(order);
	}

	@GetMapping("/stores/{storeName}/orders")
	public Iterable<Order> getOrders(@PathVariable String storeName) throws Exception {
		if(!storeRepository.findById(storeName).isPresent()) {
			throw new Exception("store_not_found");
		}
		Iterable<Order> orders = orderRepository.findAllByStoreNameOrderByNameAsc(storeName);
		return orders;
	}

	@DeleteMapping("/stores/{storeName}/orders/{orderName}")
	public Map<String, Boolean> cancelOrder(@PathVariable String storeName, @PathVariable String orderName) throws Exception{
		if(!storeRepository.findById(storeName).isPresent()){
			throw new Exception("store_not_found");
		}
		Order order = lookupOrder(storeName, orderName);
		orderRepository.delete(order);
		Map<String, Boolean> response = new HashMap<String, Boolean>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

	@PostMapping("/stores/{storeName}/orders/{orderName}/purchase")
	@Transactional(rollbackFor = Exception.class)
	@PreAuthorize("hasAuthority('ADMIN') OR @securityHelper.orderBelongsToUser(#storeName, #orderName, authentication.name)")
	public Boolean purchaseOrder(
		@PathVariable String storeName,
		@PathVariable String orderName
	) throws Exception {
		Optional<Store> optStore = storeRepository.findById(storeName);
		if (!optStore.isPresent()) {
			throw new Exception("store_not_found");
		}
		Store store = optStore.get();
		Order order = store.getOrderByName(orderName);
		if (order == null) {
			throw new Exception("order_not_found");
		}
		Drone drone = order.getDrone();
		if (!drone.getHasPilot()) {
			throw new Exception("drone_has_no_pilot");
		}
		if (drone.getRemainingTrips() <= 0) {
			throw new Exception("drone_needs_maintenance");
		}
		Customer customer = order.getCustomer();
		customer.subtractCredit(order.getTotalCost());
		customerRepository.save(customer);
		store.addRevenue(order.getTotalCost());
		storeRepository.save(store);
		Pilot pilot = drone.getPilot();
		pilot.incrementExperience();
		pilotRepository.save(pilot);
		drone.decrementTrips();
		orderRepository.delete(order);
		droneRepository.save(drone);
		return true;
	}
}
