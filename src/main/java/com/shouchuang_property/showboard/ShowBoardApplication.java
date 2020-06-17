package com.shouchuang_property.showboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages={"com.shouchuang_property.showboard"})
public class ShowBoardApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShowBoardApplication.class, args);
	}

}
