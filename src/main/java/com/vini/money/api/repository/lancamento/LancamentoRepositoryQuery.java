package com.vini.money.api.repository.lancamento;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.vini.money.api.model.Lancamento;
import com.vini.money.api.repository.filter.LancamentoFilter;

public interface LancamentoRepositoryQuery{
	
	public Page<Lancamento> filtrar(LancamentoFilter lancamentoFilter, Pageable pageable);
}
