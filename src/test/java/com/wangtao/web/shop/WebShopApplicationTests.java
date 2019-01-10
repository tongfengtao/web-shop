package com.wangtao.web.shop;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.RequestMapping;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WebShopApplicationTests {

	@Test
	@RequestMapping(value = "user/audit")
	public void contextLoads() {
		System.out.print(123123);
	}

}

