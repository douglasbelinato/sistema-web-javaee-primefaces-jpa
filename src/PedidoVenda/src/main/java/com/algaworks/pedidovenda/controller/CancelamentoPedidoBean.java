package com.algaworks.pedidovenda.controller;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;

import com.algaworks.pedidovenda.model.Pedido;
import com.algaworks.pedidovenda.service.CancelamentoPedidoService;
import com.algaworks.pedidovenda.util.jsf.FacesUtil;

@Named
@RequestScoped
public class CancelamentoPedidoBean implements Serializable{

	private static final long serialVersionUID = -6247194008766795865L;
	
	@Inject
	private CancelamentoPedidoService cancelamentoPedidoService;
	
	@Inject
	private Event<PedidoAlteradoEvent> pedidoAlteradoEvent;
	
	@Inject
	@PedidoEdicao
	private Pedido pedido;
	
	public void cancelarPedido() {
		pedido = cancelamentoPedidoService.cancelar(pedido);
		pedidoAlteradoEvent.fire(new PedidoAlteradoEvent(pedido));
		
		FacesUtil.addInfoMessage("Pedido cancelado com sucesso!");
	}
	
	public boolean isNaoCancelavel() {		
		return cancelamentoPedidoService.isNaoCancelavel(pedido);
	}

}
