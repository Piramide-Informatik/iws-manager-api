package com.iws_manager.iws_manager_api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class IwsManagerApiApplicationTests {

	@Test
	void contextLoads() {
	}

	/** Example of test */
	@Test
    public void testSum() {
        int result = IwsManagerApiApplication.sum(1, 2);
        assertEquals(3, result);
    }
}
