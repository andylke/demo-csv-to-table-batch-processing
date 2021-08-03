package com.github.andylke.demo.product;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "demo.product-details")
public class ProductDetailsImportProperties {

  private String filePath = "product-details.csv";

  private String[] fieldNames = new String[] { "typeCode", "currencyCode", "description" };

  private int lineToSkip = 1;

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
