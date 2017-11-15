package com.algaworks.pedidovenda.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.algaworks.pedidovenda.model.Usuario;
import com.algaworks.pedidovenda.repository.UsuarioRepository;
import com.algaworks.pedidovenda.repository.filter.UsuarioFilter;
import com.algaworks.pedidovenda.util.jpa.Transactional;

public class PesquisaUsuariosService implements Serializable {
	
	private static final long serialVersionUID = 2057115206135715090L;
	
	@Inject
	private UsuarioRepository usuarioRepository;
	
	@Transactional
	public List<Usuario> carregarUsuarios(UsuarioFilter filtro) {
		return usuarioRepository.filtrados(filtro);
	}
	
	@Transactional
	public void remover(Usuario usuario) {
		usuarioRepository.remover(usuario);
	}

}
