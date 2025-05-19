package com.iws_manager.iws_manager_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication //(exclude = DataSourceAutoConfiguration.class)
@EnableJpaAuditing
@EntityScan(basePackages = "com.iws_manager.iws_manager_api.models")
public class IwsManagerApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(IwsManagerApiApplication.class, args);
	}

}
