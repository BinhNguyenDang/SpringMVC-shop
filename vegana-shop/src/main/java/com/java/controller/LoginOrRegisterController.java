package com.java.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.java.entity.Customer;
import com.java.entity.Role;
import com.java.repository.CustomersRepository;
import com.java.service.SendMailService;

@Controller
public class LoginOrRegisterController extends CommonController {

	@Autowired
	CustomersRepository customersRepository;

	@Autowired
	SendMailService sendMailService;

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@GetMapping(value = "/login")
	public String loginOrRegister(Model model, @RequestParam("error") Optional<String> error) {
		String errorString = error.orElse("false");
		if (errorString.equals("true")) {
			model.addAttribute("error", "Tài khoản hoặc mật khẩu không chính xác, vui lòng thử lại!");
		}

		model.addAttribute("customer", new Customer());

		return "site/loginOrRegister";
	}

	// register
	@SuppressWarnings("unused")
	@RequestMapping(value = "/registered")
	public String addCourse(@Valid @ModelAttribute("customer") Customer customer, BindingResult result, ModelMap model,
			Principal principal) {

		// check error
		if (result.hasErrors()) {
			return "site/loginOrRegister";
		}

		// check email by database
		if (!checkEmail(customer.getEmail())) {
			model.addAttribute("error", "Đăng kí thất bại, Email này đã được sử dụng!");
			return "site/loginOrRegister";
		}

		// check id login by database
		if (!checkIdlogin(customer.getCustomerId())) {
			model.addAttribute("error", "Đăng kí thất bại, ID Login này đã được sử dụng!");
			return "site/loginOrRegister";
		}

		customer.setEnabled(true);
		customer.setRoleId("0");
		customer.setPassword(bCryptPasswordEncoder.encode(customer.getPassword()));

		Customer c = customersRepository.save(customer);
		sendMailService.sendMail(c.getEmail(), "Vegana Store", "<h3>Hi:" + c.getFullname()
				+ " !</h3> Chúc mừng bạn đã đăng ký tài khoản thành công tại Vegana-Store!\r\n ");
		Role role = new Role();
		role.setRoleName("ROLE_USER");
		role.setCustomer(customer);
		if (null != c) {
			model.addAttribute("message", "Đăng kí thành công, xin mời đăng nhập!");
			model.addAttribute("customer", customer);
		} else {
			model.addAttribute("error", "failure");
			model.addAttribute("customer", customer);
		}

		return "site/loginOrRegister";
	}

	// check email
	public boolean checkEmail(String email) {
		List<Customer> list = customersRepository.findAll();
		for (Customer c : list) {
			if (c.getEmail().equalsIgnoreCase(email)) {
				return false;
			}
		}
		return true;
	}

	// check ID Login
	public boolean checkIdlogin(String customerId) {
		List<Customer> listC = customersRepository.findAll();
		for (Customer c : listC) {
			if (c.getCustomerId().equalsIgnoreCase(customerId)) {
				return false;
			}
		}
		return true;
	}

}
