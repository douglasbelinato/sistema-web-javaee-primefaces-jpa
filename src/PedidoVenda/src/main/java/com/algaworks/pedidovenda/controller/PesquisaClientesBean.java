package com.algaworks.pedidovenda.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.algaworks.pedidovenda.model.Cliente;
import com.algaworks.pedidovenda.repository.filter.ClienteFilter;
import com.algaworks.pedidovenda.service.PesquisaClientesService;
import com.algaworks.pedidovenda.util.jsf.FacesUtil;

@Named
@ViewScoped
public class PesquisaClientesBean implements Serializable {

	private static final long serialVersionUID = -578225829180254044L;

	@Inject
	private PesquisaClientesService pesquisaClientesService;	
	
	private ClienteFilter filtro;

	private List<Cliente> clientesFiltrados;
	
	private Cliente clienteSelecionado;
	

	public PesquisaClientesBean() {
		filtro = new ClienteFilter();
	}
	
	public void pesquisar() {
		clientesFiltrados = pesquisaClientesService.carregarClientes(filtro);
	}
	
	public void excluir() {
		pesquisaClientesService.remover(clienteSelecionado);
		clientesFiltrados.remove(clienteSelecionado);
		
		FacesUtil.addInfoMessage("Cliente " + clienteSelecionado.getNome() + " excluído com sucesso.");
	}
	

	public ClienteFilter getFiltro() {
		return filtro;
	}

	public void setClientesFiltrados(List<Cliente> clientesFiltrados) {
		this.clientesFiltrados = clientesFiltrados;
	}

	public List<Cliente> getClientesFiltrados() {
		return clientesFiltrados;
	}

	public Cliente getClienteSelecionado() {
		return clienteSelecionado;
	}

	public void setClienteSelecionado(Cliente clienteSelecionado) {
		this.clienteSelecionado = clienteSelecionado;
	}

}
