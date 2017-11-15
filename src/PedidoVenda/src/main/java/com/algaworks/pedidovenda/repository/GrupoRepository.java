package com.algaworks.pedidovenda.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.algaworks.pedidovenda.model.Grupo;
import com.algaworks.pedidovenda.model.GrupoCad;

public class GrupoRepository implements Serializable {

	private static final long serialVersionUID = 1551577066937019645L;
	
	@Inject
	private EntityManager manager;
	
	public Grupo porId(Long id) {
		return manager.find(Grupo.class, id);
	}
	
	//public List<Grupo> grupos() {
	public List<GrupoCad> grupos() {
		return manager.createQuery("from GrupoCad", GrupoCad.class).getResultList();
		//return manager.createQuery("from Grupo", Grupo.class).getResultList();
	}

}
