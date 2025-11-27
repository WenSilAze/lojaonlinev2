package com.wendel.loja.dao;

import com.wendel.loja.model.Pedido;

import java.sql.*;

public class PedidoDAO {

    public int inserir(Connection conn, Pedido pedido) throws SQLException {
        // Removemos o campo 'data' que não existe na tabela
        String sql = "INSERT INTO pedido (cliente_id, status) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, pedido.getClienteId());
            stmt.setString(2, pedido.getStatus());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1); // retorna o ID do pedido
                }
            }
        }
        throw new SQLException("Falha ao inserir pedido.");
    }
}