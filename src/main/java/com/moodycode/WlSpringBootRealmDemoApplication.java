package com.moodycode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.web.WebApplicationInitializer;


/***
 *  MOODYCODE.COM DEMO
 *  SETUP FOR WEBLOGIC DEPLOYED APPLICATION, WITH AN OVERRIDE FOR WebApplicationInitializer and extending
 *  	SpringBootServletInitializer
 */

@SpringBootApplication
public class WlSpringBootRealmDemoApplication  extends SpringBootServletInitializer implements
		WebApplicationInitializer {

	public static void main(String[] args) {
		SpringApplication.run(WlSpringBootRealmDemoApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(WlSpringBootRealmDemoApplication.class);
	}


}
