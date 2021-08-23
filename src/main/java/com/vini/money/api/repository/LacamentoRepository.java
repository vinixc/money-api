package com.vini.money.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.vini.money.api.model.Lancamento;

public interface LacamentoRepository extends JpaRepository<Lancamento, Long>{
	
	@Query(value = "SELECT l FROM Lancamento l JOIN FETCH l.categoria JOIN FETCH l.pessoa "
			+ "WHERE l.id = ?1 ")
	Lancamento findById(Long id);

}
