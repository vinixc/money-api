package com.vini.money.api.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "usuario")
@SequenceGenerator(name = "usuario_seq", sequenceName = "usuario_seq", allocationSize = 1,initialValue = 1)
public class Usuario implements Serializable{
	private static final long serialVersionUID = -8858373410611214644L;
	
	@Id
	@Getter
	@GeneratedValue(generator = "usuario_seq", strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@Getter @Setter
	private String nome;
	@Getter @Setter
	private String email;
	@Getter @Setter
	private String senha;
	
	@Getter
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "usuario_permissao", 
	joinColumns 		= @JoinColumn(name = "id_usuario"), 
	inverseJoinColumns  = @JoinColumn(name = "id_permissao"))
	private List<Permissao> permissoes;
	
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
		Usuario other = (Usuario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nome=" + nome + ", email=" + email + ", senha=" + senha + "]";
	}

}
