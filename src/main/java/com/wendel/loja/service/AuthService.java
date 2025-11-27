package com.wendel.loja.service;

import com.wendel.loja.dao.ClienteDAO;
import com.wendel.loja.model.Cliente;

public class AuthService {

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