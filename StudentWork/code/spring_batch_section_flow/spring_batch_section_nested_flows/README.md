This should be in parameters section

### Starting only Parent Job at start-up:

    o.s.b.c.l.support.SimpleJobLauncher      : Job: [SimpleJob: [name=childJob]] launched with the following parameters: [{}]
    o.s.batch.core.job.SimpleStepHandler     : Step already complete or not restartable, so no action to execute: StepExecution: id=3, version=3, name=stepA, status=COMPLETED, exitStatus=COMPLETED, readCount=0, filterCount=0, writeCount=0 readSkipCount=0, writeSkipCount=0, processSkipCount=0, commitCount=1, rollbackCount=0, exitDescription=

If no job is specified, then parent and child jobs will attempt to run.
Adding the list of jobs to automatically run will ensure only parent jobs are run:

#### application.yml:
    spring.batch.job.names: parentJob
