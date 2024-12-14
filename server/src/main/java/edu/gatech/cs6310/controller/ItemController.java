package edu.gatech.cs6310.controller;

import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import org.springframework.beans.factory.annotation.*;
import org.springframework.security.access.prepost.*;

import edu.gatech.cs6310.model.*;
import edu.gatech.cs6310.repository.*;

@RestController
public class ItemController extends DeliveryServiceController {
	@Autowired
	private ItemRepository itemRepository;
	@Autowired
	private StoreRepository storeRepository;
	
	private Store lookupStore(String storeName) throws Exception {
		Optional<Store> store = storeRepository.findById(storeName);
		if (!store.isPresent()) {
			throw new Exception("store_not_found");
		}
		return store.get();
	}

	@PostMapping("/stores/{storeName}/items")
	@PreAuthorize("hasAuthority('ADMIN')")
	public Item createItem(@PathVariable String storeName, @RequestBody Item item) throws Exception {
		Store store = lookupStore(storeName);
		if(itemRepository.findByNameAndStoreName(item.getName(), storeName) != null) {
			throw new Exception("item_already_exists");
		}
		item.setStore(store);
		return itemRepository.save(item);
	}

	@GetMapping("/stores/{storeName}/items")
	public Iterable<Item> getItems(@PathVariable String storeName) throws Exception {
		lookupStore(storeName);
		return itemRepository.findAllByStoreNameOrderByNameAsc(storeName);
	}
}