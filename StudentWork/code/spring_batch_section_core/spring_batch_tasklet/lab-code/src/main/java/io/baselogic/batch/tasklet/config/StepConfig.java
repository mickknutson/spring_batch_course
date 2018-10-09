package io.baselogic.batch.tasklet.config;

import io.baselogic.batch.tasklet.steps.EchoTasklet;
import io.baselogic.batch.tasklet.steps.NoOpTasklet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@Slf4j
@SuppressWarnings({"Duplicates", "SpringJavaInjectionPointsAutowiringInspection"})
public class StepConfig {


    //---------------------------------------------------------------------------//
    // Lab: Create Step's and Autowire Tasklets




    //---------------------------------------------------------------------------//
    // Lab: Create Tasklet with lambda expression




    //---------------------------------------------------------------------------//
    // Lab: Create NoOpTasklet



    //---------------------------------------------------------------------------//
    // Lab: Create EchoTasklet with jobParameters









} // The End...
