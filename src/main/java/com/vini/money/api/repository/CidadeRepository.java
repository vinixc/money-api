package com.vini.money.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vini.money.api.model.Cidade;

public interface CidadeRepository extends JpaRepository<Cidade, Long>{

	List<Cidade> findByEstadoId(Long estadoId);
}
