package com.example.demo.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.domain.entity.OrderProduct;
import com.example.demo.domain.entity.OrderProductPK;

public interface OrderProductRepository extends JpaRepository<OrderProduct, OrderProductPK>{
	
	public OrderProduct findByOrderIdAndProductId(int orderId, int productId);
	
	public List<OrderProduct> findByOrderId(int orderId);
	
//	@Query("select p from order_product p where p.order_id = ?1 and p.product_id = ?2")
//	public OrderProduct findByOrderIdAndProductId(int orderId,  int productId);
	
//	@Query("select p from order_product p where p.order_id = :orderId and p.product_id = :productId")
//	public OrderProduct selectOrder3(@Param(value = "orderId") int orderId, @Param("productId") int productId);
	
    @Query(value = "SELECT * FROM order_product p WHERE p.order_id = :orderId and p.product_id = :productId", nativeQuery = true)
    public OrderProduct selectOrder(@Param("orderId") int orderId, @Param("productId") int productId);
    
    @Modifying
    @Query(value = "INSERT INTO order_product(order_id, product_id, amount) values (:#{#orderProduct.orderId}, :#{#orderProduct.productId}, :#{#orderProduct.amount})", nativeQuery = true)
    public int insertOrder(@Param("orderProduct") OrderProduct orderProduct);
    
    @Modifying
    @Query(value = "UPDATE order_product SET amount = :amount WHERE order_id = :orderId and product_id = :productId", nativeQuery = true)
    public int updatePrice(@Param("orderId") int orderId, @Param("productId") int productId, @Param("amount") int amount);
    
}