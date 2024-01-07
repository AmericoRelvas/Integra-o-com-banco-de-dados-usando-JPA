package br.com.example.loja.testes;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.example.loja.dao.CategoriaDAO;
import br.com.example.loja.dao.ProdutoDAO;
import br.com.example.loja.modelo.Categoria;
import br.com.example.loja.modelo.CategoriaId;
import br.com.example.loja.modelo.Produto;
import br.com.example.loja.util.JPAUtil;

public class CadastroDeProduto {

	public static void main(String[] args) {
		
		cadastrarProduto();
		Long id = 1l;
		
		EntityManager em = JPAUtil.getEntityManager();
        ProdutoDAO produtoDao = new ProdutoDAO(em);
        
        Produto p = produtoDao.buscarPorId(1l);
        System.out.println(p.getPreco());
        
        List<Produto> todos = produtoDao.buscarTodos();
        todos.forEach(p2 -> System.out.println(p2.getNome()));
        
        BigDecimal precoDoProduto = produtoDao.buscarPrecoDoProdutoComNome("Xiaomi");
        System.out.println("Preco do Produto: " +precoDoProduto);
	}

	private static void cadastrarProduto() {
		Categoria celulares = new Categoria("CELULARES");
		Produto celular = new Produto("Xiaomi Redmi", "Muito legal", new BigDecimal("800"), celulares);
        
        EntityManager em = JPAUtil.getEntityManager();
        ProdutoDAO produtoDao = new ProdutoDAO(em);
        CategoriaDAO categoriaDao = new CategoriaDAO(em); 

        em.getTransaction().begin();
        categoriaDao.cadastrar(celulares);
        produtoDao.cadastrar(celular);
        em.getTransaction().commit();
        
        em.find(Categoria.class, new CategoriaId("CELULARES", "xpto"));
        em.close();
	}
}
