package com.algaworks.pedidovenda.controller;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.algaworks.pedidovenda.model.Cliente;
import com.algaworks.pedidovenda.util.jsf.FacesUtil;
import com.algaworks.pedidovenda.util.mail.Mailer;
import com.outjected.email.api.MailMessage;
import com.outjected.email.impl.templating.velocity.VelocityTemplate;

@Named
@RequestScoped
public class EnviarClienteEmailBean implements Serializable {

	private static final long serialVersionUID = -5295982973106863184L;
	
	@Inject
	private Mailer mailer;
	
	@Inject
	@ClienteEdicao
	private Cliente cliente;
	
	public void enviarEmail() {
		MailMessage message = mailer.novaMensagem();
		
		// Nota: A classe VelocityTemplate é da dependência simple-email. Ela faz a integração com a 
		// 		 dependência do Velocity. Por isso, preciso colocar no pom o Velocity, senão ocorre erro.
		
		try {
			message.to(cliente.getEmail())
			.from("belinato.douglas@gmail.com")
			.subject("Cadastro Cliente - " + cliente.getId() + " / " + cliente.getNome())
			.bodyHtml(new VelocityTemplate(getClass().getResourceAsStream("/emails/cliente.template")))
			.put("cliente", cliente) // passa um objeto cliente para o template do Velocity
			//.put("numberTool", new NumberTool()) // passa um objeto capaz de formatar números no template do Velocity (classe importada da dep. velocity-tools)
			//.put("locale", new Locale("pt", "BR")) // passa o Locale para o template do Velocity
			.send();
			
			FacesUtil.addInfoMessage("Cadastro de cliente enviado por e-mail com sucesso!");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			FacesUtil.addErrorMessage("Erro!");
		}
	}

}
