package com.java.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.java.entity.Customer;
import com.java.entity.Order;
import com.java.repository.CustomersRepository;
import com.java.repository.OrderRepository;
import com.java.service.ShoppingCartService;
import com.java.service.WishListService;

@Controller
public class AccountController extends CommonController {

	@Autowired
	ShoppingCartService shoppingCartService;

	@Autowired
	CustomersRepository customersRepository;

	@Autowired
	OrderRepository orderRepository;

	@Autowired
	WishListService wishListService;

	@GetMapping(value = "/account")
	public String account(Model model, Principal principal) {

		model.addAttribute("customer", new Customer());
		Customer customer = customersRepository.FindByEmail(principal.getName()).get();
		model.addAttribute("customer", customer);

		List<Order> listO2 = orderRepository.findByCustomerId(customer.getCustomerId());
		model.addAttribute("orders2", listO2);

		model.addAttribute("totalCartItemWishs", wishListService.getCount());
		model.addAttribute("totalCartItems", shoppingCartService.getCount());

		return "site/account";
	}

}
