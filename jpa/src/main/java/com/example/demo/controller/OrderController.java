package com.example.demo.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.service.OrderService;
import com.example.demo.domain.entity.OrderProduct;
import com.google.gson.Gson;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class OrderController {
	
	@Resource(name="orderService")
	private OrderService orderService;
	
	@RequestMapping(value="/selectOrder", method={RequestMethod.GET})
	@ResponseBody
	public String selectOrder() throws Exception {
		String result = "";
		Gson gson = new Gson();
		
		OrderProduct orderProduct = orderService.selectOrder();
//		int cnt = orderService.selectOrder();
//		log.info(cnt);
		if (orderProduct != null) {
			String jsonStr = gson.toJson(orderProduct);
			log.info(jsonStr);
			result = gson.toJson(orderProduct);
		}
		log.info(result);
		return result;
	}
}