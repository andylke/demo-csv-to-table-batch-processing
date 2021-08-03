package com.github.andylke.demo.customer;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.validator.ValidationException;
import org.springframework.batch.item.validator.Validator;

public class CustomerDetailsValidator implements Validator<CustomerDetails> {

  private static final Logger LOGGER = LoggerFactory.getLogger(CustomerDetailsValidator.class);

  @Override
  public void validate(CustomerDetails value) throws ValidationException {
    LOGGER.info("Validating {}", value);
    
    if (StringUtils.isBlank(value.getName())) {
      throw new ValidationException("Field [name] must not be blank.");
    }
  }

}
