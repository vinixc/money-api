package com.vini.money.api.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

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
	@Getter @Setter
	private String cidade;
	@Getter @Setter
	private String estado;
	
	public Endereco() {}
	
	public Endereco(String logradouro, String numero, String complemento, String bairro, String cep, String cidade,
			String estado) {
		this.logradouro = logradouro;
		this.numero = numero;
		this.complemento = complemento;
		this.bairro = bairro;
		this.cep = cep;
		this.cidade = cidade;
		this.estado = estado;
	}

	@Override
	public String toString() {
		return "Endereco [logradouro=" + logradouro + ", numero=" + numero + ", complemento=" + complemento
				+ ", bairro=" + bairro + ", cep=" + cep + ", cidade=" + cidade + ", estado=" + estado + "]";
	}
}
