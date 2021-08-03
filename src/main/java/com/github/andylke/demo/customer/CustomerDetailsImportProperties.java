package com.github.andylke.demo.customer;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "demo.customer-details")
public class CustomerDetailsImportProperties {

  private String name = "customer-details-import";

  private String filePath = "classpath:customer-details.csv";

  private String[] fieldNames = new String[] { "number", "type", "name" };

  private int lineToSkip = 1;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getFilePath() {
    return filePath;
  }

  public void setFilePath(String filePath) {
    this.filePath = filePath;
  }

  public String[] getFieldNames() {
    return fieldNames;
  }

  public void setFieldNames(String[] fieldNames) {
    this.fieldNames = fieldNames;
  }

  public int getLineToSkip() {
    return lineToSkip;
  }

  public void setLineToSkip(int lineToSkip) {
    this.lineToSkip = lineToSkip;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}
