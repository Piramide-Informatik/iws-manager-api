package com.iws_manager.iws_manager_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class IwsManagerApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(IwsManagerApiApplication.class, args);
	}

	/** Method example for applying a test */
	public static int sum(int a, int b) {
        return a + b;
    }

}
