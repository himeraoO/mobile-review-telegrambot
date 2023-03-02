package com.github.himeraoO.mrtb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class MobileReviewTelegramBotApplication {

	public static void main(String[] args) {
		SpringApplication.run(MobileReviewTelegramBotApplication.class, args);
	}

}
