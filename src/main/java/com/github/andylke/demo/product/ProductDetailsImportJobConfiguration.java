package com.github.andylke.demo.product;

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
@EnableConfigurationProperties(ProductDetailsImportProperties.class)
public class ProductDetailsImportJobConfiguration {

  public static final String JOB_NAME = "productDetailsImportJob";

  @Autowired
  private ProductDetailsImportProperties productDetailsImportProperties;

  @Autowired
  private JobBuilderFactory jobBuilderFactory;

  @Autowired
  private StepBuilderFactory stepBuilderFactory;

  @Autowired 
  private ResourcePatternResolver resourcePatternResolver;
  
  @Autowired
  private ProductDetailsRepository productDetailsRepository;

  @Bean(name = JOB_NAME)
  public Job productDetailsImportJob() {
    return jobBuilderFactory
        .get(JOB_NAME)
        .incrementer(new RunIdIncrementer())
        .start(productDetailsImportStep())
        .build();

  }

  private Step productDetailsImportStep() {
    return stepBuilderFactory
        .get(JOB_NAME)
        .<ProductDetails, ProductDetails>chunk(10)
        .reader(productDetailsReader())
        .processor(productDetailsProcessor())
        .writer(productDetailsWriter())
        .faultTolerant()
        .skipPolicy(productDetailsImportSkipPolicy())
        .skip(ValidationException.class)
        .noRollback(ValidationException.class)
        .listener(productDetailsImportListener())
        .build();
  }

  @Bean
  @StepScope
  public FlatFileItemReader<ProductDetails> productDetailsReader() {
    return new FlatFileItemReaderBuilder<ProductDetails>()
        .name("productDetailsReader")
        .resource(resourcePatternResolver.getResource(productDetailsImportProperties.getFilePath()))
        .delimited()
        .names(productDetailsImportProperties.getFieldNames())
        .linesToSkip(productDetailsImportProperties.getLineToSkip())
        .fieldSetMapper(new BeanWrapperFieldSetMapper<ProductDetails>() {
          {
            setTargetType(ProductDetails.class);
          }
        })
        .build();
  }

  @Bean
  public ValidatingItemProcessor<ProductDetails> productDetailsProcessor() {
    return new ValidatingItemProcessor<ProductDetails>(new ProductDetailsValidator());
  }

  @Bean
  public RepositoryItemWriter<ProductDetails> productDetailsWriter() {
    return new RepositoryItemWriterBuilder<ProductDetails>()
        .repository(productDetailsRepository)
        .methodName("save")
        .build();
  }

  private SkipPolicy productDetailsImportSkipPolicy() {
    return new AlwaysSkipItemSkipPolicy();
  }

  @Bean
  private ProductDetailsImportListener productDetailsImportListener() {
    return new ProductDetailsImportListener();
  }

}
