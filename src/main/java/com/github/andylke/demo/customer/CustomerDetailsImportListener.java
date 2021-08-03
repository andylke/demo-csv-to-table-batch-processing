package com.github.andylke.demo.customer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.annotation.OnSkipInProcess;

public class CustomerDetailsImportListener {

  private static final Logger LOGGER = LoggerFactory.getLogger(CustomerDetailsImportListener.class);

  @OnSkipInProcess
  public void onSkipInProcess(CustomerDetails item, Throwable t) {
    LOGGER.info("Customer {} skipped due to {}", item, t.getMessage());
  }

}
