package com.algaworks.pedidovenda.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;

import com.algaworks.pedidovenda.model.Cliente;
import com.algaworks.pedidovenda.repository.ClienteRepository;
import com.algaworks.pedidovenda.util.cdi.CDIServiceLocator;

@FacesConverter(forClass = Cliente.class)
public class ClienteConverter implements Converter {
	
	private ClienteRepository clienteRepository; 
	
	public ClienteConverter() {
		clienteRepository = CDIServiceLocator.getBean(ClienteRepository.class);
	}

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		Cliente cliente = null;
		if (StringUtils.isNotBlank(value)) {
			Long id = new Long(value);
			cliente = clienteRepository.porId(id);
		}
		return cliente;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object value) {
		if (value != null) {
			Cliente cliente = (Cliente) value;
			return cliente.getId() == null ? null : cliente.getId().toString();
		}
		return null;
	}

}
