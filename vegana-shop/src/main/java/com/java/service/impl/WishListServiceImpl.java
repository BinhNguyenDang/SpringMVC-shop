package com.java.service.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.java.entity.CartItemWish;
import com.java.entity.Product;
import com.java.service.WishListService;

@Service
public class WishListServiceImpl implements WishListService{

	private Map<Integer, CartItemWish> map = new HashMap<Integer, CartItemWish>();

	@Override
	public void clear() {

		map.clear();

	}

	@Override
	public Collection<CartItemWish> getCartItemWishs() {

		return map.values();
	}

	@Override
	public void remove(CartItemWish item) {
		
		map.remove(item.getProductId());
		
	}

	@Override
	public void add(CartItemWish item) {
		CartItemWish existedItem = map.get(item.getProductId());
		if (existedItem != null) {
			existedItem.setUnitPrice(item.getUnitPrice());
//			existedItem.setQuantity(item.getQuantity() + existedItem.getQuantity());
//			existedItem.setTotalPrice(item.getTotalPrice() + existedItem.getUnitPrice() * existedItem.getQuantity());
		} else {
			map.put(item.getProductId(), item);
		}
		
	}

	@Override
	public void remove(Product product) {
		
	}

	@Override
	public int getCount() {
		if (map.isEmpty()) {
			return 0;
		}
		return map.values().size();
	}
}
