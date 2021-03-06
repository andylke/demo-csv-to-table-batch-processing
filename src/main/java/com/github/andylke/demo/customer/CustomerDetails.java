package com.github.andylke.demo.customer;

import java.math.BigDecimal;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.apache.commons.lang3.builder.ToStringBuilder;

@Entity
public class CustomerDetails {

  @Id
  @GeneratedValue
  private UUID id;

  @Column(unique = true, nullable = false, precision = 20)
  private BigDecimal customerNumber;

  @Column(nullable = false, length = 50)
  private String typeCode;

  @Column(nullable = false, length = 300)
  private String name;

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public BigDecimal getCustomerNumber() {
    return customerNumber;
  }

  public void setCustomerNumber(BigDecimal customerNumber) {
    this.customerNumber = customerNumber;
  }

  public String getTypeCode() {
    return typeCode;
  }

  public void setTypeCode(String typeCode) {
    this.typeCode = typeCode;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}
