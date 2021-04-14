package com.example.demo.customerPortfolio.infrastructure;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.customerPortfolio.domain.CustomerPortfolio;

public interface CustomerPortfolioSpringDataJPARepository extends JpaRepository<CustomerPortfolio, UUID> {
}
