package com.vini.money.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vini.money.api.model.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long>{

}
