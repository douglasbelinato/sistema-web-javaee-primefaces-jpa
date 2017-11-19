package com.algaworks.pedidovenda.repository.filter;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

public class ProdutosReportFilter implements Serializable {

	private static final long serialVersionUID = 1857506692299756649L;
	
	@NotNull
	private Long valorInicial;
	
	@NotNull
	private Long valorFinal;

	public Long getValorInicial() {
		return valorInicial;
	}

	public void setValorInicial(Long valorInicial) {
		this.valorInicial = valorInicial;
	}

	public Long getValorFinal() {
		return valorFinal;
	}

	public void setValorFinal(Long valorFinal) {
		this.valorFinal = valorFinal;
	}

}
