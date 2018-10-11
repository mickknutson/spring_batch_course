package io.baselogic.batch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Arrays;

@SpringBootApplication
@EnableBatchProcessing
@EnableScheduling

@SuppressWarnings({"Duplicates", "SpringJavaInjectionPointsAutowiringInspection"})
public class Application {
    private org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

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
            log.trace(sb.toString().replaceAll("[\r\n]",""));

        };
    }

} // The End...
