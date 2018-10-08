package io.baselogic.batch.restart.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
@SuppressWarnings({"Duplicates", "SpringJavaInjectionPointsAutowiringInspection"})
public class JobConfig {


    //---------------------------------------------------------------------------//
    // Jobs


    @Bean("job")
    public Job job(JobBuilderFactory jobBuilderFactory,
                   Step stepA,
                   Step stepB
    ) {
        return jobBuilderFactory.get("notRestartableJob")
//                .preventRestart()
                .start(stepA)
                .next(stepB)
                .build();
    }


    @Bean("startLimitJob")
    public Job startLimitJob(JobBuilderFactory jobBuilderFactory,
                   Step stepA,
                   Step stepB
    ) {
        return jobBuilderFactory.get("startLimitJob")
                .start(stepA)
                .next(stepB)
                .build();
    }

} // The End...
