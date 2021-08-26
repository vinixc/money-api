package com.vini.money.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vini.money.api.model.Pessoa;
import com.vini.money.api.repository.pessoa.PessoaRepositoryQuery;

public interface PessoaRepository extends JpaRepository<Pessoa, Long>, PessoaRepositoryQuery{

}
