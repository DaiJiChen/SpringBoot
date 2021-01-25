package com.sit.jichen;

import com.sit.jichen.bean.PropertySource_Annotation_Demo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.sit.jichen.bean.person;


@SpringBootTest
class SpringBoot02ConfigApplicationTests {
	@Autowired
	person person;

	@Autowired
	PropertySource_Annotation_Demo demo;

	@Test
	void contextLoads() {
		System.out.println(person);
		System.out.println(demo);
	}

}
