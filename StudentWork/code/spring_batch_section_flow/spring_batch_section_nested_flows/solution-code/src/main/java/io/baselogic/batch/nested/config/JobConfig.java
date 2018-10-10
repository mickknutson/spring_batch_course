package io.baselogic.batch.nested.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SuppressWarnings({"Duplicates", "SpringJavaInjectionPointsAutowiringInspection"})
public class JobConfig {


    //---------------------------------------------------------------------------//
    // Jobs

    @Bean("job")
    public Job parentJob(JobBuilderFactory jobBuilderFactory,
                         Step childJobStep,
                         Step step1,
                         Step step2,
                         Step step3
    ) {
        return jobBuilderFactory.get("parentJob")
                .start(step1)
                .next(childJobStep)
                .next(step2)
                .next(step3)
                .build();
    }



    @Bean
    public Job childJob(JobBuilderFactory jobBuilderFactory,
                        Step stepA,
                        Step stepB,
                        Step stepC
    ) {
        return jobBuilderFactory.get("childJob")
                .start(stepA)
                .next(stepB)
                .next(stepC)
                .build();
    }


} // The End...
