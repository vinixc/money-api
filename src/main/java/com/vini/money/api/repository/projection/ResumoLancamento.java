package com.vini.money.api.repository.projection;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.vini.money.api.enums.TipoLancamento;

import lombok.Getter;
import lombok.Setter;

public class ResumoLancamento {
	
	@Getter @Setter
	private Long id;
	@Getter @Setter
	private String descricao;
	@Getter @Setter
	private LocalDate dataVencimento;
	@Getter @Setter
	private LocalDate dataPagamento;
	@Getter @Setter
	private BigDecimal valor;
	@Getter @Setter
	private TipoLancamento tipo;
	@Getter @Setter
	private String categoria;
	@Getter @Setter
	private String pessoa;
	
	public ResumoLancamento(Long id, String descricao, LocalDate dataVencimento, LocalDate dataPagamento,
			BigDecimal valor, TipoLancamento tipo, String categoria, String pessoa) {
		this.id = id;
		this.descricao = descricao;
		this.dataVencimento = dataVencimento;
		this.dataPagamento = dataPagamento;
		this.valor = valor;
		this.tipo = tipo;
		this.categoria = categoria;
		this.pessoa = pessoa;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ResumoLancamento other = (ResumoLancamento) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
