package com.swinginwind.czss;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages="com.swinginwind")
@MapperScan("com.swinginwind.czss.mapper")
public class CzssServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CzssServiceApplication.class, args);
	}

}
