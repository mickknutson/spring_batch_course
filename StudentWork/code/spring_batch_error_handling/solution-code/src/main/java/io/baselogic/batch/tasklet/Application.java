package io.baselogic.batch.tasklet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"io.baselogic.batch.introduction.steps"})
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

} // The End...
