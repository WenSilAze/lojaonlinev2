package com.wendel.loja.service;

import com.wendel.loja.dao.ProdutoDAO;
import com.wendel.loja.model.Produto;

import java.util.List;

public class ProdutoService {
    private final ProdutoDAO produtoDAO = new ProdutoDAO();

    // Listar todos os produtos
    public List<Produto> listar() throws Exception {
        return produtoDAO.listar();
    }

    // Salvar novo produto
    public void salvar(Produto produto) throws Exception {
        produtoDAO.salvar(produto);
    }

    // Atualizar produto existente
    public void atualizar(Produto produto) throws Exception {
        produtoDAO.atualizar(produto);
    }

    // Remover produto pelo ID
    public void remover(Integer id) throws Exception {
        produtoDAO.remover(id);
    }
}