package com.wendel.loja.view;

import com.wendel.loja.model.Cliente;
import com.wendel.loja.model.ItemPedido;
import com.wendel.loja.model.Produto;
import com.wendel.loja.service.PedidoService;
import com.wendel.loja.service.ProdutoService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.math.BigDecimal;

public class LojaView {

    private final BorderPane root = new BorderPane();
    private final ProdutoService produtoService = new ProdutoService();
    private final PedidoService pedidoService = new PedidoService();

    private final ObservableList<Produto> produtos = FXCollections.observableArrayList();
    private final ObservableList<ItemPedido> carrinho = FXCollections.observableArrayList();

    private final Cliente cliente;
    private final Runnable onOpenAdmin;

    public LojaView(Cliente cliente, Runnable onOpenAdmin) {
        this.cliente = cliente;
        this.onOpenAdmin = onOpenAdmin;

        // Tabela de produtos
        TableView<Produto> tabelaProdutos = new TableView<>(produtos);
        TableColumn<Produto, String> colNome = new TableColumn<>("Produto");
        colNome.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getNome()));
        TableColumn<Produto, String> colPreco = new TableColumn<>("Preço");
        colPreco.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty("R$ " + c.getValue().getPreco()));
        TableColumn<Produto, String> colEstoque = new TableColumn<>("Estoque");
        colEstoque.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(String.valueOf(c.getValue().getEstoque())));
        tabelaProdutos.getColumns().addAll(colNome, colPreco, colEstoque);

        // Tabela do carrinho
        TableView<ItemPedido> tabelaCarrinho = new TableView<>(carrinho);
        TableColumn<ItemPedido, String> colCarrinhoNome = new TableColumn<>("Produto");
        colCarrinhoNome.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getProdutoNome()));
        TableColumn<ItemPedido, String> colCarrinhoQtd = new TableColumn<>("Qtd");
        colCarrinhoQtd.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(String.valueOf(c.getValue().getQuantidade())));
        TableColumn<ItemPedido, String> colCarrinhoSubtotal = new TableColumn<>("Subtotal");
        colCarrinhoSubtotal.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty("R$ " + c.getValue().getSubtotal()));
        tabelaCarrinho.getColumns().addAll(colCarrinhoNome, colCarrinhoQtd, colCarrinhoSubtotal);

        // Botões
        Button btnAtualizar = new Button("Atualizar");
        Button btnAdicionar = new Button("Adicionar ao carrinho");
        Button btnRemover = new Button("Remover do carrinho");
        Button btnFechar = new Button("Fechar pedido");
        Button btnAdmin = new Button("Admin Produtos");
        Button btnVoltarLogin = new Button("Voltar para Login"); // <-- novo botão

        Label status = new Label();
        Label totalLabel = new Label("Total: R$ 0.00");

        // Ações
        btnAtualizar.setOnAction(e -> {
            try {
                produtos.setAll(produtoService.listar());
                status.setText("Produtos atualizados.");
            } catch (Exception ex) {
                status.setText("Erro: " + ex.getMessage());
            }
        });

        btnAdicionar.setOnAction(e -> {
            Produto p = tabelaProdutos.getSelectionModel().getSelectedItem();
            if (p == null) { status.setText("Selecione um produto."); return; }
            TextInputDialog qDialog = new TextInputDialog("1");
            qDialog.setHeaderText("Quantidade para " + p.getNome());
            qDialog.showAndWait().ifPresent(qStr -> {
                try {
                    int q = Integer.parseInt(qStr);
                    BigDecimal subtotal = p.getPreco().multiply(BigDecimal.valueOf(q));
                    ItemPedido item = new ItemPedido(p.getId(), q, p.getNome(), subtotal);
                    carrinho.add(item);
                    atualizarTotal(totalLabel);
                    status.setText("Adicionado: " + p.getNome() + " x" + q);
                } catch (NumberFormatException ex) {
                    status.setText("Quantidade inválida.");
                }
            });
        });

        btnRemover.setOnAction(e -> {
            ItemPedido item = tabelaCarrinho.getSelectionModel().getSelectedItem();
            if (item != null) {
                carrinho.remove(item);
                atualizarTotal(totalLabel);
                status.setText("Item removido.");
            }
        });

        btnFechar.setOnAction(e -> {
            if (carrinho.isEmpty()) { status.setText("Carrinho vazio."); return; }
            try {
                int pedidoId = pedidoService.fecharPedido(cliente.getId(), carrinho);
                carrinho.clear();
                atualizarTotal(totalLabel);
                btnAtualizar.fire();
                status.setText("Pedido fechado! ID: " + pedidoId);
            } catch (Exception ex) {
                status.setText("Erro ao fechar: " + ex.getMessage());
            }
        });

        btnAdmin.setOnAction(e -> {
            if (!"ADMIN".equalsIgnoreCase(cliente.getRole())) {
                Alert alert = new Alert(Alert.AlertType.WARNING,
                        "Apenas administradores podem acessar esta área.");
                alert.showAndWait();
                return;
            }
            onOpenAdmin.run();
        });

        // Voltar para Login
        btnVoltarLogin.setOnAction(e -> {
            Stage stage = (Stage) root.getScene().getWindow();
            LoginView loginView = new LoginView(c -> {
                stage.setScene(new Scene(new LojaView(c, onOpenAdmin).getRoot(), 800, 600));
            });
            stage.setScene(new Scene(loginView.getRoot(), 420, 300));
        });

        // Layout
        HBox topo = new HBox(10, btnAtualizar, btnAdicionar, btnRemover, btnFechar, btnAdmin, btnVoltarLogin);
        topo.setPadding(new Insets(10));

        VBox centro = new VBox(10, tabelaProdutos, new Label("Carrinho:"), tabelaCarrinho, totalLabel);
        centro.setPadding(new Insets(10));

        root.setTop(topo);
        root.setCenter(centro);
        root.setBottom(status);

        btnAtualizar.fire();
    }

    private void atualizarTotal(Label totalLabel) {
        BigDecimal total = carrinho.stream()
            .map(ItemPedido::getSubtotal)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        totalLabel.setText("Total: R$ " + total);
    }

    public Parent getRoot() {
        return root;
    }
}