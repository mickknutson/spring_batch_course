package io.baselogic.batch.split;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication
@EnableBatchProcessing
@Slf4j
@SuppressWarnings("Duplicates")
public class Application {

    public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

} // The End...
