package com.github.andylke.demo.account;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.annotation.OnSkipInProcess;

public class AccountDetailsImportListener {

  private static final Logger LOGGER = LoggerFactory.getLogger(AccountDetailsImportListener.class);

  @OnSkipInProcess
  public void onSkipInProcess(AccountDetails item, Throwable t) {
    LOGGER.info("Product {} skipped due to {}", item, t.getMessage());
  }

}
