package io.baselogic.batch.decisions.config;

import io.baselogic.batch.decisions.decisions.FlowDecision;
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

    @Bean
    public Job job(JobBuilderFactory jobBuilderFactory,
                   FlowDecision flowDecision,
                   Step startingStep,
                   Step evenStep,
                   Step oddStep,
                   Step endStep) {

        return jobBuilderFactory.get("decisionJob")

                // Step #1:
                .start(startingStep)
                .next(flowDecision)

                // Step #2:
                .from(flowDecision).on("ODD").to(oddStep)
                .from(flowDecision).on("EVEN").to(evenStep)

                // Does not count as step:
                .from(oddStep).on("*").to(flowDecision)

                // Step #3:
                .from(flowDecision).on("ODD").to(oddStep)
                .from(flowDecision).on("EVEN").to(evenStep)

                // Step #4
                .next(endStep)

                .end()
                .build();
    }

    //---------------------------------------------------------------------------//
    // Decisions

    @Bean
    public FlowDecision flowDecision(){
        return new FlowDecision();
    }


} // The End...
