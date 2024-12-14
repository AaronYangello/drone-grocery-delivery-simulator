package edu.gatech.cs6310.controller;

import java.util.*;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.access.prepost.*;

import edu.gatech.cs6310.model.*;
import edu.gatech.cs6310.repository.*;
import edu.gatech.cs6310.dto.LineItemDto;

@RestController
public class LineItemController extends DeliveryServiceController {
	@Autowired
	private LineItemRepository lineItemRepository;
	@Autowired
	private StoreRepository storeRepository;

	@PostMapping("/stores/{storeName}/orders/{orderName}/lineItems")
	@PreAuthorize("hasAuthority('ADMIN') OR @securityHelper.orderBelongsToUser(#storeName, #orderName, authentication.name)")
	public LineItem createLineItem(
		@PathVariable String storeName,
		@PathVariable String orderName,
		@RequestBody LineItemDto dto
	) throws Exception {		
		Optional<Store> store = storeRepository.findById(storeName);
		if (!store.isPresent()) {
			throw new Exception("store_not_found");
		}
		Order order = store.get().getOrderByName(orderName);
		if (order == null) {
			throw new Exception("order_not_found");
		}
		if(order.containsItem(dto.itemName)) {
			throw new Exception("item_exists_in_order");
		}
		Item item = store.get().getItemByName(dto.itemName);
		if (item == null) {
			throw new Exception("item_not_found");
		}
		LineItem lineItem = new LineItem();
		lineItem.setOrder(order);
		lineItem.setItem(item);
		lineItem.setItemQuantity(dto.itemQuantity);
		lineItem.setItemPrice(dto.itemPrice);
		Drone drone = order.getDrone();
		if (!drone.canCarry(lineItem)) {
			throw new Exception("drone_can't_carry");
		}
		Customer customer = order.getCustomer();
		if (!customer.canAfford(lineItem)) {
			throw new Exception("customer_can't_afford");
		}
		return lineItemRepository.save(lineItem);
	}

	@GetMapping("/stores/{storeName}/orders/{orderName}/lineItems")
	public Iterable<LineItem> getLineItems(
		@PathVariable String storeName,
		@PathVariable String orderName
	) throws Exception {
		Optional<Store> store = storeRepository.findById(storeName);
		if (!store.isPresent()) {
			throw new Exception("store_not_found");
		}
		Order order = store.get().getOrderByName(orderName);
		if (order == null) {
			throw new Exception("order_not_found");
		}
		return order.getLineItems();
	}
}
