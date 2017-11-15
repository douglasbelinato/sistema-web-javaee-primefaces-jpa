package com.algaworks.pedidovenda.util.jsf;

import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;

// 9.1. Tratando a exceção ViewExpiredException

public class JsfExceptionHandlerFactory extends ExceptionHandlerFactory {

	private ExceptionHandlerFactory parent;
	
	public JsfExceptionHandlerFactory(ExceptionHandlerFactory parent) {
		this.parent= parent;
	}
	
	@Override
	public ExceptionHandler getExceptionHandler() {
		return new JsfExceptionHandler(parent.getExceptionHandler());
	}

}
