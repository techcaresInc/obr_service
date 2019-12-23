package org.techcaresinc.techcaresinc_obr_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@SpringBootApplication
public class TechcaresincObrApplication extends SpringBootServletInitializer {

/*
	*/
/* techcares inc - a solution to your worries, deployment to external server*//*

	*/
/* comment the SpringApplicationBuilder for running test without deploy to external server*//*

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(TechcaresincObrApplication.class);
	}
*/

	@RequestMapping (value = "/")
	public String root (Model model) {
		return "index";
	}
	
	public static void main(String[] args) {
		SpringApplication.run(TechcaresincObrApplication.class, args);
	}
}