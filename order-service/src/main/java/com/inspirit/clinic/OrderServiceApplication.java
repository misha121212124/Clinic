package com.inspirit.clinic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableEurekaClient
@EnableFeignClients
@SpringBootApplication
public class OrderServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderServiceApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/*").allowedOrigins("http://localhost:9000").allowedOrigins("http://localhost:8761")
						.allowedOrigins("http://localhost:9001").allowedOrigins("http://localhost:9002")
						.allowedHeaders("http://localhost:9000").allowedHeaders("http://localhost:8761")
						.allowedHeaders("http://localhost:9001").allowedHeaders("http://localhost:9002");
//				registry.addMapping("/*");
				registry.addMapping("/*/*").allowedOrigins("http://localhost:9000").allowedOrigins("http://localhost:8761")
						.allowedOrigins("http://localhost:9001").allowedOrigins("http://localhost:9002")
						.allowedHeaders("http://localhost:9000").allowedHeaders("http://localhost:8761")
						.allowedHeaders("http://localhost:9001").allowedHeaders("http://localhost:9002");

			}
		};
	}


}
