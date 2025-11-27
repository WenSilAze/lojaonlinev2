package com.wendel.loja.service;

import com.wendel.loja.dao.ItemPedidoDAO;
import com.wendel.loja.dao.PedidoDAO;
import com.wendel.loja.dao.ProdutoDAO;
import com.wendel.loja.model.ItemPedido;
import com.wendel.loja.model.Pedido;
import com.wendel.loja.util.Conexao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class PedidoService {

    private final PedidoDAO pedidoDAO = new PedidoDAO();
    private final ItemPedidoDAO itemDAO = new ItemPedidoDAO();
    private final ProdutoDAO produtoDAO = new ProdutoDAO();

    public int fecharPedido(int clienteId, List<ItemPedido> itens) throws SQLException {
        try (Connection conn = Conexao.getConnection()) {
            try {
                conn.setAutoCommit(false);

                // cria pedido com status "ABERTO"
                Pedido pedido = new Pedido(clienteId); // status já definido no construtor
                int pedidoId = pedidoDAO.inserir(conn, pedido);

                // insere itens e atualiza estoque
                for (ItemPedido item : itens) {
                    produtoDAO.diminuirEstoque(conn, item.getProdutoId(), item.getQuantidade());
                    itemDAO.inserir(conn, item, pedidoId);
                }

                conn.commit();
                return pedidoId;
            } catch (Exception e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        }
    }
}