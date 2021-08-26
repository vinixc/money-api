package com.vini.money.api.repository.pessoa;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.vini.money.api.model.Pessoa;
import com.vini.money.api.repository.filter.PessoaFilter;

public interface PessoaRepositoryQuery {
	
	public Page<Pessoa> filtrar(PessoaFilter pessoaFilter, Pageable pagleable);
}
