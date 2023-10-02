package com.java.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.java.entity.OrderDetail;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer>{
	
	@Query(value = "select * from orderdetails where orderId = ?", nativeQuery = true)
	List<OrderDetail> findByOrderId(int id);
	
	// thống kê theo sản phẩm được bán ra
    @Query(value = "SELECT p.name ,  \r\n"
    		+ "SUM(o.quantity) as quantity ,\r\n"
    		+ "SUM(o.quantity * o.total_price) as sum,\r\n"
    		+ "AVG(o.total_price) as avg,\r\n"
    		+ "Min(o.total_price) as min, \r\n"
    		+ "max(o.total_price) as max\r\n"
    		+ "FROM orderdetails o\r\n"
    		+ "INNER JOIN products p ON o.productId = p.productId\r\n"
    		+ "GROUP BY p.name;", nativeQuery = true)

    public List<Object[]> repo();
    
    // Thống kê theo thể loại được bán ra
    @Query(value = "SELECT c.name , \r\n"
    		+ "SUM(o.quantity) as quantity ,\r\n"
    		+ "SUM(o.quantity * o.total_price) as sum,\r\n"
    		+ "AVG(o.total_price) as avg, \r\n"
    		+ "Min(o.total_price) as min,\r\n"
    		+ "max(o.total_price) as max \r\n"
    		+ "FROM orderdetails o\r\n"
    		+ "INNER JOIN products p ON o.productId = p.productId\r\n"
    		+ "INNER JOIN categories c ON p.categoryId = c.categoryId\r\n"
    		+ "GROUP BY c.name;", nativeQuery = true)

    public List<Object[]> repoWhereCategory();
    
    // Thống kê các sp từ nhà cung cấp được bán ra
    @Query(value = "SELECT s.name , \r\n"
    		+ "SUM(o.quantity) as quantity ,\r\n"
    		+ "SUM(o.quantity * o.total_price) as sum,\r\n"
    		+ "AVG(o.total_price) as avg  ,\r\n"
    		+ "Min(o.total_price) as min  ,\r\n"
    		+ "max(o.total_price) as max \r\n"
    		+ "FROM orderdetails o\r\n"
    		+ "INNER JOIN products p ON o.productId = p.productId\r\n"
    		+ "INNER JOIN suppliers s ON p.supplierId = s.id\r\n"
    		+ "GROUP BY s.name;", nativeQuery = true)

    public List<Object[]> repoWhereSuppliers();
    
    // Thống kê sản phẩm theo năm // theo các năm
    @Query(value = "Select YEAR(od.orderDate) ,\r\n"
    		+ "SUM(o.quantity) as quantity ,\r\n"
    		+ "SUM(o.quantity * o.total_price) as sum,\r\n"
    		+ "AVG(o.total_price) as avg  ,\r\n"
    		+ "Min(o.total_price) as min  ,\r\n"
    		+ "max(o.total_price) as max \r\n"
    		+ "FROM orderdetails o\r\n"
    		+ "INNER JOIN orders od ON o.orderId =od.orderId\r\n"
    		+ "GROUP BY YEAR(od.orderDate);", nativeQuery = true)
    public List<Object[]> repoWhereYear();
    
    // Thống kê sản phẩm theo tháng // theo các Tháng
    @Query(value = "Select month(od.orderDate) ,\r\n"
    		+ "SUM(o.quantity) as quantity ,    \r\n"
    		+ "SUM(o.quantity * o.total_price) as sum,\r\n"
    		+ "AVG(o.total_price) as avg  ,\r\n"
    		+ "Min(o.total_price) as min  ,\r\n"
    		+ "max(o.total_price) as max\r\n"
    		+ "FROM orderdetails o\r\n"
    		+ "INNER JOIN orders od ON o.orderId =od.orderId\r\n"
    		+ "GROUP BY month(od.orderDate);", nativeQuery = true)

    public List<Object[]> repoWhereMonth();
    
    // Thống kê sản phẩm theo quý // theo các quý
    @Query(value = "Select QUARTER(od.orderDate),\r\n"
    		+ "SUM(o.quantity) as quantity , \r\n"
    		+ "SUM(o.quantity * o.total_price) as sum,\r\n"
    		+ "AVG(o.total_price) as avg, \r\n"
    		+ "Min(o.total_price) as min,\r\n"
    		+ "max(o.total_price) as max\r\n"
    		+ "FROM orderdetails o\r\n"
    		+ "INNER JOIN orders od ON o.orderId =od.orderId\r\n"
    		+ "GROUP By QUARTER(od.orderDate);", nativeQuery = true)

    public List<Object[]> repoWhereQUARTER();
    
    // Thống kê sản phẩm theo người đặt hàng
   @Query(value = "SELECT c.customerId,\r\n"
   		+ "SUM(o.quantity) as quantity,  \r\n"
   		+ "SUM(o.quantity * o.total_price) as sum,\r\n"
   		+ "AVG(o.total_price) as avg,\r\n"
   		+ "Min(o.total_price) as min, \r\n"
   		+ "max(o.total_price) as max \r\n"
   		+ "FROM orderdetails o\r\n"
   		+ "INNER JOIN orders p ON o.orderId = p.orderId\r\n"
   		+ "INNER JOIN customers c ON p.customerId = c.customerId\r\n"
   		+ "GROUP BY c.customerId;", nativeQuery = true)
   public List<Object[]> reportCustommer();

}
