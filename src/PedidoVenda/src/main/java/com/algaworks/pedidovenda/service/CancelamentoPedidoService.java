package com.algaworks.pedidovenda.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.algaworks.pedidovenda.model.Pedido;
import com.algaworks.pedidovenda.model.StatusPedido;
import com.algaworks.pedidovenda.repository.PedidoRepository;
import com.algaworks.pedidovenda.util.jpa.Transactional;

public class CancelamentoPedidoService implements Serializable {

	private static final long serialVersionUID = -7630865632469334775L;

	@Inject
	private PedidoRepository pedidoRepository;
	
	@Inject
	private EstoqueService estoqueService;
	
	
	@Transactional
	public Pedido cancelar(Pedido pedido) {
		pedido = pedidoRepository.porId(pedido.getId());
		
		if (isNaoCancelavel(pedido)) {
			throw new NegocioException("Pedido não pode ser cancelado no status " + 
									   pedido.getStatus().getDescricao() + ".");
		}
		
		if (isEmitido(pedido)) {
			estoqueService.retornarItensEstoque(pedido);
		}
		
		pedido.setStatus(StatusPedido.CANCELADO);
		
		pedido = pedidoRepository.guardar(pedido);
		
		return pedido;
	}	
	
	public boolean isEmitido(Pedido pedido) {
		return pedido.isEmitido();
	}

	public boolean isNaoCancelavel(Pedido pedido) {
		return !isCancelavel(pedido);
	}

	public boolean isCancelavel(Pedido pedido) {
		boolean resultado = pedido.isExistente() && !pedido.isCancelado();
		return resultado;
	}
	
	
	
}
