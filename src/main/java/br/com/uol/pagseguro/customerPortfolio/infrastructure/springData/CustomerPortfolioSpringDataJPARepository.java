package br.com.uol.pagseguro.customerPortfolio.infrastructure.springData;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.uol.pagseguro.customerPortfolio.domain.CustomerPortfolio;

public interface CustomerPortfolioSpringDataJPARepository extends JpaRepository<CustomerPortfolio, UUID> {
	@Query("Select c from CustomerPortfolio c where status = '1'")
	List<CustomerPortfolio> findByStatusActive();
}
