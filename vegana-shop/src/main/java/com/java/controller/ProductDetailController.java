package com.java.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import com.java.entity.Product;
import com.java.repository.ProductRepository;
import com.java.service.ShoppingCartService;
import com.java.service.WishListService;

@Controller
public class ProductDetailController extends CommonController {

	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	ShoppingCartService shoppingCartService;
	
	@Autowired
	WishListService wishListService;

	// get productDetail
	@GetMapping(value = "/productDetail")
	public String productDetail(@RequestParam("productId") Integer productId, Model model) {

		Product product = productRepository.findById(productId).orElse(null);
		model.addAttribute("product", product);
		productByCategory(model, product.getCategory().getCategoryId());
		
		listproduct10(model);
		model.addAttribute("totalCartItemWishs", wishListService.getCount());
		model.addAttribute("totalCartItems", shoppingCartService.getCount());
		
		return "site/productDetail";
	}

	// Suggest products of the same category.
	public void productByCategory(Model model, Integer categoryId) {
		List<Product> products = productRepository.productsByCategory(categoryId);
		model.addAttribute("productByCategory", products);

	}

	// List of products on the homepage
	@ModelAttribute("listProduct10")
	public List<Product> listproduct10(Model model) {
		List<Product> productList = productRepository.listProduct10();
		model.addAttribute("productList", productList);

		return productList;
	}

}
