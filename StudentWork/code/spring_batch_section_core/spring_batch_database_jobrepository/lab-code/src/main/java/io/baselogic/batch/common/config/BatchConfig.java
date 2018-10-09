package io.baselogic.batch.common.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;


@Configuration
@Slf4j
@SuppressWarnings({"Duplicates", "SpringJavaInjectionPointsAutowiringInspection"})
public class BatchConfig extends DefaultBatchConfigurer {


    //---------------------------------------------------------------------------//
    // Lab: Autowire transactionManager



    //---------------------------------------------------------------------------//
    // Lab: Autowire DataSource



    //---------------------------------------------------------------------------//
    // Lab: Create JobLauncher Bean




    //---------------------------------------------------------------------------//
    // Lab: Create JobRepository Bean



} // The End...
