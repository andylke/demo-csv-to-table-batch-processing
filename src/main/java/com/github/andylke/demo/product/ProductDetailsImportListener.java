package com.github.andylke.demo.product;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.annotation.OnSkipInProcess;

public class ProductDetailsImportListener {

  private static final Logger LOGGER = LoggerFactory.getLogger(ProductDetailsImportListener.class);

  @OnSkipInProcess
  public void onSkipInProcess(ProductDetails item, Throwable t) {
    LOGGER.info("Product {} skipped due to {}", item, t.getMessage());
  }

}
