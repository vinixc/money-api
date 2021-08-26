package com.vini.money.api.repository.pessoa;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.vini.money.api.model.Pessoa;
import com.vini.money.api.repository.filter.PessoaFilter;
import com.vini.money.api.repository.projection.ResumoPessoa;

public interface PessoaRepositoryQuery {
	
	public Page<Pessoa> filtrar(PessoaFilter pessoaFilter, Pageable pageable);
	public Page<ResumoPessoa> resumo(PessoaFilter pessoaFilter, Pageable pageable);
}
