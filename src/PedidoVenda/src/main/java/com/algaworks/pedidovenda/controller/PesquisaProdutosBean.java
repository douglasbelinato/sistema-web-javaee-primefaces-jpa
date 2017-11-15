package com.algaworks.pedidovenda.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.algaworks.pedidovenda.model.Produto;
import com.algaworks.pedidovenda.repository.ProdutoRepository;
import com.algaworks.pedidovenda.repository.filter.ProdutoFilter;
import com.algaworks.pedidovenda.service.CadastroProdutoService;
import com.algaworks.pedidovenda.util.jsf.FacesUtil;

@Named
@ViewScoped
public class PesquisaProdutosBean implements Serializable {
	
	private static final long serialVersionUID = 5426987901422639190L;
	
	private List<Produto> produtosFiltrados;
	
	private Produto produtoSelecionado;
	
	@Inject
	private ProdutoRepository produtoRepository;
	
	@Inject
	private CadastroProdutoService cadastroProdutoService;
	
	private ProdutoFilter produtoFilter;
	
	public PesquisaProdutosBean() {
		produtoFilter = new ProdutoFilter();
	}
	
	public void pesquisar() {
		produtosFiltrados = produtoRepository.filtrados(produtoFilter);
	}
	
	public void excluir() {
		cadastroProdutoService.remover(produtoSelecionado);
		produtosFiltrados.remove(produtoSelecionado);
		
		FacesUtil.addInfoMessage("Produto " + produtoSelecionado.getSku() + " excluído com sucesso.");
	}
	
	public List<Produto> getProdutosFiltrados() {
		return produtosFiltrados;
	}

	public ProdutoFilter getProdutoFilter() {
		return produtoFilter;
	}
	
	public Produto getProdutoSelecionado() {
		return produtoSelecionado;
	}

	public void setProdutoSelecionado(Produto produtoSelecionado) {
		this.produtoSelecionado = produtoSelecionado;
	}

}
