package com.adinstar.pangyo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
public class PangyoApplication {

	public static void main(String[] args) {
		SpringApplication.run(PangyoApplication.class, args);
	}
}
