package com.vini.money.api.repository.projection;

import lombok.Getter;
import lombok.Setter;

public class ResumoPessoa {
	@Getter @Setter
	private Long id;
	@Getter @Setter
	private String nome;
	@Getter @Setter
	private Boolean ativo;
	
	public ResumoPessoa(Long id, String nome, Boolean ativo) {
		this.id = id;
		this.nome = nome;
		this.ativo = ativo;
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
		ResumoPessoa other = (ResumoPessoa) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
