package com.algaworks.pedidovenda.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.algaworks.pedidovenda.model.Cliente;
import com.algaworks.pedidovenda.repository.ClienteRepository;
import com.algaworks.pedidovenda.repository.filter.ClienteFilter;
import com.algaworks.pedidovenda.util.jpa.Transactional;

public class PesquisaClientesService implements Serializable {

	private static final long serialVersionUID = -7037166000752109989L;
	
	@Inject
	private ClienteRepository clienteRepository;
	
	public List<Cliente> carregarClientes(ClienteFilter filtro) {
		return clienteRepository.filtrados(filtro);
	}
	
	@Transactional
	public void remover(Cliente cliente) {
		clienteRepository.remover(cliente);
	}

}
