package io.baselogic.batch.listeners.listeners;

import io.baselogic.batch.common.config.BatchDao;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;

@SuppressWarnings({"Duplicates", "SpringJavaInjectionPointsAutowiringInspection"})

//---------------------------------------------------------------------------//
// Lab: Create @BeforeRead and log step details
public class JobListener implements JobExecutionListener {
    private org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BatchDao batchDao;



    //---------------------------------------------------------------------------//
    // Lab: Create @BeforeRead and log step details
    @Override
	public void beforeJob(JobExecution jobExecution) {

        log.info("\n(beforeJob) " + batchDao.consoleLine(80, '>'));

        log.info("Beginning Job: {}, at {}",
                jobExecution.getJobInstance().getJobName(),
                jobExecution.getStartTime()
        );
        log.info("\n" + batchDao.consoleLine(80,'<') + " (beforeJob)\n");
	}


    //---------------------------------------------------------------------------//
    // Lab: Create @BeforeRead and log step details
	@Override
	public void afterJob(JobExecution jobExecution) {

        log.info("\n(afterJob) " + batchDao.consoleLine(80, '>'));

        if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("!!! JOB FINISHED! Time to verify the results");

            if(log.isDebugEnabled()) {

                log.debug(batchDao.logJobExecutions(jobExecution));

                jobExecution.getStepExecutions().forEach(stepExecution -> {
                    log.debug(batchDao.logStepExecutions(stepExecution));

                });
            }
        }
        log.info("\n" + batchDao.consoleLine(80,'<') + " (afterJob)\n");
	}

} // The End...
