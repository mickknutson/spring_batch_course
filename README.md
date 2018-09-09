# spring_batch_course


REVISIT:
https://examples.javacodegeeks.com/enterprise-java/spring/batch/spring-batch-step-step-example/



https://www.baeldung.com/spring-batch-tasklet-chunk
https://docs.spring.io/spring-batch/4.1.x/reference/html/step.html






AssertFile
DataSourceInitializer
ExecutionContextTestUtils
JobLauncherTestUtils
JobRepositoryTestUtils
JobScopeTestExecutionListener
JobScopeTestUtils
JsrTestUtils
MetaDataInstanceFactory
StepRunner
StepScopeTestExecutionListener
StepScopeTestUtils




https://github.com/spring-projects/spring-batch/tree/master/spring-batch-test/src/test/java/org/springframework/batch/test  






@Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job job1() {
        return jobBuilderFactory.get("job1")
                .incrementer(new RunIdIncrementer())
                .start(step1()).build();
    }

    private TaskletStep step1() {
        Tasklet tasklet = (contribution, context) -> {
            logger.info("This is from tasklet step with parameter ->"
                    + context.getStepContext().getJobParameters().get("message"));
            return RepeatStatus.FINISHED;
        };
        return stepBuilderFactory.get("step1").tasklet(tasklet).build();
    }





//---------------------------------------------------------------------------//
//---------------------------------------------------------------------------//

@Bean
    public Job job2() {
        return jobBuilderFactory.get("job2")
                .incrementer(new RunIdIncrementer())
                .start(step2()).build();
    }

    @Bean
    public Step step2() {
        return stepBuilderFactory.get("step2")
                .<Map<String,String>,Map<String,String>>chunk(10)
                .reader(reader(null))
                .writer(writer())
                .build();
    }

    @Bean
    @StepScope
    public FlatFileItemReader<Map<String,String>> reader(@Value("#{jobParameters['file']}") String file) {
        FlatFileItemReader<Map<String,String>> reader = new FlatFileItemReader<>();
        reader.setResource(new ClassPathResource(file));
        reader.setStrict(false);

        DefaultLineMapper<Map<String,String>> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer(":");
        tokenizer.setNames("key", "value");

        lineMapper.setFieldSetMapper((fieldSet) -> {
            Map<String,String> map = new LinkedHashMap<>();
            map.put(fieldSet.readString("key"),fieldSet.readString("value"));
            return map;
        });
        lineMapper.setLineTokenizer(tokenizer);
        reader.setLineMapper(lineMapper);

        return reader;
    }

    @Bean
    public ItemWriter<Map<String,String>> writer(){
        return (items) -> items.forEach(item -> {
            item.entrySet().forEach(entry -> {
                logger.info("key->[" + entry.getKey() + "] Value ->[" + entry.getValue() + "]");
            });
        });
    }


data.txt
--------
Monday:1
Tuesday:2
Wednesday:3
Thursday:4
Friday:5

//---------------------------------------------------------------------------//




http://www.cherryshoetech.com/2017/10/spring-batch-decision-with-spring-boot.html


https://github.com/N4rm0/spring-batch-example/blob/master/src/main/java/springbatch/flowjob/JobConfig.java
https://github.com/N4rm0/spring-batch-example



https://grokonez.com/spring-framework/spring-batch/spring-batch-programmatic-flow-decision#4_Create_Flow_Decision









    <job id="job">
		<step id="step1" next="decision">
			<tasklet ref="taskletStep_1" />
		</step>
 
		<decision id="decision" decider="decider">
			<next on="FAILED" to="step2" />
			<next on="COMPLETED" to="step3" />
		</decision>
		
		<step id="step2" next="step3" >
			<tasklet ref="taskletStep_2" />
		</step>
		
		<step id="step3" >
			<tasklet ref="taskletStep_3" />
		</step>
	</job>
 
	<beans:bean id="taskletStep_1" class="com.javasampleapproach.springbatch.step.Step1" />
	<beans:bean id="taskletStep_2" class="com.javasampleapproach.springbatch.step.Step2" />
	<beans:bean id="taskletStep_3" class="com.javasampleapproach.springbatch.step.Step3" />
	
	<beans:bean id="decider" class="com.javasampleapproach.springbatch.decision.FlowDecision"/>







