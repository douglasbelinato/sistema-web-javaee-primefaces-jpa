package com.algaworks.pedidovenda.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.algaworks.pedidovenda.model.Categoria;

public class CategoriaRepository implements Serializable {

	private static final long serialVersionUID = 5623168357648200433L;
	
	@Inject
	private EntityManager manager;
	
	public Categoria porId(Long id) {
		return manager.find(Categoria.class, id);
	}
	
	public List<Categoria> raizes() {
		return manager.createQuery("from Categoria where categoriaPai is null", Categoria.class).getResultList();
	}
	
	public List<Categoria> subcategoriasDe(Categoria categoriaPai) {
		return manager.createQuery("from Categoria where categoriaPai = :raiz", Categoria.class)
				.setParameter("raiz", categoriaPai)
				.getResultList();
	}

}
