package com.github.andylke.demo.customer;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerDetailsRepository extends JpaRepository<CustomerDetails, UUID> {
  
  Optional<CustomerDetails> findByCustomerNumber(BigDecimal customerNumber);
}
