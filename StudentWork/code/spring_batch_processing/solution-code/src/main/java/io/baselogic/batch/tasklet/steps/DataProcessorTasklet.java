package io.baselogic.batch.tasklet.steps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class DataProcessorTasklet implements Tasklet {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private Resource inputResource;

	public RepeatStatus execute(final StepContribution contribution,
                                final ChunkContext chunkContext)
            throws Exception {

        logger.info(">>>____________________________________________________");
        logger.info("DataProcessorTasklet.execute");


        // FIXME: wrap with an autResourceClosure block

		BufferedReader bufferReader = new BufferedReader(new InputStreamReader(inputResource.getInputStream()));

		String line = null;

		while ((line = bufferReader.readLine()) != null) {
            logger.info("_______________________________________________________");
			logger.info("Processing product : {}", line);
		}
		bufferReader.close();

        logger.info("____________________________________________________<<<");
		return RepeatStatus.FINISHED;
	}
	
	public void setInputResource(Resource inputResource) {
		this.inputResource = inputResource;
	}
	
} // The End...
