package com.algaworks.pedidovenda.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.algaworks.pedidovenda.model.Usuario;
import com.algaworks.pedidovenda.repository.UsuarioRepository;
import com.algaworks.pedidovenda.util.cdi.CDIServiceLocator;

@FacesConverter(forClass = Usuario.class)
public class UsuarioConverter implements Converter {

	private UsuarioRepository usuarioRepository;

	public UsuarioConverter() {
		usuarioRepository = CDIServiceLocator.getBean(UsuarioRepository.class);
	}

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		Usuario usuario = null;
		if (value != null & !value.isEmpty()) {
			Long id = new Long(value);
			usuario = usuarioRepository.porId(id);
		}
		return usuario;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object value) {
		if (value != null) {
			Usuario usuario = (Usuario) value;
			return usuario.getId() == null ? null : usuario.getId().toString();
		}
		return "";
	}

}
