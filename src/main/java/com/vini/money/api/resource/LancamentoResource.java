package com.vini.money.api.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vini.money.api.model.Lancamento;
import com.vini.money.api.repository.LacamentoRepository;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoResource {
	
	@Autowired
	private LacamentoRepository lacamentoRepository;
	
	@GetMapping
	public ResponseEntity<List<Lancamento>> listar() {
		List<Lancamento> lancamentos = lacamentoRepository.findAll();
		return lancamentos.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(lancamentos);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Lancamento> buscaPeloId(@PathVariable(name = "id") Long id){
		Lancamento lancamento = lacamentoRepository.findOne(id);
		return lancamento != null ? ResponseEntity.ok(lancamento) : ResponseEntity.notFound().build();
	}
}
