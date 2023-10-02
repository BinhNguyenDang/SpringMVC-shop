package com.java.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.java.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
	
	// Hiển thị Top 10 sách bán chạy nhất
	@Query(value = "SELECT p.productId,\r\n"
			+ "  COUNT(*) AS SoLuong\r\n"
			+ "FROM orderdetails p\r\n"
			+ "JOIN products c ON p.productId = c.productId\r\n"
			+ "GROUP BY p.productId\r\n"
			+ "ORDER by SoLuong DESC limit 10;", nativeQuery = true)
	public List<Object[]> topSellingProduct10();

	@Query(value = "select * from products o where productId in :ids", nativeQuery = true)
	List<Product> findByInventoryIds(@Param("ids") List<Integer> listProductId);
	
	// Cart Item
	@Query(value = "SELECT * FROM products where productId = ?" , nativeQuery = true)
    public Product findByIdProduct (int productId);

	// Hiển thị danh sách product mới nhất ở trang chủ LIMIT = 10
	@Query(value = "SELECT * FROM products ORDER BY enteredDate DESC limit 10", nativeQuery = true)
	public List<Product> listProduct10();

	// List Sản phẩm by danh mục
	@Query(value = "SELECT * FROM products WHERE categoryId = ?", nativeQuery = true)
	public List<Product> listProductByCategory(Integer categoryId);
	
	@Query(value = "select * from products where categoryId = ?", nativeQuery = true)
	Page<Product> findAllProductByCategoryId(Integer id, Pageable pageable);

	// List Sản phẩm by nhà cung cấp
	@Query(value = "SELECT * FROM products where supplierId = ?", nativeQuery = true)
	public List<Product> listProductBySupplier(Integer supplierId);

	// Search Product
	@Query(value = "SELECT * FROM products WHERE name LIKE %?1%", nativeQuery = true)
	public List<Product> searchProduct(String name);
	
	// Gợi ý sản phẩm cùng thể loại
	@Query(value = "SELECT \r\n"
			+ "*FROM products AS p\r\n"
			+ "WHERE p.categoryId = ?;" , nativeQuery = true)
	List<Product> productsByCategory(Integer categoryId);

}
