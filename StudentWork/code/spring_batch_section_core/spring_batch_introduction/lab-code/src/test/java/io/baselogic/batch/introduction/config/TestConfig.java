package io.baselogic.batch.introduction.config;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//---------------------------------------------------------------------------//
// Lab: add @EnableBatchProcessing


@Configuration
public class TestConfig {

    //-------------------------------------------------------------------//
    // Lab: Review JobLauncherTestUtils
    @Bean
    public JobLauncherTestUtils jobLauncherTestUtils(){
        return new JobLauncherTestUtils();
    }

} // The End...
