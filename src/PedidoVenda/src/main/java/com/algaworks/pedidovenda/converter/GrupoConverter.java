package com.algaworks.pedidovenda.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import com.algaworks.pedidovenda.model.Grupo;
import com.algaworks.pedidovenda.repository.GrupoRepository;
import com.algaworks.pedidovenda.util.cdi.CDIServiceLocator;

@FacesConverter(forClass = Grupo.class)
public class GrupoConverter implements Converter {
	
	@Inject
	private GrupoRepository grupoRepository;
	
	public GrupoConverter() {
		grupoRepository = CDIServiceLocator.getBean(GrupoRepository.class);
	}

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		Grupo grupo = null;
		if (StringUtils.isNotBlank(value)) {
			//try {
				Long id = new Long(value);
				grupo = grupoRepository.porId(id);
			/*} catch(Exception e) {
				
			}*/			
		}
		return grupo;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object value) {
		if (value != null) {
			Grupo grupo = (Grupo) value;
			return grupo.getId() == null ? null : grupo.getId().toString();			
		}
		return "";
	}

}
