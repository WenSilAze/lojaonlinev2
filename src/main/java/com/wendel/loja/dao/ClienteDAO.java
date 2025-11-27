package com.wendel.loja.dao;

import com.wendel.loja.model.Cliente;
import com.wendel.loja.util.Conexao;

import java.sql.*;

public class ClienteDAO {

    // Salvar novo cliente
    public Integer salvar(Cliente cliente) throws SQLException {
        String sql = "INSERT INTO cliente (nome, email, senha, role) VALUES (?, ?, ?, ?)";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getEmail());
            stmt.setString(3, cliente.getSenha());
            stmt.setString(4, cliente.getRole() != null ? cliente.getRole() : "USER");
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return null;
    }

    // Buscar cliente por email
    public Cliente buscarPorEmail(String email) throws SQLException {
        String sql = "SELECT * FROM cliente WHERE email = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Cliente cliente = new Cliente();
                    cliente.setId(rs.getInt("id"));
                    cliente.setNome(rs.getString("nome"));
                    cliente.setEmail(rs.getString("email"));
                    cliente.setSenha(rs.getString("senha"));
                    cliente.setRole(rs.getString("role"));
                    return cliente;
                }
            }
        }
        return null;
    }

    // Autenticar cliente
    public Cliente autenticar(String email, String senha) throws SQLException {
        String sql = "SELECT * FROM cliente WHERE email = ? AND senha = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setString(2, senha);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Cliente cliente = new Cliente();
                    cliente.setId(rs.getInt("id"));
                    cliente.setNome(rs.getString("nome"));
                    cliente.setEmail(rs.getString("email"));
                    cliente.setSenha(rs.getString("senha"));
                    cliente.setRole(rs.getString("role"));
                    return cliente;
                }
            }
        }
        return null;
    }
}