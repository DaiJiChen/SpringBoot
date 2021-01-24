package com.sit.jichen;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.sit.jichen.bean.person;


@SpringBootTest
class SpringBoot02ConfigApplicationTests {
	@Autowired
	person person;

	@Test
	void contextLoads() {
		System.out.println(person);
	}

}
