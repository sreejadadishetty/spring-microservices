package com.info.product_service.mapper;

import com.info.product_service.dto.ProductRequest;
import com.info.product_service.model.Product;

public class ProductMapper {
	
	public static Product mapToProduct(ProductRequest prodReq) {
		return new Product(prodReq.getId(), prodReq.getName(), prodReq.getDescription(), prodReq.getPrice());
	}
	
	public static ProductRequest mapToProductRequest(Product product) {
		return new ProductRequest(product.getId(), product.getName(), product.getDescription(), product.getPrice());
	}

}
