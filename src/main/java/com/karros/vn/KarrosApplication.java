package com.karros.vn;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.karros")
@MapperScan(basePackages = "com.karros.vn.mapper")
public class KarrosApplication {
	public static void main(String[] args) {
		SpringApplication.run(KarrosApplication.class, args);
	}

}
