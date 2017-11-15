import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.algaworks.pedidovenda.model.Categoria;
import com.algaworks.pedidovenda.model.Cliente;
import com.algaworks.pedidovenda.model.Endereco;
import com.algaworks.pedidovenda.model.EnderecoEntrega;
import com.algaworks.pedidovenda.model.FormaPagamento;
import com.algaworks.pedidovenda.model.Grupo;
import com.algaworks.pedidovenda.model.ItemPedido;
import com.algaworks.pedidovenda.model.Pedido;
import com.algaworks.pedidovenda.model.Produto;
import com.algaworks.pedidovenda.model.StatusPedido;
import com.algaworks.pedidovenda.model.TipoPessoa;
import com.algaworks.pedidovenda.model.Usuario;

public class Teste {
	
	public static void main(String[] args) {
		//insertClienteAndEndereco();
		//insertUsuarioAndGrupo();
		//insertProdutoAndCategoria();
		//insertPedidoVenda();
	}
	
	private static void insertClienteAndEndereco() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("PedidoPU");
		EntityManager manager = factory.createEntityManager();
		
		EntityTransaction trx = manager.getTransaction();
		trx.begin();
		
		Cliente cliente = new Cliente();
		cliente.setNome("João das Couves2");
		cliente.setEmail("joao@dascouves.com");
		cliente.setDocumentoReceitaFederal("123.123.123-12");
		cliente.setTipo(TipoPessoa.FISICA);
		
		Endereco endereco = new Endereco();
		endereco.setLogradouro("Rua das Aboboras Vermelhas");
		endereco.setNumero("111");
		endereco.setCidade("Uberlândia");
		endereco.setUf("MG");
		endereco.setCep("38400-000");
		endereco.setCliente(cliente);
		
		cliente.getEnderecos().add(endereco);
		
		manager.persist(cliente);
		
		trx.commit();
	}
	
	@SuppressWarnings("unused")
	private static void insertUsuarioAndGrupo() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("PedidoPU");
		EntityManager manager = factory.createEntityManager();
				
		EntityTransaction trx = manager.getTransaction();
		trx.begin();
				
		Usuario usuario = new Usuario();
		usuario.setNome("Maria");
		usuario.setEmail("maria@abadia.com");
		usuario.setSenha("123");
				
		Grupo grupo = new Grupo();
		grupo.setNome("Vendedores");
		grupo.setDescricao("Vendedores da empresa");
				
		usuario.getGrupos().add(grupo);
				
		manager.persist(usuario);
			
		trx.commit();
	}
	
	@SuppressWarnings("unused")
	private static void insertProdutoAndCategoria() { 
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("PedidoPU");
		EntityManager manager = factory.createEntityManager();
		
		EntityTransaction trx = manager.getTransaction();
		trx.begin();
		
		// instanciamos a categoria pai (Bebidas)
		Categoria categoriaPai = new Categoria();
		categoriaPai.setDescricao("Bebidas");
		
		// instanciamos a categoria filha (Refrigerantes)
		Categoria categoriaFilha = new Categoria();
		categoriaFilha.setDescricao("Refrigerantes");
		categoriaFilha.setCategoriaPai(categoriaPai);
		
		// adicionamos a categoria Refrigerantes como filha de Bebidas
		categoriaPai.getSubcategorias().add(categoriaFilha);
		
		// ao persistir a categoria pai (Refrigerantes), a filha (Bebidas) 
		// deve ser persistida também
		manager.persist(categoriaPai);
		
		// instanciamos e persistimos um produto
		Produto produto = new Produto();
		produto.setCategoria(categoriaFilha);
		produto.setNome("Guaraná 2L");
		produto.setQuantidadeEstoque(10);
		produto.setSku("GUA00123");
		produto.setValorUnitario(new BigDecimal(2.21));
				
		manager.persist(produto);
		
		trx.commit();
	}
	
	@SuppressWarnings("unused")
	private static void insertPedidoVenda() { 
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("PedidoPU");
		EntityManager manager = factory.createEntityManager();
		
		EntityTransaction trx = manager.getTransaction();
		trx.begin();
		
		Cliente cliente = manager.find(Cliente.class, 1L);
		Produto produto = manager.find(Produto.class, 1L);
		Usuario vendedor = manager.find(Usuario.class, 1L);
		
		EnderecoEntrega enderecoEntrega = new EnderecoEntrega();
		enderecoEntrega.setLogradouro("Rua dos Mercados");
		enderecoEntrega.setNumero("1000");
		enderecoEntrega.setCidade("Uberlândia");
		enderecoEntrega.setUf("MG");
		enderecoEntrega.setCep("38400-123");
		
		Pedido pedido = new Pedido();
		pedido.setCliente(cliente);
		pedido.setDataCriacao(new Date());
		pedido.setDataEntrega(new Date());
		pedido.setFormaPagamento(FormaPagamento.CARTAO_CREDITO);
		pedido.setObservacao("Aberto das 08 às 18h");
		pedido.setStatus(StatusPedido.ORCAMENTO);
		pedido.setValorDesconto(BigDecimal.ZERO);
		pedido.setValorFrete(BigDecimal.ZERO);
		pedido.setValorTotal(new BigDecimal(23.2));
		pedido.setVendedor(vendedor);
		pedido.setEnderecoEntrega(enderecoEntrega);
		
		ItemPedido item = new ItemPedido();
		item.setProduto(produto);
		item.setQuantidade(10);
		item.setValorProduto(new BigDecimal(2.32));
		item.setPedido(pedido);
		
		pedido.getItens().add(item);
		
		manager.persist(pedido);
		
		
		trx.commit();
	}

}
