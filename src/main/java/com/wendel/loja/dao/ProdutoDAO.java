package com.wendel.loja.dao;

// importações
import com.wendel.loja.model.Produto;
import com.wendel.loja.util.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Classe para operações de banco de dados relacionadas a produtos
public class ProdutoDAO {

    // Listar todos os produtos
    public List<Produto> listar() throws SQLException {
        List<Produto> list = new ArrayList<>();
        String sql = "SELECT * FROM produto ORDER BY nome";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Produto(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getBigDecimal("preco"),
                        rs.getInt("estoque")
                ));
            }
        }
        return list;
    }

    // Buscar produto por ID
    public Produto buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM produto WHERE id = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Produto(
                            rs.getInt("id"),
                            rs.getString("nome"),
                            rs.getBigDecimal("preco"),
                            rs.getInt("estoque")
                    );
                }
            }
        }
        return null;
    }

    // Salvar novo produto
    public void salvar(Produto p) throws SQLException {
        String sql = "INSERT INTO produto (nome, preco, estoque) VALUES (?, ?, ?)";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, p.getNome());
            ps.setBigDecimal(2, p.getPreco());
            ps.setInt(3, p.getEstoque());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    p.setId(rs.getInt(1));
                }
            }
        }
    }

    // Atualizar produto existente
    public void atualizar(Produto p) throws SQLException {
        String sql = "UPDATE produto SET nome = ?, preco = ?, estoque = ? WHERE id = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getNome());
            ps.setBigDecimal(2, p.getPreco());
            ps.setInt(3, p.getEstoque());
            ps.setInt(4, p.getId());
            ps.executeUpdate();
        }
    }

    // Remover produto pelo ID
    public void remover(int id) throws SQLException {
        String sql = "DELETE FROM produto WHERE id = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    // Diminuir estoque (usado em pedidos)
    public void diminuirEstoque(Connection conn, int produtoId, int quantidade) throws SQLException {
        String sql = "UPDATE produto SET estoque = estoque - ? WHERE id = ? AND estoque >= ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, quantidade);
            ps.setInt(2, produtoId);
            ps.setInt(3, quantidade);
            int rows = ps.executeUpdate();
            if (rows == 0) {
                throw new SQLException("Estoque insuficiente para produto id " + produtoId);
            }
        }
    }
}