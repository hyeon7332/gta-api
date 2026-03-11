package com.gta;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.gta.mapper")
public class GtaApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(GtaApiApplication.class, args);
	}

}
