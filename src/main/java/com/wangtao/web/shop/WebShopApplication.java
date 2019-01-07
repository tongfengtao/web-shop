package com.wangtao.web.shop;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication
@EnableTransactionManagement//开启事务
@MapperScan(value="com.wangtao.web.shop.*.dao")
@ComponentScan(basePackages = "com.wangtao.web.shop")
//@EnableSwagger2
@EnableAsync//支持异步
@EnableScheduling//开启spring自带定时任务
@EnableCaching//缓存
@EnableAspectJAutoProxy//轻量级AOP 切面
public class WebShopApplication {

	public static void main(String[] args)
	{
		SpringApplication.run(WebShopApplication.class, args);
	}
	/*@Configuration
	@ImportResource(locations={"verify-code.xml"})
	public class XMLConfig {
	}*/

	private CorsConfiguration buildConfig() {
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.addAllowedOrigin("*");
		corsConfiguration.addAllowedHeader("*");
		corsConfiguration.addAllowedMethod("*");
		return corsConfiguration;
	}

	/**
	 * 跨域过滤器
	 * @return
	 */
	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", buildConfig()); // 4
		return new CorsFilter(source);
	}
}

