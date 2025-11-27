package com.wendel.loja.service;

// importações
import com.wendel.loja.dao.ClienteDAO;
import com.wendel.loja.model.Cliente;

// Classe para serviços de autenticação e registro de clientes
public class AuthService {

    // DAO de Cliente
    private final ClienteDAO clienteDAO = new ClienteDAO();

    // Login com email e senha
    public Cliente login(String email, String senha) throws Exception {
        Cliente cliente = clienteDAO.autenticar(email, senha);
        return cliente; // retorna Cliente completo (id, nome, email, senha, role)
    }

    // Registrar novo cliente
    public Cliente registrar(String nome, String email, String senha) throws Exception {
        Cliente novo = new Cliente(null, nome, email, senha, "USER"); // default USER
        Integer id = clienteDAO.salvar(novo);
        novo.setId(id);
        return novo;
    }
}