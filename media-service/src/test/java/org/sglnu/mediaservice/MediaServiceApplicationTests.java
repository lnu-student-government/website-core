package org.sglnu.mediaservice;

import org.sglnu.mediaservice.controller.FileController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class MediaServiceApplicationTests {

	@Autowired
	private FileController fileController;

	@Test
	void contextLoads() {
		assertNotNull(fileController);
	}
}
