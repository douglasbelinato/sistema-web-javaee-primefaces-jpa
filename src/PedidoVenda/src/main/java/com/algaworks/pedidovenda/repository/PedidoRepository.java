package com.algaworks.pedidovenda.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.algaworks.pedidovenda.model.Pedido;
import com.algaworks.pedidovenda.repository.filter.PedidoFilter;

public class PedidoRepository implements Serializable {

	private static final long serialVersionUID = -1466612638864832087L;
	
	@Inject
	private EntityManager manager;
	
	@SuppressWarnings({ "unchecked", "unused" })
	public List<Pedido> filtrados(PedidoFilter filtro) {
		Session session = manager.unwrap(Session.class);
		
		Criteria criteria = session.createCriteria(Pedido.class)
									// fazemos uma associação (join) com cliente e nomeamos como "c"
									.createAlias("cliente", "c")
									// fazemos uma associação (join) com vendedor e nomeamos como "v"
									.createAlias("vendedor", "v");
		
		if (filtro.getNumeroDe() != null) {
			// id deve ser maior ou igual (great or equal) ao filtro.getNumeroDe()
			criteria.add(Restrictions.ge("id", filtro.getNumeroDe()));
		}
		
		if (filtro.getNumeroAte() != null) {
			// id deve ser menor ou igual (less or equal) ao filtro.getNumeroAte()
			criteria.add(Restrictions.le("id", filtro.getNumeroAte()));
		}
		
		if (filtro.getDataCriacaoDe() != null) {
			// id deve ser maior ou igual (great or equal) ao filtro.getDataCriacaoDe()
			criteria.add(Restrictions.ge("dataCriacao", filtro.getDataCriacaoDe()));
		}
		
		if (filtro.getDataCriacaoAte() != null) {
			// id deve ser menor ou igual (less or equal) ao filtro.getDataCriacaoAte()
			criteria.add(Restrictions.le("dataCriacao", filtro.getDataCriacaoAte()));
		}
		
		if (StringUtils.isNotBlank(filtro.getNomeCliente())) {
			// acessamos o nome do cliente associado ao pedido pelo alias "c", criado anteriormente
			criteria.add(Restrictions.ilike("c.nome", filtro.getNomeCliente(), MatchMode.ANYWHERE));
		}
		
		if (StringUtils.isNotBlank(filtro.getNomeVendedor())) {
			// acessamos o nome do vendedor associado ao pedido pelo alias "v", criado anteriormente
			criteria.add(Restrictions.ilike("v.nome", filtro.getNomeVendedor(), MatchMode.ANYWHERE));
		}
		
		if (filtro.getStatuses() != null && filtro.getStatuses().length > 0) {
			// adicionamos uma restrição "in" passando um array de constantes da enum StatusPedido
			criteria.add(Restrictions.in("status", filtro.getStatuses()));
		}
		
		return criteria.addOrder(Order.asc("id")).list();
		
	}

	public Pedido guardar(Pedido pedido) {
		return manager.merge(pedido);
	}

	public Pedido porId(Long id) {
		return manager.find(Pedido.class, id);
	}

}
