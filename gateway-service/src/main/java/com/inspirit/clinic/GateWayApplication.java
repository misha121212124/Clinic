package com.inspirit.clinic;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;


@SpringBootApplication
@RestController
public class GateWayApplication {

	@Value("${route.patient-service.path}")
	String patient_service = "http://localhost:9001";
	@Value("${route.order-service.path}")
	String order_service = "http://localhost:9002";
	@Value("${route.auth-service.path}")
	String auth_service = "http://localhost:8999";

	public static void main(String[] args) {
		SpringApplication.run(GateWayApplication.class, args);
	}

	@Bean
	public RouteLocator routeLocator(RouteLocatorBuilder builder) {
		return builder.routes()
				.route(r -> r
						.path("/patients/**")
						.uri(patient_service)
				).route(r -> r
						.path("/orders/**")
						.uri(order_service)
				).route(r -> r
						.path("/users/**")
						.uri(auth_service)
				)
				.build();
	}
	@GetMapping
	public String gg(){
		return "well";
	}
}
