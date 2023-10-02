package com.java.service;

import java.util.Collection;

import org.springframework.stereotype.Service;
import com.java.entity.CartItemWish;
import com.java.entity.Product;

@Service
public interface WishListService {
	
	int getCount();

	void clear();
	
	Collection<CartItemWish> getCartItemWishs();

	void remove(CartItemWish item);

	void add(CartItemWish item);

	void remove(Product product);

}
