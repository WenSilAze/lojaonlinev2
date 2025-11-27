package com.wendel.loja.model;

import java.math.BigDecimal;

public class Produto {
    private Integer id;
    private String nome;
    private BigDecimal preco;
    private int estoque;

    // Construtor vazio (necessário para AdminProdutoView)
    public Produto() {}

    // Construtor completo
    public Produto(Integer id, String nome, BigDecimal preco, int estoque) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.estoque = estoque;
    }

    // Getters e Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public BigDecimal getPreco() { return preco; }
    public void setPreco(BigDecimal preco) { this.preco = preco; }

    public int getEstoque() { return estoque; }
    public void setEstoque(int estoque) { this.estoque = estoque; }
}