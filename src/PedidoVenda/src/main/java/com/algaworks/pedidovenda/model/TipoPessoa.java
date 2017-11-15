package com.algaworks.pedidovenda.model;

public enum TipoPessoa {

	FISICA(1, "Física"), JURIDICA(2, "Jurídica");

	private int codigo;

	private String nome;

	private TipoPessoa(int codigo, String nome) {
		this.codigo = codigo;
		this.nome = nome;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
