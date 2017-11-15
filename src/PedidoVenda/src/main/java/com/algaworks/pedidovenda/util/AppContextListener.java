package com.algaworks.pedidovenda.util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class AppContextListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// Chamado quando o TomCat subir e for feito o deploy da aplicação		
		
		// Definição encontrada na doc. do TomCat
		// Utilizado para informar que campos da tela que são do tipo Integer / Long não devem ser 
		// convertidos automaticamente para zero quando não são preenchidos. 
		// (Aula 13.1. Implementando a pesquisa de pedidos)
		System.setProperty("org.apache.el.parser.COERCE_TO_ZERO", "false"); 
	}
	
	

}
