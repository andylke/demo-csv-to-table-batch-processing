package com.github.andylke.demo.account;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.validator.ValidationException;
import org.springframework.batch.item.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.andylke.demo.customer.CustomerDetails;
import com.github.andylke.demo.customer.CustomerDetailsRepository;
import com.github.andylke.demo.product.ProductDetails;
import com.github.andylke.demo.product.ProductDetailsRepository;

import net.objectlab.kit.util.BigDecimalUtil;

@Component
public class AccountDetailsValidator implements Validator<AccountDetails> {

  private static final Logger LOGGER = LoggerFactory.getLogger(AccountDetailsValidator.class);

  @Autowired
  private CustomerDetailsRepository customerDetailsRepository;

  @Autowired
  private ProductDetailsRepository productDetailsRepository;

  @Override
  public void validate(AccountDetails value) throws ValidationException {
    LOGGER.info("Validating {}", value);
    
    validateAccountDetails(value);
    validateAccountOwner(value);
    validateAccountProduct(value);
  }

  private void validateAccountDetails(AccountDetails value) {
    if (BigDecimalUtil.isNullOrZero(value.getAccountNumber())) {
      throw new ValidationException("Field [accountNumber] must not be zero.");
    }
  }

  private void validateAccountOwner(AccountDetails value) {
    if (BigDecimalUtil.isNullOrZero(value.getCustomerNumber())) {
      throw new ValidationException("Field [customerNumber] must not be null or zero.");
    }

    final Optional<CustomerDetails> findCustomerResult = customerDetailsRepository
        .findByCustomerNumber(value.getCustomerNumber());
    if (findCustomerResult.isEmpty()) {
      throw new ValidationException(String.format("[customerNumber=%d] does not exists."));
    }
  }

  private void validateAccountProduct(AccountDetails value) {
    if (StringUtils.isBlank(value.getProductTypeCode())) {
      throw new ValidationException("Field [productTypeCode] must not be blank.");
    }

    final Optional<ProductDetails> findProductResult = productDetailsRepository
        .findByTypeCodeAndCurrencyCode(value.getProductTypeCode(), value.getCurrencyCode());
    if (findProductResult.isEmpty()) {
      throw new ValidationException(String.format("[productTypeCode=%s] and [currencyCode=%s] does not exists.",
          value.getProductTypeCode(), value.getCurrencyCode()));
    }
  }

}
