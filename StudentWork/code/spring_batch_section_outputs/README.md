# *Spring Batch Master Class*

##  *Section: Outputs*


**Learn how to design and develop robust batch applications with the power of the Spring Batch framework with JavaConfig**

---

### *Modules in this section:*
 - **[Chunks](https://github.com/mickknutson/spring_batch_course/tree/master/StudentWork/code/spring_batch_section_core/spring_batch_chunks)**

---

### *NOTES*

TBD...

---

## TO REVISIT

Flat File Output:

    @Bean
    public FlatFileItemWriter flatFileItemWriter() {
        return  new FlatFileItemWriterBuilder<Movie>()
                .name("itemWriter")
                .resource(new FileSystemResource("target/batch/output.json"))
                .lineAggregator(new PassThroughLineAggregator<>())
                .build();
    }


XML Output:

    @Bean
    @StepScope
    public StaxEventItemWriter<Movie> itemWriter(Marshaller marshaller,
                                                 @Value("#{stepExecutionContext[outputFileName]}") String filename)
    throws MalformedURLException {
        StaxEventItemWriter<Movie> itemWriter = new StaxEventItemWriter<>();
        itemWriter.setMarshaller(marshaller);
        itemWriter.setRootTagName("transactionRecord");
        itemWriter.setResource(new FileSystemResource("target/batch/output/" + filename));
        return itemWriter;
    }




## The End...
