package com.algaworks.pedidovenda.controller;

import java.io.IOException;
import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.algaworks.pedidovenda.util.jsf.FacesUtil;

@Named
@SessionScoped
public class LoginBean implements Serializable {

	private static final long serialVersionUID = 1689472638770402833L;
	
	@Inject
	private FacesContext facesContext;
	
	@Inject
	private HttpServletRequest request;
	
	@Inject
	private HttpServletResponse response;
	
	private String email;
	
	public void preRender() {
		if (request.getParameter("invalid") != null && request.getParameter("invalid").equals("true")) {
			FacesUtil.addErrorMessage("Usuário ou senha inválido!");
		}
	}
	
	public void login() throws ServletException, IOException {
		// Direciono para a url de validação do Spring Security
		// Nota: Coloco esse redirecionamento aqui porque, como estamos usando JSF, não consigo colocar essa
		// instrução diretamente no formulário de login da pagina (como faço em action based).
		RequestDispatcher dispatcher = request.getRequestDispatcher("/j_spring_security_check");
		dispatcher.forward(request, response);
		
		facesContext.responseComplete();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	

}
