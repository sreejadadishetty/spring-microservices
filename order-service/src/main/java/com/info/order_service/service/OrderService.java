package com.info.order_service.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.info.order_service.client.InventoryClient;
import com.info.order_service.dto.OrderRequest;
import com.info.order_service.event.OrderPlacedEvent;
import com.info.order_service.model.Order;
import com.info.order_service.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
	
	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	InventoryClient inventoryClient;
	
	@Autowired
	KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;
	
	


public void placeOrder(OrderRequest orderRequest) {
		var isProductInStock=inventoryClient.isInStock(orderRequest.skuCode(), orderRequest.quantity());
		
		if(isProductInStock) {
			Order order=new Order();
			order.setOrderNumber(UUID.randomUUID().toString());
			order.setPrice(orderRequest.price());
			order.setQuantity(orderRequest.quantity());
			order.setSkuCode(orderRequest.skuCode());
			orderRepository.save(order);
			
			OrderPlacedEvent orderPlacedEvent=new OrderPlacedEvent(order.getOrderNumber(), orderRequest.userDetails().email());
			System.out.println("start---- sending order placed event to kafka topic"+orderPlacedEvent);
			kafkaTemplate.send("order-placed",orderPlacedEvent);
			System.out.println("End---- sending order placed event to kafka topic"+orderPlacedEvent);
			
		}else {
			throw new RuntimeException("Product with SkuCode"+ orderRequest.skuCode()+"is not in stock");
		}
		
	}
	

}
