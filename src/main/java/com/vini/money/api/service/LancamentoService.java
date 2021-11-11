package com.vini.money.api.service;

import java.io.InputStream;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.vini.money.api.dto.LancamentoEstatisticaPessoa;
import com.vini.money.api.model.Lancamento;
import com.vini.money.api.model.Pessoa;
import com.vini.money.api.repository.LancamentoRepository;
import com.vini.money.api.repository.PessoaRepository;
import com.vini.money.api.service.exception.PessoaInexistenteOuInativaException;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

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
	
	public byte[] relatorioPorPessoa(LocalDate inicio, LocalDate fim) throws JRException {
		List<LancamentoEstatisticaPessoa> dados = lancamentoRepository.porPessoa(inicio, fim);
		
		Map<String,Object> parametros = new HashMap<>();
		parametros.put("DT_INICIO", Date.valueOf(inicio));
		parametros.put("DT_FIM", Date.valueOf(fim));
		parametros.put("REPORT_LOCALE", new Locale("pt", "BR"));
		
		InputStream inputStream = this.getClass().getResourceAsStream("/relatorios/lancamentoPorPessoa.jasper");
		
		JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream,parametros, new JRBeanCollectionDataSource(dados));
		
		return JasperExportManager.exportReportToPdf(jasperPrint);
	}
	
	@Scheduled(cron = "0 0 6 * * *") // 0 0 0 0 0 0
	public void avisarSobreLancamentosVencidos() {
		
		System.out.println("METODO SENDO EXECUTADO....");
		
	}
}
