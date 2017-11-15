package com.algaworks.pedidovenda.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.algaworks.pedidovenda.model.Grupo;
import com.algaworks.pedidovenda.model.Usuario;
import com.algaworks.pedidovenda.service.CadastroUsuarioService;
import com.algaworks.pedidovenda.service.GrupoService;
import com.algaworks.pedidovenda.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroUsuarioBean implements Serializable {

	private static final long serialVersionUID = 6305189367723406894L;

	@Inject
	private CadastroUsuarioService cadastroUsuarioService;
	
	@Inject
	private GrupoService grupoService;
	
	private List<Grupo> grupos;
	
	private Grupo grupoSelecionado;
	
	private Usuario usuario;
	
	public CadastroUsuarioBean() {
		limpar();
	}
	
	public void teste() {
		System.out.println("Testando...");
	}
	
	public void inicializar() {
		if (FacesUtil.isNotPostback()) {			
			carregarGrupos();
		}
	}

	public void limpar() {
		usuario = new Usuario();
	}	
	
	public void carregarGrupos() {
		grupos = grupoService.listarGrupos();		
	}
	
	public void adicionarGrupo() {
		usuario.getGrupos().add(grupoSelecionado);
	}
	
	public void removerGrupo() {
		usuario.getGrupos().remove(grupoSelecionado);
	}
	
	public void salvar() {
		usuario = cadastroUsuarioService.salvar(usuario);
		limpar();
		
		FacesUtil.addInfoMessage("Usuário salvo com sucesso!");
	}
	
	public boolean isEditando() {
		return this.usuario.getId() != null;
	}

	public List<Grupo> getGrupos() {
		// Tratamento para que só seja exibido no combo os grupos que ainda não
		// foram adicionados para o usuário
		List<Grupo> gruposCombo = new ArrayList<>();
		
		for (Grupo grupo : grupos) {
			if (!usuario.getGrupos().contains(grupo)) {
				gruposCombo.add(grupo);
			}
		}
		
		return gruposCombo;
	}

	public Usuario getUsuario() {
		return usuario;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Grupo getGrupoSelecionado() {
		return grupoSelecionado;
	}

	public void setGrupoSelecionado(Grupo grupoSelecionado) {
		this.grupoSelecionado = grupoSelecionado;
	}
	
}
