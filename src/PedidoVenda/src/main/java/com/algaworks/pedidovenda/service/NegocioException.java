package com.algaworks.pedidovenda.service;

public class NegocioException extends RuntimeException {
	
	private static final long serialVersionUID = 6214732052541078101L;

	public NegocioException(String msg) {
		super(msg);
	}

}
