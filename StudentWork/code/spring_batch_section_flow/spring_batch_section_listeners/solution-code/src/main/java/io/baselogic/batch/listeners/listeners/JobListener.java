package io.baselogic.batch.listeners.listeners;

import io.baselogic.batch.common.config.BatchDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@Slf4j
@SuppressWarnings({"Duplicates", "SpringJavaInjectionPointsAutowiringInspection"})
public class JobListener implements JobExecutionListener {

    @Autowired
    private BatchDao batchDao;

    @Override
	public void beforeJob(JobExecution jobExecution) {

        log.info("\n(beforeJob) " + batchDao.consoleLine(80, '>'));

        log.info("Beginning Job: {}, at {}",
                jobExecution.getJobInstance().getJobName(),
                jobExecution.getStartTime()
        );
        log.info("\n" + batchDao.consoleLine(80,'<') + " (beforeJob)\n");
	}

	@Override
	public void afterJob(JobExecution jobExecution) {

        log.info("\n(afterJob) " + batchDao.consoleLine(80, '>'));

        if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("!!! JOB FINISHED! Time to verify the results");

            if(log.isDebugEnabled()) {

                log.info(batchDao.logJobExecutions(jobExecution));

                jobExecution.getStepExecutions().forEach((stepExecution) -> {
                    log.debug(batchDao.logStepExecutions(stepExecution));

                });
            }
        }
        log.info("\n" + batchDao.consoleLine(80,'<') + " (afterJob)\n");
	}

} // The End...
