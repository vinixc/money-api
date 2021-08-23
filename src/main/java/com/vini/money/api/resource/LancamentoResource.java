package com.vini.money.api.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vini.money.api.event.RecursoCriadoEvent;
import com.vini.money.api.model.Lancamento;
import com.vini.money.api.repository.LacamentoRepository;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoResource {
	
	@Autowired
	private LacamentoRepository lancamentoRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping
	public ResponseEntity<List<Lancamento>> listar() {
		List<Lancamento> lancamentos = lancamentoRepository.findAll();
		return lancamentos.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(lancamentos);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Lancamento> buscaPeloId(@PathVariable(name = "id") Long id){
		Lancamento lancamento = lancamentoRepository.findOne(id);
		return lancamento != null ? ResponseEntity.ok(lancamento) : ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<Lancamento> criarLancamento(@Valid @RequestBody Lancamento lancamento, HttpServletResponse response){
		lancamentoRepository.save(lancamento);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, lancamento.getId()));
		
		Lancamento lancamentoBanco = lancamentoRepository.findById(lancamento.getId());
		return ResponseEntity.status(HttpStatus.CREATED).body(lancamentoBanco);
	}
}
