package com.algaworks.pedidovenda.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.algaworks.pedidovenda.model.Pedido;
import com.algaworks.pedidovenda.model.StatusPedido;
import com.algaworks.pedidovenda.repository.PedidoRepository;
import com.algaworks.pedidovenda.util.jpa.Transactional;

public class EmissaoPedidoService implements Serializable{

	private static final long serialVersionUID = 1793926243874431969L;

	@Inject
	private CadastroPedidoService cadastroPedidoService;
	
	@Inject
	private PedidoRepository pedidoRepository;
	
	@Inject
	private EstoqueService estoqueService;
	
	@Transactional
	public Pedido emitir(Pedido pedido) {
		pedido = cadastroPedidoService.salvar(pedido);
		
		if (cadastroPedidoService.isNaoEmissivel(pedido)) {
			throw new NegocioException("Pedido não pode ser emitido com status "
									+ pedido.getStatus().getDescricao() + ".");
		}
		
		estoqueService.baixarItensEstoque(pedido);
		
		pedido.setStatus(StatusPedido.EMITIDO);
		
		pedido = pedidoRepository.guardar(pedido);
		
		return pedido;
	}
	
	

}
