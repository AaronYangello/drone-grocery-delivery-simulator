package edu.gatech.cs6310.security;

import java.util.*;

import org.springframework.beans.factory.annotation.*;

import edu.gatech.cs6310.model.*;
import edu.gatech.cs6310.repository.*;

public class SecurityHelper {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private StoreRepository storeRepository;

	public Boolean orderBelongsToUser(String storeName, String orderName, String account) throws Exception {
		Optional<Store> optStore = storeRepository.findById(storeName);
		if (!optStore.isPresent()) {
			throw new Exception("store_not_found");
		}
		Store store = optStore.get();
		Order order = store.getOrderByName(orderName);
		if (order == null) {
			throw new Exception("order_not_found");
		}
		return account.equals(order.getCustomer().getAccount());
	}
}