http://www.cherryshoetech.com/2017/10/spring-batch-decision-with-spring-boot.html


@Component
public class CherryShoeExecutionDecider implements JobExecutionDecider {

 private static final Logger LOGGER = LoggerFactory.getLogger(CherryShoeExecutionDecider.class);

 @Value("${cherryshoe.job1.job.enabled}")
 protected Boolean jobEnabled;

 @Override
 public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
  FlowExecutionStatus status = FlowExecutionStatus.STOPPED;
  if (jobEnabled)
   status = FlowExecutionStatus.UNKNOWN;

  LOGGER.info(
    "Job enabled[" + jobEnabled + "],[ flowExecutionStatus[" + status.getName() + "]");

  return status;
 }

}





@Configuration
public class NoOpStepConfig {

 private static final Logger LOGGER = LoggerFactory.getLogger(NoOpStepConfig.class);

 /**
  * Step noOpStep
  * 
  * @param stepBuilderFactory
  * @return
  */
 @Bean
 Step noOpStep(StepBuilderFactory stepBuilderFactory, NoOpTasklet noOpTasklet) {
  LOGGER.debug("NoOp Step processing...");
  return stepBuilderFactory.get("noOpStep").tasklet(noOpTasklet).build();
 }
}

@Component
public class NoOpTasklet implements Tasklet {

 private Logger logger = LoggerFactory.getLogger(NoOpTasklet.class);

 @Override
 public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
  logger.debug("NoOp Tasklet processing...");
  return RepeatStatus.FINISHED;
 }

}




@Bean
 Job cherryShoeJob(JobBuilderFactory jobBuilderFactory, 
    CherryShoeListener jobListener,
   CherryShoeExecutionDecider jobExecutionDecider, 
   @Qualifier("noOpStep") Step noOpStep,
   @Qualifier("step1") Step step1, 
   @Qualifier("step2") Step step2, 
   @Qualifier("step3") Step step3) {

  // Since we are using command line argument values to determine if a Job
  // should run or not (vs using a scheduler to determine that),
  // we must solve this by: 1. Step 1 is a no-op step just so we can use a
  // Decider to determine if we should continue running the Job, or stop.
  // If we continue
  // running the job, then it goes through each notification type step. If
  // we stop, then we jump to the no-op step again, just so we can end.
   return jobBuilderFactory.get("cherryShoeJob")
   .incrementer(new RunIdIncrementer()).listener(jobListener)
     .flow(noOpStep).next(jobExecutionDecider)
       .on(FlowExecutionStatus.STOPPED.getName()).to(noOpStep)
     .from(noOpStep).next(jobExecutionDecider)
       .on(FlowExecutionStatus.UNKNOWN.getName()).to(step1).next(step2).next(step3)
     .end().build();
 }











@Bean
   Step ingestContributionCardStep(ItemReader<ContributionCard> reader){
         return stepBuilderFactory.get("ingestContributionCardStep")
                 .<ContributionCard, ContributionCard>chunk(1)
                 .reader(contributionCardReader(WILL_BE_INJECTED))
                 .writer(contributionCardWriter())
                 .build();
    }
    


https://docs.spring.io/spring-batch/4.1.x/reference/html/step.html#late-binding






https://examples.javacodegeeks.com/enterprise-java/spring/batch/spring-batch-step-step-example/



https://docs.spring.io/spring-batch/4.0.x/reference/html/spring-batch-integration.html


https://docs.spring.io/spring-batch/trunk/reference/html/configureStep.html
https://docs.spring.io/spring-batch/trunk/reference/html/readersAndWriters.html
https://docs.spring.io/spring-batch/trunk/reference/html/configureJob.html



https://blog.codecentric.de/en/2013/06/spring-batch-2-2-javaconfig-part-2-jobparameters-executioncontext-and-stepscope/




