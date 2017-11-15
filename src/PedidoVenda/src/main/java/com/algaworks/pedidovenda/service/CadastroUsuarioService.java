package com.algaworks.pedidovenda.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.algaworks.pedidovenda.model.Usuario;
import com.algaworks.pedidovenda.repository.UsuarioRepository;
import com.algaworks.pedidovenda.util.jpa.Transactional;

public class CadastroUsuarioService implements Serializable {

	private static final long serialVersionUID = -1090456178960374353L;
		
	@Inject
	private UsuarioRepository usuarioRepository;
		
	@Transactional
	public Usuario salvar(Usuario usuario) {
		return usuarioRepository.guardar(usuario);		
	}

}
