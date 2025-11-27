package com.wendel.loja.model;

// importações
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// Classe que representa um pedido
public class Pedido {
    private Integer id;
    private Integer clienteId;
    private LocalDateTime dataPedido; // preenchido pelo banco
    private String status;
    private List<ItemPedido> itens = new ArrayList<>();

    // Construtor completo (usado ao carregar do banco)
    public Pedido(Integer id, Integer clienteId, LocalDateTime dataPedido, String status) {
        this.id = id;
        this.clienteId = clienteId;
        this.dataPedido = dataPedido;
        this.status = status;
    }

    // Construtor usado ao criar novo pedido
    public Pedido(Integer clienteId) {
        this.clienteId = clienteId;
        this.status = "ABERTO";
    }

    // Getters
    public Integer getId() { return id; }
    public Integer getClienteId() { return clienteId; }
    public LocalDateTime getDataPedido() { return dataPedido; }
    public String getStatus() { return status; }
    public List<ItemPedido> getItens() { return itens; }

    // Setters
    public void setId(Integer id) { this.id = id; }
    public void setClienteId(Integer clienteId) { this.clienteId = clienteId; }
    public void setDataPedido(LocalDateTime dataPedido) { this.dataPedido = dataPedido; }
    public void setStatus(String status) { this.status = status; }

    // Adicionar item
    public void addItem(ItemPedido item) {
        itens.add(item);
    }
}