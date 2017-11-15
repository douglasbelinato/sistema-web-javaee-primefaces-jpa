package com.algaworks.pedidovenda.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.algaworks.pedidovenda.model.Cliente;
import com.algaworks.pedidovenda.repository.filter.ClienteFilter;
import com.algaworks.pedidovenda.service.NegocioException;

public class ClienteRepository implements Serializable {

	private static final long serialVersionUID = -7528126804617273516L;
	
	@Inject
	private EntityManager manager;
	
	/**
	 * Insere e atualiza o cliente
	 * @param usuario
	 * @return
	 */
	public Cliente guardar(Cliente cliente) {
		return manager.merge(cliente);
	}

	/**
	 * Obtém todos os clientes cadastrados de acordo com o filtro
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Cliente> filtrados(ClienteFilter filtro) {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Cliente.class);
		
		if (StringUtils.isNotBlank(filtro.getNome())) {
			// MatchMode.ANYWHERE coloca o % do like antes e depois da palavra
			criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
		}
		
		if (StringUtils.isNotBlank(filtro.getDocumentoReceitaFederal())) {
			// MatchMode.ANYWHERE coloca o % do like antes e depois da palavra
			criteria.add(Restrictions.eq("documentoReceitaFederal", filtro.getDocumentoReceitaFederal()));
		}
		
		return criteria.addOrder(Order.asc("nome")).list();
	}
	
	/**
	 * Exclui o cliente
	 * @param cliente
	 */
	public void remover(Cliente cliente) {
		try {
			cliente = porId(cliente.getId());
			manager.remove(cliente);
			manager.flush();
		} catch (PersistenceException e) {
			throw new NegocioException("Cliente não pode ser excluído.");
		}
	}
	
	/**
	 * Busca um cliente pelo Id
	 * @param id
	 * @return
	 */
	public Cliente porId(Long id) {
		return manager.find(Cliente.class, id);
	}
	
	/**
	 * Busca um cliente pelo nome
	 * @param nome
	 * @return
	 */
	public List<Cliente> porNome(String nome) {
		return manager.createQuery("from Cliente " +
								   "where upper(nome) like :nome", Cliente.class)
					   .setParameter("nome", nome.toUpperCase() + "%")
					   .getResultList();
	}
	
	

}
