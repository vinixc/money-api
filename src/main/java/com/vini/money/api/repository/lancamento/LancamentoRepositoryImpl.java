package com.vini.money.api.repository.lancamento;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import com.vini.money.api.dto.LancamentoEstatisticaCategoria;
import com.vini.money.api.dto.LancamentoEstatisticaDia;
import com.vini.money.api.dto.LancamentoEstatisticaPessoa;
import com.vini.money.api.model.Categoria_;
import com.vini.money.api.model.Lancamento;
import com.vini.money.api.model.Lancamento_;
import com.vini.money.api.model.Pessoa_;
import com.vini.money.api.repository.filter.LancamentoFilter;
import com.vini.money.api.repository.projection.ResumoLancamento;

public class LancamentoRepositoryImpl implements LancamentoRepositoryQuery{

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public Page<Lancamento> filtrar(LancamentoFilter lancamentoFilter,Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Lancamento> criteria = builder.createQuery(Lancamento.class);
		
		Root<Lancamento> root = criteria.from(Lancamento.class);
		
		//criar restricoes
		Predicate[] predicates = criarRestricoes(lancamentoFilter, builder, root);
		criteria.where(predicates);
		
		adicionaOrdenacao(pageable, builder, criteria, root);
		
		TypedQuery<Lancamento> query = manager.createQuery(criteria);
		adicionarRestricoesDePaginacao(query,pageable);
		
		return new PageImpl<>(query.getResultList(), pageable, total(lancamentoFilter));
	}
	
	@Override
	public Page<ResumoLancamento> resumir(LancamentoFilter lancamentoFilter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<ResumoLancamento> criteria = builder.createQuery(ResumoLancamento.class);
		
		Root<Lancamento> root = criteria.from(Lancamento.class);
		
		criteria.select(builder.construct(ResumoLancamento.class, 
				root.get(Lancamento_.id),
				root.get(Lancamento_.descricao),
				root.get(Lancamento_.dataVencimento),
				root.get(Lancamento_.dataPagamento),
				root.get(Lancamento_.valor),
				root.get(Lancamento_.tipo),
				root.get(Lancamento_.categoria).get(Categoria_.nome),
				root.get(Lancamento_.pessoa).get(Pessoa_.nome)
		));

		//criar restricoes
		Predicate[] predicates = criarRestricoes(lancamentoFilter, builder, root);
		criteria.where(predicates);
		
		adicionaOrdenacao(pageable, builder, criteria, root);
		
		TypedQuery<ResumoLancamento> query = manager.createQuery(criteria);
		adicionarRestricoesDePaginacao(query,pageable);
		
		return new PageImpl<>(query.getResultList(), pageable, total(lancamentoFilter));
	}
	
	@Override
	public List<LancamentoEstatisticaCategoria> porCategoria(LocalDate mesReferente) {
		//Criando criteria builder
		CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
		
		//Criando criteria query da class que sera construida com o resultado da pesquisa.
		CriteriaQuery<LancamentoEstatisticaCategoria> criteriaQuery = criteriaBuilder.createQuery(LancamentoEstatisticaCategoria.class);
		
		//Criando o root de Lancamento, onde a consulta sera realizada.
		Root<Lancamento> root = criteriaQuery.from(Lancamento.class);
		
		//Criando os campos que serao consultados e passando ao construtor do dto
		criteriaQuery.select(criteriaBuilder.construct(LancamentoEstatisticaCategoria.class, 
				root.get(Lancamento_.categoria),
				criteriaBuilder.sum(root.get(Lancamento_.valor))
		));
		
		//Primeiro dia do mes informado
		LocalDate primeiroDia = mesReferente.withDayOfMonth(1);
		
		//Ultimo dia do mes informado
		LocalDate ultimoDia = mesReferente.withDayOfMonth(mesReferente.lengthOfMonth());
		
		//Criando where, dataVencimento maior ou igual ao primeiro dia e data vencimento menor ou igual ao ultimo dia do mes
		criteriaQuery.where(
				criteriaBuilder.greaterThanOrEqualTo(root.get(Lancamento_.dataVencimento), primeiroDia),
				criteriaBuilder.lessThanOrEqualTo(root.get(Lancamento_.dataVencimento), ultimoDia)
		);
		
		//Agrupando por categoria
		criteriaQuery.groupBy(root.get(Lancamento_.categoria));
		
		//Criando o typedQuery
		TypedQuery<LancamentoEstatisticaCategoria> typedQuery = manager.createQuery(criteriaQuery);
		
		//Retornando consulta
		return typedQuery.getResultList();
	}
	
