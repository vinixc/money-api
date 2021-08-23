package com.vini.money.api.repository.lancamento;

import java.util.List;

import com.vini.money.api.model.Lancamento;
import com.vini.money.api.repository.filter.LancamentoFilter;

public interface LancamentoRepositoryQuery{
	
	public List<Lancamento> filtrar(LancamentoFilter lancamentoFilter);
}
