package com.github.andylke.demo.account;

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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.ResourcePatternResolver;

@Configuration(proxyBeanMethods = false)
public class AccountDetailsImportJobConfiguration {

  public static final String JOB_NAME = "accountDetailsImportJob";

  @Autowired
  private JobBuilderFactory jobBuilderFactory;

  @Autowired
  private StepBuilderFactory stepBuilderFactory;

  @Autowired 
  private ResourcePatternResolver resourcePatternResolver;
  
  @Autowired
  private AccountDetailsRepository accountDetailsRepository;

  @Autowired
  private AccountDetailsValidator accountDetailsValidator;

  @Bean(name = JOB_NAME)
  public Job accountDetailsImportJob() {
    return jobBuilderFactory
        .get(JOB_NAME)
        .incrementer(new RunIdIncrementer())
        .start(accountDetailsImportStep())
        .build();

  }

  private Step accountDetailsImportStep() {
    return stepBuilderFactory
        .get(JOB_NAME)
        .<AccountDetails, AccountDetails>chunk(10)
        .reader(accountDetailsReader())
        .processor(accountDetailsProcessor())
        .writer(accountDetailsWriter())
        .faultTolerant()
        .skipPolicy(accountDetailsImportSkipPolicy())
        .skip(ValidationException.class)
        .noRollback(ValidationException.class)
        .listener(accountDetailsImportListener())
        .build();
  }

  @Bean
  @StepScope
  public FlatFileItemReader<AccountDetails> accountDetailsReader() {
    return new FlatFileItemReaderBuilder<AccountDetails>()
        .name("accountDetailsReader")
        .resource(resourcePatternResolver.getResource("account-details.csv"))
        .delimited()
        .names(new String[] { "accountNumber", "customerNumber", "productTypeCode", "currencyCode", "balance" })
        .linesToSkip(1)
        .fieldSetMapper(new BeanWrapperFieldSetMapper<AccountDetails>() {
          {
            setTargetType(AccountDetails.class);
          }
        })
        .build();
  }

  @Bean
  public ValidatingItemProcessor<AccountDetails> accountDetailsProcessor() {
    return new ValidatingItemProcessor<AccountDetails>(accountDetailsValidator);
  }

  @Bean
  public RepositoryItemWriter<AccountDetails> accountDetailsWriter() {
    return new RepositoryItemWriterBuilder<AccountDetails>()
        .repository(accountDetailsRepository)
        .methodName("save")
        .build();
  }

  private SkipPolicy accountDetailsImportSkipPolicy() {
    return new AlwaysSkipItemSkipPolicy();
  }

  @Bean
  private AccountDetailsImportListener accountDetailsImportListener() {
    return new AccountDetailsImportListener();
  }

}
