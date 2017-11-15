package com.algaworks.pedidovenda.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.algaworks.pedidovenda.model.Produto;
import com.algaworks.pedidovenda.repository.ProdutoRepository;
import com.algaworks.pedidovenda.util.cdi.CDIServiceLocator;

@FacesConverter(forClass = Produto.class)
public class ProdutoConverter implements Converter {

	// @Inject // Injeção de dependencia não está funcionando até esta versão.
	// Qdo estiver OK, basta usar @Inject ao invés de usar a classe
	// CDIServiceLocator para instanciar o repository.
	private ProdutoRepository produtoRepository;

	public ProdutoConverter() {
		produtoRepository = CDIServiceLocator.getBean(ProdutoRepository.class);
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Produto produto = null;
		if (value != null && !value.isEmpty()) {
			Long id = new Long(value);
			produto = produtoRepository.porId(id);
		}
		return produto;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Produto produto = (Produto) value;
			return produto.getId() == null ? null : produto.getId().toString();
		}
		return "";
	}

}
