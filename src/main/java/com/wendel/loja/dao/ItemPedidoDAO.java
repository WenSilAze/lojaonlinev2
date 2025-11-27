package com.wendel.loja.dao;

// importações
import com.wendel.loja.model.ItemPedido;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

// Classe para operações de banco de dados relacionadas a itens de pedido
public class ItemPedidoDAO {

    public void inserir(Connection conn, ItemPedido item, int pedidoId) throws SQLException {
        String sql = "INSERT INTO item_pedido (pedido_id, produto_id, quantidade, subtotal) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, pedidoId);
            stmt.setInt(2, item.getProdutoId());
            stmt.setInt(3, item.getQuantidade());
            stmt.setBigDecimal(4, item.getSubtotal());
            stmt.executeUpdate();
        }
    }
}