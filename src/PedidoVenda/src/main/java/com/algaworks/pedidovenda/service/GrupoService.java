package com.algaworks.pedidovenda.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import com.algaworks.pedidovenda.model.Grupo;
import com.algaworks.pedidovenda.model.GrupoCad;
import com.algaworks.pedidovenda.repository.GrupoRepository;

public class GrupoService implements Serializable {

	private static final long serialVersionUID = 1480562174286718238L;
	
	@Inject
	private GrupoRepository GrupoRepository;
	
	public List<Grupo> listarGrupos() {
		List<GrupoCad> gruposCadastrados = GrupoRepository.grupos();
		List<Grupo> grupos = new ArrayList<>();
		
		for (GrupoCad grupoCadastrado : gruposCadastrados) {
			Grupo grupo = new Grupo();
			grupo.setId(grupoCadastrado.getId());
			grupo.setNome(grupoCadastrado.getNome());
			grupo.setDescricao(grupoCadastrado.getDescricao());
			
			grupos.add(grupo);
		}
		
		//List<Grupo> grupos = GrupoRepository.grupos();
		
		return grupos;
	}
}
