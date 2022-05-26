package com.example.demo.service;

import java.util.List;
import com.example.demo.domain.entity.OrderProduct;

public interface OrderService {
	public OrderProduct selectOrder () throws Exception;
}
