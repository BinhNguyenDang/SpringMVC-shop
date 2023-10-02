package com.java.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;

import com.java.entity.Customer;
import com.java.repository.CustomersRepository;

@Service
public class VeganaService implements UserDetailsService{
	
	@Autowired
    CustomersRepository customersRepository;
	
	@Autowired
	BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String customerId) throws UsernameNotFoundException{
        Optional<Customer> customer = Optional.ofNullable(customersRepository.findCustomersLogin(customerId));
        final  Customer  customerLogin = new Customer();
        customerLogin.setEnabled(customer.get().getEnabled());
        customerLogin.setCustomerId(customer.get().getCustomerId());
        customerLogin.setEmail(customer.get().getEmail());
        customerLogin.setPassword(customer.get().getPassword());
        customerLogin.setFullname(customer.get().getFullname());
        customerLogin.setRoleId(customer.get().getRoleId());
        return customerLogin;
    }
    
//    public void loginFormOAuth2(OAuth2AuthenticationToken oauth2) {
//		String email = oauth2.getPrincipal().getAttribute("email");
//		String password = Long.toHexString(System.currentTimeMillis());
//		
//		UserDetails userDetails = User.withUsername(email)
//				.password(passwordEncoder.encode(password)).roles("ROLE_USER").build();
//		
//		Authentication auth = new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());
//		SecurityContextHolder.getContext().setAuthentication(auth);
//	}

}
