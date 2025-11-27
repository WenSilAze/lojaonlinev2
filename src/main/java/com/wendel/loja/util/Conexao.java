package com.wendel.loja.util;

// importações
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Classe para gerenciar conexões com o banco de dados
public class Conexao {
    private static final String URL = "jdbc:mysql://localhost:3306/lojaonlinev2?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "root"; // User do seu banco de dados
    private static final String PASSWORD = ""; // Senha do seu banco de dados

    // Método para obter conexão com o banco de dados
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}