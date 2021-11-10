package com.vini.money.api.repository.lancamento;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.vini.money.api.dto.LancamentoEstatisticaCategoria;
import com.vini.money.api.model.Lancamento;
import com.vini.money.api.repository.filter.LancamentoFilter;
import com.vini.money.api.repository.projection.ResumoLancamento;

public interface LancamentoRepositoryQuery{
	
	public List<LancamentoEstatisticaCategoria> porCategoria(LocalDate mesReferente);
	
	public Page<Lancamento> filtrar(LancamentoFilter lancamentoFilter, Pageable pageable);
	public Page<ResumoLancamento> resumir(LancamentoFilter lancamentoFilter, Pageable pageable);
}
