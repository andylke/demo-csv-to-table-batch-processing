package com.github.andylke.demo;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.github.andylke.demo.customer.CustomerDetailsImportJobConfiguration;
import com.github.andylke.demo.product.ProductDetailsImportJobConfiguration;

@Configuration(proxyBeanMethods = false)
@Import({ CustomerDetailsImportJobConfiguration.class, ProductDetailsImportJobConfiguration.class })
public class DemoImportJobConfiguration {

  public static final String JOB_NAME = "demoImportJob";

  @Autowired
  private JobBuilderFactory jobBuilderFactory;

  @Autowired
  private StepBuilderFactory stepBuilderFactory;

  @Autowired
  @Qualifier(CustomerDetailsImportJobConfiguration.JOB_NAME)
  private Job customerDetailsImportJob;

  @Autowired
  @Qualifier(ProductDetailsImportJobConfiguration.JOB_NAME)
  private Job productDetailsImportJob;

  @Bean
  public Job demoImportJob() {
    return jobBuilderFactory
        .get(JOB_NAME)
        .incrementer(new RunIdIncrementer())
        .start(customerDetailsImport())
        .next(productDetailsImport())
        .build();
  }

  private Step customerDetailsImport() {
    return stepBuilderFactory
        .get(CustomerDetailsImportJobConfiguration.JOB_NAME)
        .job(customerDetailsImportJob)
        .build();
  }

  private Step productDetailsImport() {
    return stepBuilderFactory
        .get(ProductDetailsImportJobConfiguration.JOB_NAME)
        .job(productDetailsImportJob)
        .build();
  }
}
