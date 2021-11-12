package com.vini.money.api.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vini.money.api.enums.TipoLancamento;
import com.vini.money.api.repository.listener.LancamentoAnexoListener;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "lancamento")
@SequenceGenerator(name = "lancamento_seq", sequenceName = "lancamento_seq", initialValue = 1, allocationSize = 1)
@EntityListeners(LancamentoAnexoListener.class)
public class Lancamento implements Serializable{
	private static final long serialVersionUID = 8164846250512875043L;
	
	@Id
	@Getter
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "lancamento_seq")
	private Long id;
	
	@Getter @Setter
	@NotNull @Size(min = 5,max = 50)
	private String descricao;
	
	@NotNull
	@Getter @Setter
	@Column(name = "data_vencimento", columnDefinition = "DATE")
	private LocalDate dataVencimento;
	
	@Getter @Setter
	@Column(name = "data_pagamento", columnDefinition = "DATE")
	private LocalDate dataPagamento;
	
	@Getter @Setter @NotNull
	private BigDecimal valor;
	
	@Getter @Setter @Size(max = 100)
	private String observacao;
	
	@NotNull
	@Getter @Setter
	@Enumerated(EnumType.STRING)
	private TipoLancamento tipo;
	
	@ManyToOne
	@Getter @Setter
	@JoinColumn(name = "id_categoria")
	@NotNull
	private Categoria categoria;
	
	@ManyToOne
	@Getter @Setter
	@JoinColumn(name = "id_pessoa")
	@NotNull
	@JsonIgnoreProperties("contatos")
	private Pessoa pessoa;
	
	@Getter @Setter
	private String anexo;
	
	@Transient
	@Getter @Setter
	private String urlAnexo;
	
	@JsonIgnore
	public boolean isReceita() {
		return TipoLancamento.RECEITA.equals(this.tipo);
	}
	
	public Lancamento() {}

	public Lancamento(String descricao, LocalDate dataVencimento, LocalDate dataPagamento, BigDecimal valor,
			String observacao, TipoLancamento tipo, Categoria categoria, Pessoa pessoa) {
		this.descricao = descricao;
		this.dataVencimento = dataVencimento;
		this.dataPagamento = dataPagamento;
		this.valor = valor;
		this.observacao = observacao;
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
		Lancamento other = (Lancamento) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Lancamento [id=" + id + ", descricao=" + descricao + ", dataVencimento=" + dataVencimento
				+ ", dataPagamento=" + dataPagamento + ", valor=" + valor + ", observacao=" + observacao + ", tipo="
				+ tipo + ", categoria=" + categoria + ", pessoa=" + pessoa + "]";
	}
}
