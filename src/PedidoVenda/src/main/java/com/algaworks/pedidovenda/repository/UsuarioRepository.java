package com.algaworks.pedidovenda.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.algaworks.pedidovenda.model.Usuario;
import com.algaworks.pedidovenda.repository.filter.UsuarioFilter;
import com.algaworks.pedidovenda.service.NegocioException;

public class UsuarioRepository implements Serializable {
	
	private static final long serialVersionUID = -2504278181141124586L;
	
	@Inject
	private EntityManager manager;
	
	/**
	 * Insere e atualiza o usuário
	 * @param usuario
	 * @return
	 */
	public Usuario guardar(Usuario usuario) {
		return manager.merge(usuario);
	}

	/**
	 * Obtém todos os usuários cadastrados de acordo com o filtro
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Usuario> filtrados(UsuarioFilter filtro) {		
		// Session funciona apenas para implementação de JPA via Hibernate
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Usuario.class);
		
		if (StringUtils.isNotBlank(filtro.getNome())) {
			// MatchMode.ANYWHERE coloca o % do like antes e depois da palavra
			criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
		}
		
		return criteria.addOrder(Order.asc("nome")).list();
	}
	
	/**
	 * Exclui o usuário
	 * @param cliente
	 */
	public void remover(Usuario usuario) {
		try {
			usuario = porId(usuario.getId());
			manager.remove(usuario);
			manager.flush();
		} catch (PersistenceException e) {
			throw new NegocioException("Usuário não pode ser excluido.");
		}
	}

	/**
	 * Busca um usuário pelo Id
	 * @param id
	 * @return
	 */
	public Usuario porId(Long id) {
		return manager.find(Usuario.class, id);
	}
	
	public List<Usuario> vendedores() {
		return manager.createQuery("from Usuario", Usuario.class)
					  .getResultList();
	}

	public Usuario porEmail(String email) {
		Usuario usuario = null;
		
		try {
			usuario = manager.createQuery("from Usuario where lower(email) = :email",Usuario.class)
							 .setParameter("email", email.toLowerCase())
							 .getSingleResult();
		} catch (NoResultException e) {
			// nenhum usuário com o e-mail informado
		}
		
		return usuario;
	}

}
