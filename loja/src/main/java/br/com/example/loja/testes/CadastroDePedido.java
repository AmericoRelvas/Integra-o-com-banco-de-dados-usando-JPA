package br.com.example.loja.testes;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.example.loja.dao.CategoriaDAO;
import br.com.example.loja.dao.ClienteDAO;
import br.com.example.loja.dao.PedidoDAO;
import br.com.example.loja.dao.ProdutoDAO;
import br.com.example.loja.modelo.Categoria;
import br.com.example.loja.modelo.Cliente;
import br.com.example.loja.modelo.ItemPedido;
import br.com.example.loja.modelo.Pedido;
import br.com.example.loja.modelo.Produto;
import br.com.example.loja.util.JPAUtil;
import br.com.example.loja.vo.RelatorioDeVendasVo;

public class CadastroDePedido {

	public static void main(String[] args) {
		
		popularBancoDeDados();
		
		EntityManager em = JPAUtil.getEntityManager();
		ProdutoDAO produtoDao = new ProdutoDAO(em);
		ClienteDAO clienteDao = new ClienteDAO(em);
		
		Produto produto = produtoDao.buscarPorId(1l);
		Cliente cliente = clienteDao.buscarPorId(1l);
		
		em.getTransaction().begin();
		
		Pedido pedido = new Pedido(cliente);
		pedido.adicionarItem(new ItemPedido(10, pedido, produto));
		
		PedidoDAO pedidoDao = new PedidoDAO(em);
		pedidoDao.cadastrar(pedido);
		
		em.getTransaction().commit();
		
		BigDecimal totalVendido = pedidoDao.valorTotalVendido();
		System.out.println("VALOR TOTAL: " +  totalVendido);
		
		List<RelatorioDeVendasVo> relatorio = pedidoDao.relatoriodeVendas();
		relatorio.forEach(System.out::println);
		
	}
	
	private static void popularBancoDeDados() {
		Categoria celulares = new Categoria("CELULARES");
		Produto celular = new Produto("Xiaomi Redmi", "Muito legal", new BigDecimal("800"), celulares);
        
        EntityManager em = JPAUtil.getEntityManager();
        ProdutoDAO produtoDao = new ProdutoDAO(em);
        CategoriaDAO categoriaDao = new CategoriaDAO(em); 
        ClienteDAO clienteDao = new ClienteDAO(em);
        
        Cliente cliente = new Cliente("Rodrigo", "123456");

        em.getTransaction().begin();
        categoriaDao.cadastrar(celulares);
        produtoDao.cadastrar(celular);
        clienteDao.cadastrar(cliente);
        em.getTransaction().commit();
        em.close();
	}
}
