package com.vini.money.api.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Contato.class)
public class Contato_ {
	
	public static volatile SingularAttribute<Contato, Long> id;
	public static volatile SingularAttribute<Contato, String> nome;
	public static volatile SingularAttribute<Contato, String> email;
	public static volatile SingularAttribute<Contato, String> telefone;
	public static volatile SingularAttribute<Contato, Pessoa> pessoa; 

}
