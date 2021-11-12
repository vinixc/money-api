package com.vini.money.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.vini.money.api.model.Pessoa;
import com.vini.money.api.repository.PessoaRepository;
import com.vini.money.api.service.exception.PessoaInexistenteOuInativaException;

@Service
public class PessoaService {
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	public Pessoa atualizar(Long id,Pessoa pessoa) {
		Pessoa pessoaSalva = buscarPessoaPeloId(id);
		
		pessoaSalva.getContatos().clear();
		pessoaSalva.getContatos().addAll(pessoa.getContatos());
		
		//setando pessoa para os contatos dela.
		pessoaSalva.getContatos().forEach(c -> c.setPessoa(pessoaSalva));
				
		BeanUtils.copyProperties(pessoa, pessoaSalva, "id","contatos");
		return pessoaRepository.save(pessoaSalva);
	}

	public void atualizarPropriedateAtivo(Long id, Boolean ativo) {
		Pessoa pessoaSalva = buscarPessoaPeloId(id);
		pessoaSalva.setAtivo(ativo);
		pessoaRepository.save(pessoaSalva);
	}
	
	public Pessoa buscarPessoaPeloId(Long id) {
		Pessoa pessoaSalva = pessoaRepository.findOne(id);
		
		if(pessoaSalva == null) throw new EmptyResultDataAccessException(1);
		
		return pessoaSalva;
	}
	
	public void validarPessoaAtivaOrInexistente(Long pessoaId) {
		Pessoa pessoa = pessoaRepository.findOne(pessoaId);
		if(pessoa == null || !pessoa.getAtivo()) throw new PessoaInexistenteOuInativaException();
	}

	public Pessoa salvar(Pessoa pessoa) {
		//setando pessoa para os contatos dela.
		pessoa.getContatos().forEach(c -> c.setPessoa(pessoa));
		
		return pessoaRepository.save(pessoa);
	}
}
