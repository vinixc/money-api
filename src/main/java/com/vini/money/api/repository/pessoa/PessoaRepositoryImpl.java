package com.vini.money.api.repository.pessoa;


import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import com.vini.money.api.model.Pessoa;
import com.vini.money.api.model.Pessoa_;
import com.vini.money.api.repository.filter.PessoaFilter;
import com.vini.money.api.repository.projection.ResumoPessoa;

public class PessoaRepositoryImpl implements PessoaRepositoryQuery{
	
	@PersistenceContext
	private EntityManager manager;

	@Override
	public Page<Pessoa> filtrar(PessoaFilter pessoaFilter, Pageable pageable) {
		//criando um builder de criteria
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		//criando a criteria de pessoa
		CriteriaQuery<Pessoa> criteria = builder.createQuery(Pessoa.class);
		
		//indicando de onde vou pegar os dados
		Root<Pessoa> root = criteria.from(Pessoa.class);
		
		//criando as restricoes, wherer
		Predicate[] predicates = getRestricoes(pessoaFilter,builder,root);
		criteria.where(predicates);
		
		TypedQuery<Pessoa> query = manager.createQuery(criteria);
		adicionarRestricoesDePaginacao(query,pageable);
		
		return new PageImpl<>(query.getResultList(), pageable, total(pessoaFilter));
	}
	
	@Override
	public Page<ResumoPessoa> resumo(PessoaFilter pessoaFilter, Pageable pageable) {
		//criando um builder de criteria
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		//criando a criteria de pessoa
		CriteriaQuery<ResumoPessoa> criteria = builder.createQuery(ResumoPessoa.class);
		//indicando de onde vou pegar os dados
		Root<Pessoa> root = criteria.from(Pessoa.class);
		
		criteria.select(
				builder.construct(
				ResumoPessoa.class,
				root.get(Pessoa_.id),
				root.get(Pessoa_.nome),
				root.get(Pessoa_.ativo)
				)
		);
		
		//Adicionando restricoes
		criteria.where(getRestricoes(pessoaFilter, builder, root));
		
		//monta query
		TypedQuery<ResumoPessoa> query = manager.createQuery(criteria);
		adicionarRestricoesDePaginacao(query, pageable);
		
		return new PageImpl<>(query.getResultList(),pageable, total(pessoaFilter));
	}

	private void adicionarRestricoesDePaginacao(TypedQuery<?> query, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int quantidadeRegistrosPorPagina = pageable.getPageSize();
		int primeiroRegistroPagina = paginaAtual * quantidadeRegistrosPorPagina;
		
		//First result: De qual registro comecar a buscar
		query.setFirstResult(primeiroRegistroPagina);
		//Max result: quantos registros pegar de la
		query.setMaxResults(quantidadeRegistrosPorPagina);
	}

	private long total(PessoaFilter pessoaFilter) {
		//criando um builder de criteria
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		//criando a criteria de long
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		
		//indicando de onde vou pegar os dados
		Root<Pessoa> root = criteria.from(Pessoa.class);
		
		//criando as restricoes, wherer
		Predicate[] restricoes = getRestricoes(pessoaFilter, builder, root);
		criteria.where(restricoes);
		
		CriteriaQuery<Long> query = criteria.select(builder.count(root));
		
		return manager.createQuery(query).getSingleResult();
	}

	private Predicate[] getRestricoes(PessoaFilter pessoaFilter, CriteriaBuilder builder, Root<Pessoa> root) {
		Set<Predicate> restricoes = new HashSet<>();
		
		if(!StringUtils.isEmpty(pessoaFilter.getNome())) {
			// adiciona restricao para busca pelo nome
			
			restricoes.add(
				builder.like(
						builder.lower(
							root.get(Pessoa_.nome)
						),
						"%" + pessoaFilter.getNome().toLowerCase() + "%"
				)
			);
		}
		
		return restricoes.toArray(new Predicate[restricoes.size()]);
	}
}
