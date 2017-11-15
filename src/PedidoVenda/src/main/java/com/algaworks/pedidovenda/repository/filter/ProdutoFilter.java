package com.algaworks.pedidovenda.repository.filter;

import java.io.Serializable;

import com.algaworks.pedidovenda.validation.SKU;

public class ProdutoFilter implements Serializable {

	private static final long serialVersionUID = -2941181121235299722L;

	@SKU
	private String sku;
	
	private String nome;

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku == null ? null : sku.toUpperCase();
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
