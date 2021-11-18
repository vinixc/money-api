package com.vini.money.api.service;

import java.io.InputStream;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.vini.money.api.dto.LancamentoEstatisticaPessoa;
import com.vini.money.api.mail.Mailer;
import com.vini.money.api.model.Lancamento;
import com.vini.money.api.model.Pessoa;
import com.vini.money.api.repository.LancamentoRepository;
import com.vini.money.api.repository.PessoaRepository;
import com.vini.money.api.service.exception.PessoaInexistenteOuInativaException;
import com.vini.money.api.storage.S3;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class LancamentoService {
	
	private static final Logger logger = LoggerFactory.getLogger(LancamentoService.class);
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	@Autowired
	private PessoaService pessoaService;
	
	@Autowired
	private Mailer mailer;
	
	@Autowired
	private S3 s3;

	public Lancamento salvar(Lancamento lancamento) {
		Pessoa pessoa = pessoaRepository.findOne(lancamento.getPessoa().getId());
		
		if(pessoa == null || pessoa.isInativo()) {
			throw new PessoaInexistenteOuInativaException();
		}
		
		if(StringUtils.hasText(lancamento.getAnexo())) {
			s3.salvar(lancamento.getAnexo());
		}
		
		return lancamentoRepository.save(lancamento);
	}

	public Lancamento atualizarLancamento(Long id, Lancamento lancamento) {
		Lancamento lancamentoSalvo = buscarLancamentoPeloId(id);
		pessoaService.validarPessoaAtivaOrInexistente(lancamento.getPessoa().getId());
		
		if(StringUtils.isEmpty(lancamento.getAnexo())
				&& StringUtils.hasText(lancamentoSalvo.getAnexo())) {
			s3.remover(lancamentoSalvo.getAnexo());
		}else if(StringUtils.hasText(lancamento.getAnexo()) 
				&& !lancamento.getAnexo().equals(lancamentoSalvo.getAnexo())){
			s3.substituir(lancamentoSalvo.getAnexo(), lancamento.getAnexo());
			
		}
		
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
		if(logger.isDebugEnabled()) {
			logger.debug("Preparando envio de e-mails de aviso de lancamentos vencidos!");
		}
		
		List<Lancamento> vencidos = this.lancamentoRepository.findByDataVencimentoLessThanEqualAndDataPagamentoIsNull(LocalDate.now());
		
		if(vencidos == null || vencidos.isEmpty()) {
			logger.info("Sem lançamentos vencidos para aviso.");
			return;
		}
		
		logger.info("Existem {} lançamentos vencidos.", vencidos.size());
		
//		List<Usuario> destinatarios = this.usuarioRepository.findByPermissoesDescricao(DESTINATARIOS);
		
//		if(destinatarios == null || destinatarios.isEmpty()) {
//			logger.warn("Existem lancamentos vencidos, mas o sistema nao encontrou nenhum destinatario!");
//			return;
//		}
		
		this.mailer.avisarSobreLancamentosVencidos(vencidos);
		
		logger.info("Envio de e-mail de aviso concluído.");
	}
}
