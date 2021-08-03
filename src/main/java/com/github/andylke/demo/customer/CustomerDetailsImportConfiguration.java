package com.github.andylke.demo.customer;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.skip.AlwaysSkipItemSkipPolicy;
import org.springframework.batch.core.step.skip.SkipPolicy;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.validator.ValidatingItemProcessor;
import org.springframework.batch.item.validator.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.ResourcePatternResolver;

@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(CustomerDetailsImportProperties.class)
class CustomerDetailsImportConfiguration {

  @Autowired
  private CustomerDetailsImportProperties customerDetailsImportProperties;

  @Autowired
  private JobBuilderFactory jobBuilderFactory;

  @Autowired
  private StepBuilderFactory stepBuilderFactory;

  @Autowired 
  private ResourcePatternResolver resourcePatternResolver;
  
  @Autowired
  private CustomerDetailsRepository customerDetailsRepository;

  @Bean
  public Job customerDetailsImportJob() {
    return jobBuilderFactory
        .get(customerDetailsImportProperties.getName() + "-job")
        .incrementer(new RunIdIncrementer())
        .start(customerDetailsImportStep())
        .build();

  }

  private Step customerDetailsImportStep() {
    return stepBuilderFactory
        .get(customerDetailsImportProperties.getName() + "-step")
        .<CustomerDetails, CustomerDetails>chunk(10)
        .reader(customerDetailsReader())
        .processor(customerDetailsProcessor())
        .writer(customerDetailsWriter())
        .faultTolerant()
        .skipPolicy(customerDetailsImportSkipPolicy())
        .skip(ValidationException.class)
        .noRollback(ValidationException.class)
        .listener(customerDetailsImportListener())
        .build();
  }

  @Bean
  @StepScope
  public FlatFileItemReader<CustomerDetails> customerDetailsReader() {
    return new FlatFileItemReaderBuilder<CustomerDetails>()
        .name(customerDetailsImportProperties.getName() + "-reader")
        .resource(resourcePatternResolver.getResource(customerDetailsImportProperties.getFilePath()))
        .delimited()
        .names(customerDetailsImportProperties.getFieldNames())
        .linesToSkip(customerDetailsImportProperties.getLineToSkip())
        .fieldSetMapper(new BeanWrapperFieldSetMapper<CustomerDetails>() {
          {
            setTargetType(CustomerDetails.class);
          }
        })
        .build();
  }

  @Bean
  public ValidatingItemProcessor<CustomerDetails> customerDetailsProcessor() {
    return new ValidatingItemProcessor<CustomerDetails>(new CustomerDetailsValidator());
  }

  @Bean
  public RepositoryItemWriter<CustomerDetails> customerDetailsWriter() {
    return new RepositoryItemWriterBuilder<CustomerDetails>()
        .repository(customerDetailsRepository)
        .methodName("save")
        .build();
  }

  private SkipPolicy customerDetailsImportSkipPolicy() {
    return new AlwaysSkipItemSkipPolicy();
  }

  @Bean
  private CustomerDetailsImportListener customerDetailsImportListener() {
    return new CustomerDetailsImportListener();
  }

}
