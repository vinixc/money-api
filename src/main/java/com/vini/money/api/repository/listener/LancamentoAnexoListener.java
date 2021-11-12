package com.vini.money.api.repository.listener;

import javax.persistence.PostLoad;

import org.springframework.util.StringUtils;

import com.vini.money.api.MoneyApiApplication;
import com.vini.money.api.model.Lancamento;
import com.vini.money.api.storage.S3;

public class LancamentoAnexoListener {
	
	@PostLoad
	public void postLoad(Lancamento lancamento) {
		
		if(StringUtils.hasText(lancamento.getAnexo())) {
			
			S3 s3 = MoneyApiApplication.getBean(S3.class);
			lancamento.setUrlAnexo(s3.configurarUrl(lancamento.getAnexo()));
		}
	}
}
