package com.java.controller.admin;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.java.entity.Customer;
import com.java.repository.CustomersRepository;

@Controller
public class IndexAdminController {
	
	@Autowired
	CustomersRepository customersRepository;
	
	@GetMapping(value = "admin/home")
	public String indexAdmin(Model model, Principal principal) {
		model.addAttribute("customer", new Customer());
		Customer customer = customersRepository.FindByEmail(principal.getName()).get();
		model.addAttribute("customer", customer);
		
		return "admin/index";
	}

}
