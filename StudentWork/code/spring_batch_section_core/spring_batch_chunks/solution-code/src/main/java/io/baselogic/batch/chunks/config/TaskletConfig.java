package io.baselogic.batch.chunks.config;

import io.baselogic.batch.chunks.steps.NoOpTasklet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@SuppressWarnings("Duplicates")
public class TaskletConfig {


    //---------------------------------------------------------------------------//
    // Tasklets

    /**
     * Creating Tasklet manually, could not Autowire Tasklet with @Component
     * @return
     */
    @Bean
    public NoOpTasklet noOpTasklet(){
        return new NoOpTasklet();
    }


} // The End...