	@Override
	public List<LancamentoEstatisticaDia> porDia(LocalDate mesReferente) {
		//Criando criteria builder
		CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
		
		//Criando criteria query da class que sera construida com o resultado da pesquisa.
		CriteriaQuery<LancamentoEstatisticaDia> criteriaQuery = criteriaBuilder.createQuery(LancamentoEstatisticaDia.class);
		
		//Criando o root de Lancamento, onde a consulta sera realizada.
		Root<Lancamento> root = criteriaQuery.from(Lancamento.class);
		
		//Criando os campos que serao consultados e passando ao construtor do dto
		criteriaQuery.select(criteriaBuilder.construct(LancamentoEstatisticaDia.class, 
				root.get(Lancamento_.tipo),
				root.get(Lancamento_.dataVencimento),
				criteriaBuilder.sum(root.get(Lancamento_.valor))
		));
		
		//Primeiro dia do mes informado
		LocalDate primeiroDia = mesReferente.withDayOfMonth(1);
		
		//Ultimo dia do mes informado
		LocalDate ultimoDia = mesReferente.withDayOfMonth(mesReferente.lengthOfMonth());
		
		//Criando where, dataVencimento maior ou igual ao primeiro dia e data vencimento menor ou igual ao ultimo dia do mes
		criteriaQuery.where(
				criteriaBuilder.greaterThanOrEqualTo(root.get(Lancamento_.dataVencimento), primeiroDia),
				criteriaBuilder.lessThanOrEqualTo(root.get(Lancamento_.dataVencimento), ultimoDia)
		);
		
		//Agrupando por tipo e data vencimento
		criteriaQuery.groupBy(root.get(Lancamento_.tipo),root.get(Lancamento_.dataVencimento));
		
		//Criando o typedQuery
		TypedQuery<LancamentoEstatisticaDia> typedQuery = manager.createQuery(criteriaQuery);
		
		//Retornando consulta
		return typedQuery.getResultList();
	}
	
	@Override
	public List<LancamentoEstatisticaPessoa> porPessoa(LocalDate inicio, LocalDate fim) {
		//Criando criteria builder
		CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
		
		//Criando criteria query da class que sera construida com o resultado da pesquisa.
		CriteriaQuery<LancamentoEstatisticaPessoa> criteriaQuery = criteriaBuilder.createQuery(LancamentoEstatisticaPessoa.class);
		
		//Criando o root de Lancamento, onde a consulta sera realizada.
		Root<Lancamento> root = criteriaQuery.from(Lancamento.class);
		
		//Criando os campos que serao consultados e passando ao construtor do dto
		criteriaQuery.select(criteriaBuilder.construct(LancamentoEstatisticaPessoa.class, 
				root.get(Lancamento_.tipo),
				root.get(Lancamento_.pessoa),
				criteriaBuilder.sum(root.get(Lancamento_.valor))
		));
				
		//Criando where, dataVencimento maior ou igual ao primeiro dia e data vencimento menor ou igual ao ultimo dia do mes
		criteriaQuery.where(
				criteriaBuilder.greaterThanOrEqualTo(root.get(Lancamento_.dataVencimento), inicio),
				criteriaBuilder.lessThanOrEqualTo(root.get(Lancamento_.dataVencimento), fim)
		);
		
		//Agrupando por tipo e data vencimento
		criteriaQuery.groupBy(root.get(Lancamento_.tipo),root.get(Lancamento_.pessoa));
		
		//Criando o typedQuery
		TypedQuery<LancamentoEstatisticaPessoa> typedQuery = manager.createQuery(criteriaQuery);
		
		//Retornando consulta
		return typedQuery.getResultList();
	}

	private Predicate[] criarRestricoes(LancamentoFilter lancamentoFilter, CriteriaBuilder builder,
			Root<Lancamento> root) {
		
		List<Predicate> predicates = new ArrayList<>();
		
		if(!StringUtils.isEmpty(lancamentoFilter.getDescricao())) {
			
			predicates.add(builder.like(
					builder.lower(root.get(Lancamento_.descricao)), "%" + lancamentoFilter.getDescricao().toLowerCase() + "%"));
		}
		
		if(lancamentoFilter.getDataVencimentoDe() != null) {
			predicates.add(
					builder.greaterThanOrEqualTo(root.get(Lancamento_.dataVencimento), lancamentoFilter.getDataVencimentoDe()));
		}
		
		if(lancamentoFilter.getDataVencimentoAte() != null) {
			predicates.add(
					builder.lessThanOrEqualTo(root.get(Lancamento_.dataVencimento), lancamentoFilter.getDataVencimentoAte()));
		}
		
		return predicates.toArray(new Predicate[predicates.size()]);
	}
	
	private void adicionaOrdenacao(Pageable pageable, CriteriaBuilder builder, CriteriaQuery<?> criteria,
			Root<Lancamento> root) {
		//Adicionando order by de acordo com a propriedade sort
		if(pageable.getSort() != null) {
			List<Order> orders = new ArrayList<>();
			pageable.getSort().forEach(order ->{
				
				if(order.isAscending()) {
					orders.add(builder.asc(root.get(order.getProperty())));
				}else {
					orders.add(builder.desc(root.get(order.getProperty())));
				}
			});
			
			criteria.orderBy(orders);
		}
	}
	
	private void adicionarRestricoesDePaginacao(TypedQuery<?> query, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int totalRegistroPorPagina = pageable.getPageSize();
		int primeiroRegistroDaPagina = paginaAtual * totalRegistroPorPagina;
		
		query.setFirstResult(primeiroRegistroDaPagina);
		query.setMaxResults(totalRegistroPorPagina);
	}
	
	private long total(LancamentoFilter lancamentoFilter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		
		Root<Lancamento> root = criteria.from(Lancamento.class);
		
		Predicate[] predicates = criarRestricoes(lancamentoFilter, builder, root);
		criteria.where(predicates);
		
		criteria.select(builder.count(root));
		return manager.createQuery(criteria).getSingleResult();
	}
}
