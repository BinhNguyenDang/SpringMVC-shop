package com.java.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.java.entity.Customer;

@Repository
public interface CustomersRepository extends JpaRepository<Customer, Integer>{
	
	@Query(value = "select * from customers where customerId = ?", nativeQuery = true)
    public Customer findCustomersLogin (String customerId);
	
	@Query(value = "select * from customers where email = ?", nativeQuery = true)
	Optional<Customer> FindByEmail(String email);

}
