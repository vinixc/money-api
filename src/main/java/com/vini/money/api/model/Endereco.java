package com.vini.money.api.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Embeddable
public class Endereco implements Serializable{
	private static final long serialVersionUID = -2606736569048248154L;
	
	@Getter @Setter
	private String logradouro;
	@Getter @Setter
	private String numero;
	@Getter @Setter
	private String complemento;
	@Getter @Setter
	private String bairro;
	@Getter @Setter
	private String cep;
	
	@ManyToOne
	@JoinColumn(name = "id_cidade")
	@Getter @Setter
	private Cidade cidade;
	
	public Endereco() {}
	
	public Endereco(String logradouro, String numero, String complemento, String bairro, String cep) {
		this.logradouro = logradouro;
		this.numero = numero;
		this.complemento = complemento;
		this.bairro = bairro;
		this.cep = cep;
	}

	@Override
	public String toString() {
		return "Endereco [logradouro=" + logradouro + ", numero=" + numero + ", complemento=" + complemento
				+ ", bairro=" + bairro + ", cep=" + cep;
	}
}
