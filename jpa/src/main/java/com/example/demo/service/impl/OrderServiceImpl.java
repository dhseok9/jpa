package com.example.demo.service.impl;

import java.util.List;

import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.service.OrderService;
import com.example.demo.domain.entity.OrderProduct;
import com.example.demo.domain.repository.OrderProductRepository;

@Slf4j
@Service("orderService")
public class OrderServiceImpl implements OrderService {
//    private static final Logger logger = LoggerFactory.getLogger(SampleServiceImpl.class);

	@Autowired
	OrderProductRepository orderProductRepository;
	
	@Override
	@Transactional
	public OrderProduct selectOrder() throws Exception {
		log.info("OrderServiceImpl.selectOrder Parameter>>>>{}");
		int orderId = 2;
		int productId = 2;
		
//		OrderProduct orderProduct = new OrderProduct();
//		orderProduct.setOrderId(2);
//		orderProduct.setProductId(2);
//		orderProduct.setAmount(200);
		return orderProductRepository.findByOrderIdAndProductId(orderId, productId);
//		return orderProductRepository.selectOrder(orderId, productId);
//		return orderProductRepository.insertOrder(orderProduct);
	}
	
}
