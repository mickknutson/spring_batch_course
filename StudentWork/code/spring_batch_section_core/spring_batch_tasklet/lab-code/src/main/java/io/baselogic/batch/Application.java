package io.baselogic.batch;

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
@SuppressWarnings({"Duplicates", "SpringJavaInjectionPointsAutowiringInspection"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


    // FIXME: Need to make the others like this:
    private static final String LINE = "+" + new String(new char[78]).replace('\0', '-') + "+";

    //@Profile("trace")
    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {

            StringBuilder sb = new StringBuilder();

            sb.append("\n\n").append(LINE).append("\n");
            sb.append("Let's inspect the beans provided by Spring Boot:");
            sb.append("\n").append(LINE).append("\n\n");

            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                sb.append(beanName).append("\n");
            }

            sb.append("\n").append(LINE).append("\n");
            sb.append(LINE).append("\n");
            sb.append(LINE).append("\n\n");

            log.debug(sb.toString());

        };
    }
} // The End...
