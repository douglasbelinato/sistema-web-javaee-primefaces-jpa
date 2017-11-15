package com.algaworks.pedidovenda.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.algaworks.pedidovenda.model.Pedido;
import com.algaworks.pedidovenda.model.StatusPedido;
import com.algaworks.pedidovenda.repository.PedidoRepository;
import com.algaworks.pedidovenda.repository.filter.PedidoFilter;

@Named
@ViewScoped
public class PesquisaPedidosBean implements Serializable {
	
	private static final long serialVersionUID = 5047222135367311021L;
	
	@Inject
	private PedidoRepository pedidoRepository;
	
	private PedidoFilter filtro;
	private List<Pedido> pedidosFiltrados;
	
	public PesquisaPedidosBean() {
		filtro = new PedidoFilter();
		pedidosFiltrados = new ArrayList<>();
	}
	
	public void pesquisar() {
		pedidosFiltrados = pedidoRepository.filtrados(filtro);
	}

	public PedidoFilter getFiltro() {
		return filtro;
	}

	public List<Pedido> getPedidosFiltrados() {
		return pedidosFiltrados;
	}
	
	public StatusPedido[] getStatuses() {
		return StatusPedido.values();
	}

}
