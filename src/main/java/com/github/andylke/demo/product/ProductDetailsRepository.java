package com.github.andylke.demo.product;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDetailsRepository extends JpaRepository<ProductDetails, UUID> {

  Optional<ProductDetails> findByTypeCodeAndCurrencyCode(String typeCode, String currencyCode);

}
