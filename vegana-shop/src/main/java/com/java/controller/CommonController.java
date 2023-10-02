package com.java.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
//import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.java.entity.Category;
import com.java.entity.Customer;
import com.java.entity.Supplier;
import com.java.repository.CategoryRepository;
import com.java.repository.SuppliersRepository;
import com.java.service.ShoppingCartService;

@Controller
@RequestMapping(value = "/")
public class CommonController {
	
	@Autowired
	ShoppingCartService shoppingCartService;

	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	SuppliersRepository suppliersRepository;

	@ModelAttribute(value = "customer")
	public Customer initCustomer(Principal principal) {
		Customer customer = new Customer();
		if (principal != null) {
			customer = (Customer) ((Authentication) principal).getPrincipal();
//			customer = (Customer) ((OAuth2AuthenticationToken) principal).getAuthorities();
		}
		return customer;
	}

	
	@ModelAttribute("categoryList")
	public List<Category> showCategory(Model model) {
		List<Category> categoryList = categoryRepository.findAll();
		model.addAttribute("categoryList", categoryList);

		return categoryList;
	}

	@ModelAttribute("supplierList")
	public List<Supplier> supplierList(Model model) {
		List<Supplier> supplierList = suppliersRepository.findAll();
		model.addAttribute("supplierList", supplierList);

		return supplierList;
	}
	
}
