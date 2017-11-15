package com.algaworks.pedidovenda.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import com.algaworks.pedidovenda.model.Cliente;
import com.algaworks.pedidovenda.model.ItemPedido;
import com.algaworks.pedidovenda.model.Pedido;
import com.algaworks.pedidovenda.model.Produto;
import com.algaworks.pedidovenda.model.StatusPedido;
import com.algaworks.pedidovenda.model.Usuario;
import com.algaworks.pedidovenda.repository.ClienteRepository;
import com.algaworks.pedidovenda.repository.PedidoRepository;
import com.algaworks.pedidovenda.repository.ProdutoRepository;
import com.algaworks.pedidovenda.repository.UsuarioRepository;
import com.algaworks.pedidovenda.util.jpa.Transactional;

public class CadastroPedidoService implements Serializable {

	private static final long serialVersionUID = -7687252549175828086L;
	
	@Inject
	private UsuarioRepository usuarioReposiry;
	
	@Inject
	private ClienteRepository clienteRepository;
	
	@Inject 
	private PedidoRepository pedidoRepository;
	
	@Inject
	private ProdutoRepository produtoRepository;
	
	public List<Usuario> carregarVendedores() {
		return usuarioReposiry.vendedores();
	}
	
	public List<Cliente> completarCliente(String nome) {
		return clienteRepository.porNome(nome);
	}
	
	@Transactional
	public Pedido salvar(Pedido pedido) {
		if (pedido.isNovo()) {
			pedido.setDataCriacao(new Date());
			pedido.setStatus(StatusPedido.ORCAMENTO);
		}		
		
		recalcularPedido(pedido);
		
		if (isNaoAlteravel(pedido)) {
			throw new NegocioException("Pedido não pode ser alterado no status " +
									   pedido.getStatus().getDescricao() + ".");
		}
		
		if (pedido.getItens().isEmpty()) {
			throw new NegocioException("O pedido deve possuir pelo menos um item.");
		}
		
		if (isValorTotalNegativo(pedido)) {
			throw new NegocioException("Valor total do pedido não pode ser negativo.");
		}
		
		return pedidoRepository.guardar(pedido);
	}

	private boolean isValorTotalNegativo(Pedido pedido) {
		return pedido.getValorTotal().compareTo(BigDecimal.ZERO) < 0;
	}

	public void recalcularPedido(Pedido pedido) {
		calcularPedidoValorTotal(pedido);
		calcularPedidoValorSubTotal(pedido);
	}
	
	public void calcularPedidoValorTotal(Pedido pedido) {
		BigDecimal total = new BigDecimal(0);		
		total = total.add(pedido.getValorFrete()).subtract(pedido.getValorDesconto());
		
		for (ItemPedido item : pedido.getItens()) {
			if (item.getProduto() != null && item.getProduto().getId() != null) { // codição apenas para inicio dos testes
				total = total.add(item.getValorTotal());
			}
		}
		
		pedido.setValorTotal(total);
	}
	
	public void calcularPedidoValorSubTotal(Pedido pedido) {
		BigDecimal valorSubTotal = new BigDecimal(0);
		valorSubTotal = pedido.getValorTotal().subtract(pedido.getValorFrete()).add(pedido.getValorDesconto());
		
		pedido.setValorSubTotal(valorSubTotal);
	}
	
	public void adicionarItemVazio(Pedido pedido) {
		if (isOrcamento(pedido)) {
			Produto produto = new Produto();
			
			ItemPedido item = new ItemPedido();
			item.setProduto(produto);
			item.setPedido(pedido);
			
			pedido.getItens().add(0, item);
		}
	}
	
	public void removerItemVazio(Pedido pedido) {
		ItemPedido item = pedido.getItens().get(0);
		
		if (item != null && item.getProduto().getId() == null) {
			pedido.getItens().remove(0);
		}
	}

	private boolean isOrcamento(Pedido pedido) {
		return pedido.getStatus().equals(StatusPedido.ORCAMENTO);
	}
	
	public List<Produto> completarProduto(String nome) {
		return produtoRepository.porNome(nome);
	}
	
	public void carregarProdutoLinhaEditavel(Pedido pedido, Produto produtoLinhaEditavel) {
		ItemPedido item = pedido.getItens().get(0);
		item.setProduto(produtoLinhaEditavel);
		item.setValorProduto(produtoLinhaEditavel.getValorUnitario());
		
		adicionarItemVazio(pedido);
		recalcularPedido(pedido);
	}
	
	public Produto carregarProdutoPorSku(String sku) {
		return produtoRepository.porSku(sku);
	}

	public boolean existeItemProduto(Pedido pedido, Produto produto) {
		boolean existeItem = false;
		
		for (ItemPedido item : pedido.getItens()) {
			if (produto.equals(item.getProduto())) {
				existeItem = true;
				break;
			}
		}
		
		return existeItem;
	}
		
	public boolean isNaoEmissivel(Pedido pedido) {		
		return !isEmissivel(pedido);
	}
	
	public boolean isEmissivel(Pedido pedido) {
		return pedido.isExistente() && isOrcamento(pedido);
	}
	
	public Pedido obterPedidoPorId(Long id) {
		return pedidoRepository.porId(id);
	}
	
	public boolean isNaoAlteravel(Pedido pedido) {
		return !isAlteravel(pedido);
	}

	public boolean isAlteravel(Pedido pedido) {
		return isOrcamento(pedido);
	}

}
