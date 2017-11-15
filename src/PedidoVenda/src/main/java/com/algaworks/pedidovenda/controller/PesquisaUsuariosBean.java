package com.algaworks.pedidovenda.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.algaworks.pedidovenda.model.Usuario;
import com.algaworks.pedidovenda.repository.filter.UsuarioFilter;
import com.algaworks.pedidovenda.service.PesquisaUsuariosService;
import com.algaworks.pedidovenda.util.jsf.FacesUtil;

@Named
@ViewScoped
public class PesquisaUsuariosBean implements Serializable {

	private static final long serialVersionUID = -4069905600150350877L;

	@Inject
	private PesquisaUsuariosService pesquisaUsuariosService;
	
	private UsuarioFilter filtro;
	
	private List<Usuario> usuariosFiltrados;
	
	private Usuario usuarioSelecionado;
	
	public PesquisaUsuariosBean() {
		filtro = new UsuarioFilter();		
	}
	
	
	public void pesquisar() {
		usuariosFiltrados = pesquisaUsuariosService.carregarUsuarios(filtro);
	}
	
	public void excluir() {
		pesquisaUsuariosService.remover(usuarioSelecionado);
		usuariosFiltrados.remove(usuarioSelecionado);
		
		FacesUtil.addInfoMessage("Usuário " + usuarioSelecionado.getNome() + " excluído com sucesso.");
	}
	
	

	public List<Usuario> getUsuariosFiltrados() {
		return usuariosFiltrados;
	}

	public UsuarioFilter getFiltro() {
		return filtro;
	}

	public Usuario getUsuarioSelecionado() {
		return usuarioSelecionado;
	}

	public void setUsuarioSelecionado(Usuario usuarioSelecionado) {
		this.usuarioSelecionado = usuarioSelecionado;
	}
}
