package org.techcaresinc.techcaresinc_obr_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class TechcaresincObrApplication extends SpringBootServletInitializer {

	/* techcares inc - a solution to your worries, deployment to external server*/
	/* comment the SpringApplicationBuilder for running test without deploy to external server*/
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(TechcaresincObrApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(TechcaresincObrApplication.class, args);
	}
}
