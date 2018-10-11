package io.baselogic.batch.schedule;

import org.springframework.batch.core.launch.JobOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledLauncher {

	@Autowired
	public JobOperator jobOperator;

	@Scheduled(fixedDelay = 5_000l)
	public void runJob() throws Exception {
		this.jobOperator.startNextInstance("scheduledJob");
	}

} // The End...
