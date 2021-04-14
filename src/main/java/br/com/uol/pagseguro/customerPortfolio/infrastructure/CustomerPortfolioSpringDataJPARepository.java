package br.com.uol.pagseguro.customerPortfolio.infrastructure;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.uol.pagseguro.customerPortfolio.domain.CustomerPortfolio;

public interface CustomerPortfolioSpringDataJPARepository extends JpaRepository<CustomerPortfolio, UUID> {
}
