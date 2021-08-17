package com.vini.money.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.vini.money.api.model.Pessoa;
import com.vini.money.api.repository.PessoaRepository;

@Service
public class PessoaService {
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	public Pessoa atualizar(Long id,Pessoa pessoa) {
		Pessoa pessoaSalva = pessoaRepository.findOne(id);
		
		if(pessoaSalva == null) throw new EmptyResultDataAccessException(1);
			
		BeanUtils.copyProperties(pessoa, pessoaSalva, "id");
		return pessoaRepository.save(pessoaSalva);
	}
}
