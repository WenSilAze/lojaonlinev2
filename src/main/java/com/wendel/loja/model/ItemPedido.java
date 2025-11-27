package com.wendel.loja.model;

import java.math.BigDecimal;

public class ItemPedido {
    private Integer produtoId;
    private String produtoNome;
    private int quantidade;
    private BigDecimal subtotal;

    public ItemPedido(Integer produtoId, int quantidade, String produtoNome, BigDecimal subtotal) {
        this.produtoId = produtoId;
        this.quantidade = quantidade;
        this.produtoNome = produtoNome;
        this.subtotal = subtotal;
    }

    // Getters e setters
    public Integer getProdutoId() { return produtoId; }
    public void setProdutoId(Integer produtoId) { this.produtoId = produtoId; }

    public String getProdutoNome() { return produtoNome; }
    public void setProdutoNome(String produtoNome) { this.produtoNome = produtoNome; }

    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }

    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }
}