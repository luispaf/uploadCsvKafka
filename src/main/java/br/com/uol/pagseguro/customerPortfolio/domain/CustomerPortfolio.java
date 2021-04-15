package br.com.uol.pagseguro.customerPortfolio.domain;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "customer_portfolio")
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@Builder
public class CustomerPortfolio {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	private String businessUnit;
	private String name;
	private String segment;
	private String link;
	private String status;
}
