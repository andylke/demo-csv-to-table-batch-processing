package com.github.andylke.demo.account;

import java.math.BigDecimal;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.apache.commons.lang3.builder.ToStringBuilder;

@Entity
public class AccountDetails {

  @Id
  @GeneratedValue
  private UUID id;

  @Column(unique = true, nullable = false, precision = 20)
  private BigDecimal accountNumber;

  @Column(nullable = false, precision = 20)
  private BigDecimal customerNumber;

  @Column(nullable = true, length = 50)
  private String productTypeCode;

  @Column(nullable = true, length = 3)
  private String currencyCode;

  @Column(nullable = true, precision = 25, scale = 4)
  private BigDecimal balance;

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public BigDecimal getAccountNumber() {
    return accountNumber;
  }

  public void setAccountNumber(BigDecimal accountNumber) {
    this.accountNumber = accountNumber;
  }

  public BigDecimal getCustomerNumber() {
    return customerNumber;
  }

  public void setCustomerNumber(BigDecimal customerNumber) {
    this.customerNumber = customerNumber;
  }

  public String getProductTypeCode() {
    return productTypeCode;
  }

  public void setProductTypeCode(String productTypeCode) {
    this.productTypeCode = productTypeCode;
  }

  public String getCurrencyCode() {
    return currencyCode;
  }

  public void setCurrencyCode(String currencyCode) {
    this.currencyCode = currencyCode;
  }

  public BigDecimal getBalance() {
    return balance;
  }

  public void setBalance(BigDecimal balance) {
    this.balance = balance;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}
