package com.capgemini.vehiclebreakdown.securityconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.capgemini.vehiclebreakdown.service.UserDetailsUserService;

import lombok.RequiredArgsConstructor;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

	@Autowired
	private UserDetailsUserService userService;

	
	@Bean
	@Primary
	public UserDetailsService userDetailsService(){
	    System.out.println("[+] [ApplicationConfig] userDetailsService");
	    return userService::findUserByUsername;
	}

}