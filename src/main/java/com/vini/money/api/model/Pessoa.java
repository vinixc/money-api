package com.vini.money.api.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "pessoa")
@SequenceGenerator(name = "pessoa_seq", sequenceName = "pessoa_seq", initialValue = 1, allocationSize = 1)
public class Pessoa implements Serializable{
	private static final long serialVersionUID = 2094195610954509031L;
	
	@Id
	@Getter
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "pessoa_seq")
	private Long id;
	
	@Getter @Setter 
	@NotNull @Size(min = 3, max = 60)
	private String nome;
	
	@Getter @Setter @NotNull
	private Boolean ativo;
	
	@Embedded @Getter @Setter
	private Endereco endereco;
	
	@Valid
	@OneToMany(mappedBy = "pessoa", cascade = CascadeType.ALL, orphanRemoval = true)
	@Getter @Setter
	@JsonIgnoreProperties("pessoa") //ignorando pessoa de contato no json
	private List<Contato> contatos;

	public Pessoa() {}
	
	public Pessoa(String nome, Boolean ativo, Endereco endereco) {
		this.nome = nome;
		this.ativo = ativo;
		this.endereco = endereco;
	}
	
	@JsonIgnore
	@Transient
	public boolean isInativo() {
		return !this.ativo;
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
		Pessoa other = (Pessoa) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
