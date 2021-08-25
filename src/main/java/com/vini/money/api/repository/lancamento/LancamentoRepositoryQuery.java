package com.vini.money.api.repository.lancamento;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.vini.money.api.model.Lancamento;
import com.vini.money.api.repository.filter.LancamentoFilter;
import com.vini.money.api.repository.projection.ResumoLancamento;

public interface LancamentoRepositoryQuery{
	
	public Page<Lancamento> filtrar(LancamentoFilter lancamentoFilter, Pageable pageable);
	public Page<ResumoLancamento> resumir(LancamentoFilter lancamentoFilter, Pageable pageable);
}
