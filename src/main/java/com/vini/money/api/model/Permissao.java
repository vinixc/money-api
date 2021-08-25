package com.vini.money.api.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "permissao")
@SequenceGenerator(name = "permissao_seq", sequenceName = "permissao_seq", allocationSize = 1,initialValue = 1)
public class Permissao implements Serializable{
	private static final long serialVersionUID = -6929013020763141918L;
	
	@Id
	@Getter
	@GeneratedValue(generator = "permissao_seq", strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@Getter @Setter
	private String descricao;

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
		Permissao other = (Permissao) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Permissao [id=" + id + ", descricao=" + descricao + "]";
	}

}
