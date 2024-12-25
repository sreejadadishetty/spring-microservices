package com.info.product_service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.info.product_service.dto.ProductRequest;
import com.info.product_service.mapper.ProductMapper;
import com.info.product_service.model.Product;
import com.info.product_service.repository.ProductRepository;

@Service
public class ProductService {
	@Autowired
	ProductRepository productRepo;
	
	
	public void createProduct(ProductRequest prodRequest) {
		Product product=ProductMapper.mapToProduct(prodRequest);
		productRepo.save(product);
		System.out.println("product is saved"+product.getId());
		
	}


	public List<ProductRequest> getAllProducts() {
		List<Product> products=productRepo.findAll();
		return products.stream().map(p->ProductMapper.mapToProductRequest(p)).toList();
	}

}
