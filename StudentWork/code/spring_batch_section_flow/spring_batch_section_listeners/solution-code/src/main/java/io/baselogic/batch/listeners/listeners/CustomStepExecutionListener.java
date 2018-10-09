package io.baselogic.batch.listeners.listeners;

import io.baselogic.batch.common.config.BatchDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@SuppressWarnings({"Duplicates", "SpringJavaInjectionPointsAutowiringInspection"})

//---------------------------------------------------------------------------//
// Lab: Create @BeforeRead and log step details

public class CustomStepExecutionListener implements StepExecutionListener {

    @Autowired
    private BatchDao batchDao;


    //---------------------------------------------------------------------------//
    // Lab: Create @BeforeRead and log step details
    @Override
    public void beforeStep(StepExecution stepExecution){

        log.info("\n(beforeStep) " + batchDao.consoleLine(80, '>'));

        log.info("Starting Step: {}, at {}",
                stepExecution.getStepName(),
                stepExecution.getStartTime()
        );
        log.info("\n" + batchDao.consoleLine(80,'<') + " (beforeStep)\n");
    }


    //---------------------------------------------------------------------------//
    // Lab: Create @BeforeRead and log step details
    @Override
    public ExitStatus afterStep(StepExecution stepExecution){
        log.info("\n(afterStep) " + batchDao.consoleLine(80, '>'));

        if(stepExecution.getStatus() == BatchStatus.COMPLETED) {
//        if(stepExecution.getExitStatus() == ExitStatus.COMPLETED) {
            log.info("!!! STEP COMPLETED!");
            log.info("ExitStatus is: {}", stepExecution.getExitStatus());
            log.info("now we will return the same ExitStatus, or change it.");

            if(log.isDebugEnabled()) {
                log.info(batchDao.logStepExecutions(stepExecution));
            }
        }
        log.info("\n" + batchDao.consoleLine(80, '<') + " (afterStep)\n");

        return stepExecution.getExitStatus();
    }

} // The End...
