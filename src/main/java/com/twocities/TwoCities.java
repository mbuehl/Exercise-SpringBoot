package com.twocities;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Michael Buehl
 */


@EnableAutoConfiguration
@SpringBootApplication
public class TwoCities {

	public static void main(String[] args) {
		SpringApplication.run(TwoCities.class, args);
	}
}
