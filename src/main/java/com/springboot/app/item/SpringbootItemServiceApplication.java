package com.springboot.app.item;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;


@EnableEurekaClient
@EnableFeignClients
@SpringBootApplication
@EntityScan({"com.springboot.app.commonslib.model.entity"})
public class SpringbootItemServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootItemServiceApplication.class, args);
	}

}
