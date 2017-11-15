package com.algaworks.pedidovenda.controller;

import java.io.Serializable;
import java.util.Locale;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.velocity.tools.generic.NumberTool;

import com.algaworks.pedidovenda.model.Pedido;
import com.algaworks.pedidovenda.util.jsf.FacesUtil;
import com.algaworks.pedidovenda.util.mail.Mailer;
import com.outjected.email.api.MailMessage;
import com.outjected.email.impl.templating.velocity.VelocityTemplate;

@Named
@RequestScoped
public class EnvioPedidoEmailBean implements Serializable {

	private static final long serialVersionUID = 6959895233767179121L;
	
	@Inject
	private Mailer mailer;
	
	@Inject
	@PedidoEdicao
	private Pedido pedido;
	
	public void enviarPedido() {
		MailMessage message = mailer.novaMensagem();
		
		// Nota: A classe VelocityTemplate é da dependência simple-email. Ela faz a integração com a 
		// 		 dependência do Velocity. Por isso, preciso colocar no pom o Velocity, senão ocorre erro.
		
		try {
			message.to(pedido.getCliente().getEmail())
			.from("belinato.douglas@gmail.com")
			.subject("Pedido " + pedido.getId())
			.bodyHtml(new VelocityTemplate(getClass().getResourceAsStream("/emails/pedido.template")))
			.put("pedido", pedido) // passa um objeto pedido para o template do Velocity
			.put("numberTool", new NumberTool()) // passa um objeto capaz de formatar números no template do Velocity (classe importada da dep. velocity-tools)
			.put("locale", new Locale("pt", "BR")) // passa o Locale para o template do Velocity
			.send();
			
			FacesUtil.addInfoMessage("Pedido enviado por e-mail com sucesso!");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			FacesUtil.addErrorMessage("Erro!");
		}
	}

}
