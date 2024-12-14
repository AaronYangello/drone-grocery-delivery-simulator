package edu.gatech.cs6310.controller;

import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.*;
import org.springframework.security.access.prepost.*;

import edu.gatech.cs6310.dto.CustomerUpdateDto;
import edu.gatech.cs6310.model.*;
import edu.gatech.cs6310.repository.*;

@RestController
public class CustomerController extends DeliveryServiceController {
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private ItemRepository itemRepository;
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private FavoriteOrderRepository favoriteOrderRepository;
	@Autowired
	private FavoriteLineItemRepository favoriteLineItemRepository;

	private Customer lookupCustomer(String customerAccount) throws Exception {
		Customer customer = customerRepository.findByAccount(customerAccount);
		if (customer == null) {
			throw new Exception("customer_not_found");
		}
		return customer;
	}

	private Item lookupItem(String storeName, String itemName) throws Exception {
		Item item = itemRepository.findByNameAndStoreName(itemName, storeName);
		if(item == null) {
			throw new Exception("item_not_found.");
		}
		return item;
	}

	private Order lookupOrder(String orderid) throws Exception {
		Order order = orderRepository.findByName(orderid);
		if(order == null ) {
			throw new Exception("order_not_found");
		}
		return order;
	}

	@GetMapping("/customers")
	public Iterable<Customer> getCustomers() throws Exception {
		return customerRepository.findAllByOrderByAccountAsc();
	}

	@PostMapping("/customers")
	@PreAuthorize("hasAuthority('ADMIN')")
	public Customer createCustomer(@RequestBody Customer customer) throws Exception {
		if(customerRepository.findByAccount(customer.getAccount()) != null) {
			throw new Exception("customer_account_already_exists");
		}
		return customerRepository.save(customer);
	}

	@GetMapping("/customers/{customerAccount}")
	@PreAuthorize("hasAuthority('ADMIN') OR #customerAccount == authentication.name")
	public Customer getCustomer(@PathVariable String customerAccount) throws Exception {
		return lookupCustomer(customerAccount);
	}

	@PostMapping("/customers/{customerAccount}")
	@PreAuthorize("hasAuthority('ADMIN') OR #customerAccount == authentication.name")
	public Customer updateCustomer(@PathVariable String customerAccount, @RequestBody CustomerUpdateDto customerDto) throws Exception {
		Customer customer = lookupCustomer(customerAccount);
		if(customerDto.favoriteOrderId != null) {
			Order order = lookupOrder(customerDto.favoriteOrderId);

			Set<FavoriteLineItem> favoriteLineItems = new HashSet<FavoriteLineItem>();
			for(LineItem lineItem : order.getLineItems()) {
				FavoriteLineItem favLineItem = favoriteLineItemRepository.save(new FavoriteLineItem(lineItem));
				favoriteLineItems.add(favLineItem);
			}

			FavoriteOrder fo = favoriteOrderRepository.save(new FavoriteOrder(order.getName(),favoriteLineItems));
			customer.addFavoriteOrder(fo);
		}
		if(customerDto.priceAlertItem != null && customerDto.priceAlertStoreName != null) {
			customer.addPriceAlertItem(lookupItem(customerDto.priceAlertStoreName, customerDto.priceAlertItem));
		}

		return customerRepository.save(customer);
	}
}
