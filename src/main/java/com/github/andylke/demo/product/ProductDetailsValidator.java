package com.github.andylke.demo.product;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.validator.ValidationException;
import org.springframework.batch.item.validator.Validator;

public class ProductDetailsValidator implements Validator<ProductDetails> {

  private static final Logger LOGGER = LoggerFactory.getLogger(ProductDetailsValidator.class);

  @Override
  public void validate(ProductDetails value) throws ValidationException {
    LOGGER.info("Validating {}", value);
    
    if (StringUtils.isBlank(value.getTypeCode())) {
      throw new ValidationException("Field [typeCode] must not be blank.");
    }

    if (StringUtils.isBlank(value.getCurrencyCode())) {
      throw new ValidationException("Field [currencyCode] must not be blank.");
    }
  }

}
