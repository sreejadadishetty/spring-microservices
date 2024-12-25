package com.info.notification_service.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
	
	
	@KafkaListener(topics= "order-placed")
	public void listen() {
		
	}

}
