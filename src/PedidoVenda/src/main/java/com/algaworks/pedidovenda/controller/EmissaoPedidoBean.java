package com.algaworks.pedidovenda.controller;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;

import com.algaworks.pedidovenda.model.Pedido;
import com.algaworks.pedidovenda.service.CadastroPedidoService;
import com.algaworks.pedidovenda.service.EmissaoPedidoService;
import com.algaworks.pedidovenda.util.jsf.FacesUtil;

@Named
@RequestScoped
public class EmissaoPedidoBean implements Serializable {

	private static final long serialVersionUID = -5025215133651322335L;
	
	@Inject
	@PedidoEdicao
	private Pedido pedido;
	
	@Inject
	private CadastroPedidoService cadastroPedidoService;
	
	@Inject
	private EmissaoPedidoService emissaoPedidoService;
	
	@Inject
	private Event<PedidoAlteradoEvent> pedidoAlteradoEvent; 
	
	public void emitirPedido() {
		cadastroPedidoService.removerItemVazio(pedido);
		
		try {
			pedido = emissaoPedidoService.emitir(pedido);
			
			// lança evento CDI
			pedidoAlteradoEvent.fire(new PedidoAlteradoEvent(pedido));
			
			FacesUtil.addInfoMessage("Pedido emitido com sucesso!");
		} finally {
			cadastroPedidoService.adicionarItemVazio(pedido);
		}
	}

}
