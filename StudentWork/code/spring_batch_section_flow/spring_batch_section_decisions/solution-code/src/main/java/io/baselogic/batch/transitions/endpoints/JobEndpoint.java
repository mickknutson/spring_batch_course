package io.baselogic.batch.transitions.endpoints;


import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;

@RestController
@Slf4j
@SuppressWarnings({"Duplicates", "SpringJavaInjectionPointsAutowiringInspection"})
public class JobEndpoint {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job job;

    private AtomicBoolean enabled = new AtomicBoolean(true);

    //---------------------------------------------------------------------------//

    @GetMapping("/launch")
    public String launchJob(@RequestParam(value = "launchJob", required = false, defaultValue="true") boolean launchJob)
            throws JobExecutionException {
        enabled.set(launchJob);
        return startSimpleJob();
    }

    //---------------------------------------------------------------------------//
    public String startSimpleJob() throws JobExecutionException {

        ExitStatus exitStatus = ExitStatus.UNKNOWN;
        String result = exitStatus.getExitCode();

        if (enabled.get()) {

            log.info("launching jobExecution...");

            JobExecution jobExecution = jobLauncher
                    .run(job,
                            new JobParametersBuilder()
                                    .addLong("timestamp", System.currentTimeMillis())
                                    .addDate("launchDate", new Date())
                                    .toJobParameters());

            result = getJobExecutionDetails(jobExecution);

            log.info("...jobExecution completed");
            log.info(result);

        }
        return result;
    }

    public static String getJobExecutionDetails(JobExecution jobExecution){
        StringBuilder sb = new StringBuilder();

        sb.append("\n***Job Execution Details ***\n");
        sb.append("\njobExecution exit status: ").append(jobExecution.getExitStatus());
        sb.append("\nsteps executed:: ").append(jobExecution.getStepExecutions().size());

        return sb.toString();
    }
}
