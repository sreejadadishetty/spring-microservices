package com.info.product_service;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.info.product_service.dto.ProductRequest;

import io.restassured.RestAssured;

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductServiceApplicationTests {

	@ServiceConnection
	static MongoDBContainer mongoDbContainer=new MongoDBContainer("mongo:7.0.5");
	
	@LocalServerPort
	private Integer port;
	
	@BeforeEach
	void setup() {
		RestAssured.baseURI="http://localhost";
		RestAssured.port=port;
	}
	
	static {
		mongoDbContainer.start();
	}
	
	
	
	
	@Test
	void shouldCreateProduct(){
		String requestBody="""
				{
    "name":"table",
    "description": "table desc",
    "price": 2000

}
				""";
		RestAssured.given()
		.contentType("application/json")
		.body(requestBody)
		.when()
		.post("/api/product")
		.then()
		.statusCode(201)
		.body("name", Matchers.equalTo("table"))
		.body("description", Matchers.equalTo("table desc"))
		.body("price", Matchers.equalTo(2000));
	}

}
