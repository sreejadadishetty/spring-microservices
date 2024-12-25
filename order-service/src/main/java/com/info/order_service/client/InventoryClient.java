package com.info.order_service.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;

@FeignClient(value="inventory", url="${inventory.url}")
@CircuitBreaker(name="inventory",fallbackMethod = "fallbackMethod")
@Retry(name = "inventory")
@Slf4j
@Component
public interface InventoryClient {
	
	Logger log=LoggerFactory.getLogger(InventoryClient.class);
	
	
	
	@RequestMapping(method=RequestMethod.GET, value="/api/inventory")
	boolean isInStock(@RequestParam String skuCode, @RequestParam Integer quantity);
    
	
	default boolean fallbackMethod(String code, Integer quantity, Throwable throwable) {
        System.out.println("Cannot get inventory for skucode {}, failure reason: {}"+ code+ throwable.getMessage());
        return false;
    }
}
