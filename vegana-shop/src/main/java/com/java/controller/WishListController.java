package com.java.controller;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.java.entity.CartItemWish;
import com.java.entity.Product;
import com.java.repository.ProductRepository;
import com.java.service.ShoppingCartService;
import com.java.service.WishListService;

@Controller
public class WishListController extends CommonController{
	
	@Autowired
	ShoppingCartService shoppingCartService;
	
	@Autowired
	WishListService wishListService;
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	HttpSession session;

	// get wishList
	@GetMapping(value = "/wishlist")
	public String wishlist(Model model) {
		
		Collection<CartItemWish> cartItemWishs = wishListService.getCartItemWishs();
		model.addAttribute("cartItemWishs", cartItemWishs);
		
		model.addAttribute("totalCartItemWishs", wishListService.getCount());
		model.addAttribute("totalCartItems", shoppingCartService.getCount());
		return "site/wishList";
	}

	// add cartItem
	@GetMapping(value = "/addToWish")
	public String add(@RequestParam("productId") Integer productId, HttpServletRequest request, Model model) {

		Product product = productRepository.findById(productId).orElse(null);

		session = request.getSession();
		Collection<CartItemWish> cartItemWishs = wishListService.getCartItemWishs();
		if (product != null) {
			CartItemWish item = new CartItemWish();
			BeanUtils.copyProperties(product, item);
			item.setProduct(product);
			item.setProductId(productId);
			wishListService.add(item);
		}
		session.setAttribute("cartItemWishs", cartItemWishs);
		model.addAttribute("totalCartItemWishs", wishListService.getCount());
		model.addAttribute("totalCartItems", shoppingCartService.getCount());
		
		return "redirect:/products";
	}

	// delete cartItem
	@SuppressWarnings("unlikely-arg-type")
	@GetMapping(value = "/removeWish/{id}")
	public String remove(@PathVariable("id") Integer id, HttpServletRequest request, Model model) {
		Product product = productRepository.findById(id).orElse(null);

		Collection<CartItemWish> cartItemWishs = wishListService.getCartItemWishs();
		session = request.getSession();
		if (product != null) {
			CartItemWish item = new CartItemWish();
			BeanUtils.copyProperties(product, item);
			item.setProduct(product);
			cartItemWishs.remove(session);
			wishListService.remove(item);
		}
		model.addAttribute("totalCartItemWishs", wishListService.getCount());
		model.addAttribute("totalCartItems", wishListService.getCount());
		
		return "redirect:/wishlist";
	}
}
