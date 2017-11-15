package com.algaworks.pedidovenda.controller;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;

import com.algaworks.pedidovenda.model.Cliente;
import com.algaworks.pedidovenda.model.EnderecoEntrega;
import com.algaworks.pedidovenda.model.FormaPagamento;
import com.algaworks.pedidovenda.model.ItemPedido;
import com.algaworks.pedidovenda.model.Pedido;
import com.algaworks.pedidovenda.model.Produto;
import com.algaworks.pedidovenda.model.Usuario;
import com.algaworks.pedidovenda.service.CadastroPedidoService;
import com.algaworks.pedidovenda.util.jsf.FacesUtil;
import com.algaworks.pedidovenda.validation.SKU;

@Named
@ViewScoped
public class CadastroPedidoBean implements Serializable {

	private static final long serialVersionUID = -2465024077992049322L;

	@Inject
	private CadastroPedidoService cadastroPedidoService;
	
	@Produces
	@PedidoEdicao
	private Pedido pedido;
	
	private List<Usuario> vendedores;
	
	private Produto produtoLinhaEditavel;
	
	@SKU
	private String sku;

	private List<Produto> listProdutos;
	
	public CadastroPedidoBean() {
		limpar();
	}
	
	public void inicializar() {
		if (FacesUtil.isNotPostback()) {
			vendedores = cadastroPedidoService.carregarVendedores();
			
			if (pedido != null && pedido.getId() != null) {
				pedido = cadastroPedidoService.obterPedidoPorId(pedido.getId());
			}
			
			recalcularPedido();
			adicionarItemVazio();
		}
	}

	private void limpar() {
		pedido = new Pedido();
		pedido.setEnderecoEntrega(new EnderecoEntrega());
	}
	
	public void salvar() {
		removerItemVazio();
		
		try {
			pedido = cadastroPedidoService.salvar(pedido);
			FacesUtil.addInfoMessage("Pedido salvo com sucesso!");
		} finally {
			adicionarItemVazio();
		}
	}
	
	private void adicionarItemVazio() {
		cadastroPedidoService.adicionarItemVazio(pedido);		
	}
	
	private void removerItemVazio() {
		cadastroPedidoService.removerItemVazio(pedido);
	}
	
	public void recalcularPedido() {
		if (pedido != null) {
			cadastroPedidoService.recalcularPedido(pedido);
		}
	}
	
	public List<Cliente> completarCliente(String nome) {
		return cadastroPedidoService.completarCliente(nome);
	}
	
	public List<Produto> completarProduto(String nome) {
		listProdutos = cadastroPedidoService.completarProduto(nome);
		return listProdutos;
	}
	
	public void carregarProdutoPorSku() {
		if (StringUtils.isNotEmpty(sku)) {
			produtoLinhaEditavel = cadastroPedidoService.carregarProdutoPorSku(sku);
			carregarProdutoLinhaEditavel();
		}
	}
	
	public void carregarProdutoLinhaEditavel() {		
		if (produtoLinhaEditavel != null) {
			if (cadastroPedidoService.existeItemProduto(pedido, produtoLinhaEditavel)) {
				FacesUtil.addErrorMessage("Já existe um item no pedido com o produto informado.");
			} else {
				cadastroPedidoService.carregarProdutoLinhaEditavel(pedido, produtoLinhaEditavel);
				produtoLinhaEditavel = null;
				sku = null;
			}
		}
	}
	
	public void atualizarQuantidade(int linha) {
		if (pedido.getItens().size() < 2) {
			adicionarItemVazio();
		}
		ItemPedido item = pedido.getItens().get(linha);
		if (item.getQuantidade() < 1) {
			if (linha == 0) {
				item.setQuantidade(1);
			} else {
				pedido.getItens().remove(item);
			}
		}
		recalcularPedido();		
	}
	
	public void pedidoAlterado(@Observes PedidoAlteradoEvent event) {
		pedido = event.getPedido();
	}
	
	public FormaPagamento[] getFormasPagamento() {
		return FormaPagamento.values();
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public List<Usuario> getVendedores() {
		return vendedores;
	}
	
	public boolean isEditando() {
		return pedido.getId() != null;
	}

	public Produto getProdutoLinhaEditavel() {
		return produtoLinhaEditavel;
	}

	public void setProdutoLinhaEditavel(Produto produtoLinhaEditavel) {
		this.produtoLinhaEditavel = produtoLinhaEditavel;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}
	
	public boolean isNaoEmissivel() {
		return cadastroPedidoService.isNaoEmissivel(pedido);
	}
	
	public boolean isNaoAlteravel() {
		return cadastroPedidoService.isNaoAlteravel(pedido);
	}
	
	public boolean isNaoEnviavelPorEmail() {
		return pedido.isNovo() || pedido.isCancelado();
	}
}
