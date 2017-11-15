package com.algaworks.pedidovenda.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.algaworks.pedidovenda.model.Pedido;
import com.algaworks.pedidovenda.repository.PedidoRepository;
import com.algaworks.pedidovenda.util.cdi.CDIServiceLocator;

@FacesConverter(forClass = Pedido.class)
public class PedidoConverter implements Converter {

	// @Inject // Injeção de dependencia não está funcionando até esta versão.
	// Qdo estiver OK, basta usar @Inject ao invés de usar a classe
	// CDIServiceLocator para instanciar o repository.
	private PedidoRepository pedidoRepository;

	public PedidoConverter() {
		pedidoRepository = CDIServiceLocator.getBean(PedidoRepository.class);
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Pedido pedido = null;
		if (value != null && !value.isEmpty()) {
			Long id = new Long(value);
			pedido = pedidoRepository.porId(id);
		}
		return pedido;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Pedido pedido = (Pedido) value;
			return pedido.getId() == null ? null : pedido.getId().toString();
		}
		return "";
	}

}
