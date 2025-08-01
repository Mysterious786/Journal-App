package com.example.Springbootproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableTransactionManagement
@EnableScheduling
public class JournalApplication {

	private static final Logger log = LoggerFactory.getLogger(JournalApplication.class);

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(JournalApplication.class, args);
		ConfigurableEnvironment env = context.getEnvironment();
		if (env.getActiveProfiles().length > 0) {
			log.info("Active profile: {}", env.getActiveProfiles()[0]);
		} else {
			log.info("No active profile set.");
		}
	}

	@Bean
	public PlatformTransactionManager transactionManager(MongoDatabaseFactory dbFactory) {
		return new MongoTransactionManager(dbFactory);
	}
	@Bean
	public RestTemplate restTemplate()
	{
		return new RestTemplate();
	}
}
