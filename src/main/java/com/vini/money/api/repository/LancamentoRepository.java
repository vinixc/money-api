package com.vini.money.api.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.vini.money.api.model.Lancamento;
import com.vini.money.api.repository.lancamento.LancamentoRepositoryQuery;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long>, LancamentoRepositoryQuery{
	
	@Query(value = "SELECT l FROM Lancamento l JOIN FETCH l.categoria JOIN FETCH l.pessoa "
			+ "WHERE l.id = ?1 ")
	Lancamento findById(Long id);
	
	@Query(value = "SELECT l FROM Lancamento l "
			+ "JOIN FETCH l.categoria "
			+ "JOIN FETCH l.pessoa pessoa "
			+ "LEFT JOIN FETCH pessoa.contatos "
			+ "WHERE l.dataVencimento <= ?1 AND l.dataPagamento IS NULL ")
	List<Lancamento> findByDataVencimentoLessThanEqualAndDataPagamentoIsNull(LocalDate data);

}
