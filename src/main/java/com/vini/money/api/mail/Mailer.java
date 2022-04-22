package com.vini.money.api.mail;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.vini.money.api.config.property.MoneyApiProperty;
import com.vini.money.api.model.Lancamento;
import com.vini.money.api.model.Pessoa;
import com.vini.money.api.model.Usuario;


@Component
public class Mailer {
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private TemplateEngine thymeleaf;
	
	@Autowired
	private MoneyApiProperty property;
	
//	@EventListener
//	public void teste(ApplicationReadyEvent event) {
//		String template = "mail/aviso-lancamentos-vencidos";
//		
//		List<Lancamento> lista = repo.findAll();
//		
//		Map<String, Object> variaveis = new HashMap<>();
//		variaveis.put("lancamentos", lista);
//		
//		System.out.println("Iniciou envio de email");
//		
//		enviarEmail("email@gmail.com", Arrays.asList("email@gmail.com"), "Teste envio email", template, variaveis);
//		System.out.println("Terminou envio de email");
//	}
	
	public void avisarSobreLancamentosVencidos(List<Lancamento> vencidos) {
		String template = "mail/aviso-lancamentos-vencidos";		
		
		Map<Pessoa, List<Lancamento>> vencidosPorPessoa = vencidos.stream().collect(Collectors.groupingBy(v -> v.getPessoa()));
		
		for(Entry<Pessoa, List<Lancamento>> entrySet : vencidosPorPessoa.entrySet()) {
			Pessoa pessoa = entrySet.getKey();
			
			if(pessoa.getContatos() != null && !pessoa.getContatos().isEmpty()) {
				
				List<Lancamento> lancamentos = entrySet.getValue();
				
				Map<String, Object> variaveis = new HashMap<>();
				variaveis.put("lancamentos", lancamentos);
				
				List<String> emails = pessoa.getContatos().stream().map(c -> c.getEmail()).collect(Collectors.toList());
				
				this.enviarEmail(property.getMail().getFrom(), emails, "Lancamentos Vencidos", template, variaveis);
			}
		}
	}
	
	public void avisarSobreLancamentosVencidos(List<Lancamento> vencidos, List<Usuario> destinatarios) {
		String template = "mail/aviso-lancamentos-vencidos";		
		
		Map<String, Object> variaveis = new HashMap<>();
		variaveis.put("lancamentos", vencidos);
		
		List<String> emails = destinatarios.stream().map(u -> u.getEmail()).collect(Collectors.toList());
		
		this.enviarEmail(property.getMail().getUsername(), emails, "Lancamentos Vencidos", template, variaveis);
	}
	
	public void enviarEmail(String remetente, List<String> destinatarios, String assunto, String template, Map<String, Object> variaveis) {
		
		Context context = new Context(new Locale("pt", "BR"));
		
		variaveis.entrySet().forEach(e -> context.setVariable(e.getKey(), e.getValue()));
		
		String mensagem = thymeleaf.process(template, context);
		
		enviarEmail(remetente, destinatarios, assunto, mensagem);
	}
	
	public void enviarEmail(String remetente, List<String> destinatarios, String assunto, String mensagem) {
		
		try {
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");

			helper.setFrom(remetente);
			helper.setTo(destinatarios.toArray(new String[destinatarios.size()]));
			helper.setSubject(assunto);
			helper.setText(mensagem, true);
			
			mailSender.send(mimeMessage);
		} catch (MessagingException e) {
			throw new RuntimeException("Problema com o envio de email!", e);
		}
	}
}
