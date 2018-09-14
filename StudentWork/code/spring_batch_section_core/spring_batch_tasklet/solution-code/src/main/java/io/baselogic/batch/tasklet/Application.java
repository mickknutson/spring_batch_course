package io.baselogic.batch.tasklet;

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

    //@Profile("trace")
    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {

            StringBuilder sb = new StringBuilder();

            sb.append("\n\n----------------------------------------");
            sb.append("Let's inspect the beans provided by Spring Boot:\n");
            sb.append("--------------------------------------------");

            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                sb.append(beanName);
            }

            sb.append("----------------------------------------\n\n");
            log.debug(sb.toString().replaceAll("[\r\n]",""));

        };
    }

} // The End...
