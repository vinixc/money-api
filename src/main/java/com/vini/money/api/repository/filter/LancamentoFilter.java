package com.vini.money.api.repository.filter;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

public class LancamentoFilter {
	
	@Getter @Setter
	private String descricao;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Getter @Setter
	private LocalDate dataVencimentoDe;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Getter @Setter
	private LocalDate dataVencimentoAte;

}
