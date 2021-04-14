package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entities.Carga;

public interface CargaRepository extends JpaRepository<Carga, Long>, JpaSpecificationExecutor<Carga>{

	@Query("Select c from Carga c where ativo = true")
	List<Carga> buscarAtivos();
}
