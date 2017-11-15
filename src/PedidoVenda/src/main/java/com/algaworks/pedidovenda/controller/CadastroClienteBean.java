package com.algaworks.pedidovenda.controller;

import java.io.Serializable;

import javax.enterprise.inject.Produces;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.algaworks.pedidovenda.model.Cliente;
import com.algaworks.pedidovenda.model.Endereco;
import com.algaworks.pedidovenda.model.TipoPessoa;
import com.algaworks.pedidovenda.service.CadastroClienteService;
import com.algaworks.pedidovenda.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroClienteBean implements Serializable {
	
	private static final long serialVersionUID = 4555765741450837359L;

	@Inject
	private CadastroClienteService cadastroClienteService;
	
	@Produces
	@ClienteEdicao
	private Cliente cliente;
	
	private Endereco endereco;
	
	private TipoPessoa tipoPessoa;
	
	public CadastroClienteBean() {		
		limpar();
	}
	
	public void salvar() {	
		cadastroClienteService.salvar(cliente);
		limpar();		
		FacesUtil.addInfoMessage("Cliente salvo com sucesso!");
	}
	
	public void adicionarEndereco() {
		if (endereco.getCliente() == null) {
			endereco.setCliente(cliente);
			cliente.getEnderecos().add(endereco);
		}
		endereco = new Endereco();
	}
	
	public void removerEndereco() {		
		cliente.getEnderecos().remove(endereco);
	}
	
	public void limpar() {
		cliente = new Cliente();
		endereco = new Endereco();
	}	

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Endereco getEndereco() {
		return endereco;
	}
	
	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	@SuppressWarnings("static-access")
	public TipoPessoa[] getTipoPessoa() {
		return tipoPessoa.values();
	}
	
	public boolean isEditando() {
		return cliente.getId() != null;
	}
	
	public boolean isNaoEnviavelPorEmail() {
		return cliente.getId() == null;
	}

}
