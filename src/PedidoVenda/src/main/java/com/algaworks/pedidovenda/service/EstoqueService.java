package com.algaworks.pedidovenda.service;

import java.io.Serializable;

import javax.inject.Inject;
import javax.persistence.Transient;

import com.algaworks.pedidovenda.model.ItemPedido;
import com.algaworks.pedidovenda.model.Pedido;
import com.algaworks.pedidovenda.repository.PedidoRepository;

public class EstoqueService implements Serializable {

	private static final long serialVersionUID = 1665712678827082770L;
	
	@Inject
	private PedidoRepository pedidoRepository;

	@Transient
	public void baixarItensEstoque(Pedido pedido) {
		pedido = pedidoRepository.porId(pedido.getId());
		
		for (ItemPedido item : pedido.getItens()) {
			item.getProduto().baixarQuantidade(item.getQuantidade());
		}
	}

	public void retornarItensEstoque(Pedido pedido) {
		pedido = pedidoRepository.porId(pedido.getId());
		
		for (ItemPedido item : pedido.getItens()) {
			item.getProduto().adicionarEstoque(item.getQuantidade());
		}
	}
	
	

}
