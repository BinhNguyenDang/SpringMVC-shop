package com.java.controller;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.java.entity.Product;
import com.java.repository.ProductRepository;
import com.java.service.ShoppingCartService;
import com.java.service.WishListService;

@Controller
public class ShopController extends CommonController {

	@Autowired
	ProductRepository productRepository;

	@Autowired
	ShoppingCartService shoppingCartService;
	
	@Autowired
	WishListService wishListService;

	// get products
	@GetMapping(value = "/products")
	public String shop(Model model, Pageable pageable, @RequestParam("page") Optional<Integer> page,
			@RequestParam("size") Optional<Integer> size) {

		int currentPage = page.orElse(1);
		int pageSize = size.orElse(12);

		Page<Product> productPage = findPaginated(PageRequest.of(currentPage - 1, pageSize));

		int totalPages = productPage.getTotalPages();
		if (totalPages > 0) {
			List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
			model.addAttribute("pageNumbers", pageNumbers);
		}

		model.addAttribute("productList", productPage);
		model.addAttribute("totalCartItemWishs", wishListService.getCount());
		model.addAttribute("totalCartItems", shoppingCartService.getCount());

		return "site/shop";
	}

	public Page<Product> findPaginated(Pageable pageable) {

		List<Product> productPage = productRepository.findAll();

		int pageSize = pageable.getPageSize();
		int currentPage = pageable.getPageNumber();
		int startItem = currentPage * pageSize;
		List<Product> list;

		if (productPage.size() < startItem) {
			list = Collections.emptyList();
		} else {
			int toIndex = Math.min(startItem + pageSize, productPage.size());
			list = productPage.subList(startItem, toIndex);
		}

		Page<Product> productPages = new PageImpl<Product>(list, PageRequest.of(currentPage, pageSize),
				productPage.size());

		return productPages;
	}

	// product by category
	@GetMapping(value = "/productByCategory")
	public String productByCategory(Model model, @RequestParam("categoryId") Integer categoryId) {
		List<Product> products = productRepository.listProductByCategory(categoryId);
		model.addAttribute("productList", products);
		model.addAttribute("totalCartItemWishs", wishListService.getCount());
		model.addAttribute("totalCartItems", shoppingCartService.getCount());

		return "site/shop";
	}

	// product by Supplier
	@GetMapping(value = "/productBySupplier")
	public String productBySupplier(Model model, @RequestParam("supplierId") Integer supplierId) {
		List<Product> products = productRepository.listProductBySupplier(supplierId);
		model.addAttribute("productList", products);
		model.addAttribute("totalCartItemWishs", wishListService.getCount());
		model.addAttribute("totalCartItems", shoppingCartService.getCount());

		return "site/shop";
	}

	// search product
	@GetMapping(value = "/searchProduct")
	public String showSearch(Model model, Pageable pageable, @RequestParam("keyword") String keyword,
			@RequestParam("size") Optional<Integer> size, @RequestParam("page") Optional<Integer> page) {

		List<Product> products = productRepository.searchProduct(keyword);

		model.addAttribute("productList", products);
		model.addAttribute("totalCartItemWishs", wishListService.getCount());
		model.addAttribute("totalCartItems", shoppingCartService.getCount());
		
		return "site/shop";
	}

}
