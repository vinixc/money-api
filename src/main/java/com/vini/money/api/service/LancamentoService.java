package com.vini.money.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.vini.money.api.model.Lancamento;
import com.vini.money.api.model.Pessoa;
import com.vini.money.api.repository.LancamentoRepository;
import com.vini.money.api.repository.PessoaRepository;
import com.vini.money.api.service.exception.PessoaInexistenteOuInativaException;

@Service
public class LancamentoService {
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	@Autowired
	private PessoaService pessoaService;

	public Lancamento salvar(Lancamento lancamento) {
		Pessoa pessoa = pessoaRepository.findOne(lancamento.getPessoa().getId());
		
		if(pessoa == null || pessoa.isInativo()) {
			throw new PessoaInexistenteOuInativaException();
		}
		
		return lancamentoRepository.save(lancamento);
	}

	public Lancamento atualizarLancamento(Long id, Lancamento lancamento) {
		Lancamento lancamentoSalvo = buscarLancamentoPeloId(id);
		pessoaService.validarPessoaAtivaOrInexistente(lancamento.getPessoa().getId());
		
		BeanUtils.copyProperties(lancamento, lancamentoSalvo, "id");
		
		return lancamentoRepository.save(lancamentoSalvo);
	}

	public Lancamento buscarLancamentoPeloId(Long id) {
		Lancamento lancamento = lancamentoRepository.findOne(id);
		
		if(lancamento != null) {
			return lancamento;
		}
		
		throw new EmptyResultDataAccessException(1);
	}
}
