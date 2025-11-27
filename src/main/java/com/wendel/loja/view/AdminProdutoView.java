package com.wendel.loja.view;

// importações
import com.wendel.loja.model.Produto;
import com.wendel.loja.service.ProdutoService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.math.BigDecimal;

// Classe para a interface de administração de produtos
public class AdminProdutoView {

    // Root pane
    private final BorderPane root = new BorderPane();
    private final ProdutoService produtoService = new ProdutoService();
    private final ObservableList<Produto> produtos = FXCollections.observableArrayList();

    public AdminProdutoView(int clienteId, Runnable onBack) {
        // Tabela de produtos
        TableView<Produto> tabelaProdutos = new TableView<>(produtos);
        TableColumn<Produto, String> colNome = new TableColumn<>("Produto");
        colNome.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getNome()));
        TableColumn<Produto, String> colPreco = new TableColumn<>("Preço");
        colPreco.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty("R$ " + c.getValue().getPreco()));
        TableColumn<Produto, String> colEstoque = new TableColumn<>("Estoque");
        colEstoque.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(String.valueOf(c.getValue().getEstoque())));
        tabelaProdutos.getColumns().addAll(colNome, colPreco, colEstoque);

        // Botões
        Button btnAtualizar = new Button("Atualizar");
        Button btnAdicionar = new Button("Adicionar Produto");
        Button btnEditar = new Button("Editar Produto");
        Button btnRemover = new Button("Remover Produto");
        Button btnVoltar = new Button("Voltar");

        Label status = new Label();

        // Ações
        btnAtualizar.setOnAction(e -> {
            try {
                produtos.setAll(produtoService.listar());
                status.setText("Produtos atualizados.");
            } catch (Exception ex) {
                status.setText("Erro: " + ex.getMessage());
            }
        });

        // Botão para adicionar novo produto
        btnAdicionar.setOnAction(e -> {
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Novo Produto");

            TextField nomeField = new TextField();
            TextField precoField = new TextField();
            TextField estoqueField = new TextField();

            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(10));
            grid.addRow(0, new Label("Nome:"), nomeField);
            grid.addRow(1, new Label("Preço:"), precoField);
            grid.addRow(2, new Label("Estoque:"), estoqueField);

            dialog.getDialogPane().setContent(grid);
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

            dialog.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    try {
                        Produto novo = new Produto(); // usa construtor vazio
                        novo.setNome(nomeField.getText().trim());
                        novo.setPreco(new BigDecimal(precoField.getText().trim()));
                        novo.setEstoque(Integer.parseInt(estoqueField.getText().trim()));
                        produtoService.salvar(novo); // chama ProdutoService.salvar
                        btnAtualizar.fire();
                        status.setText("Produto adicionado.");
                    } catch (Exception ex) {
                        status.setText("Erro: " + ex.getMessage());
                    }
                }
            });
        });

        // Botão para editar produto existente
        btnEditar.setOnAction(e -> {
            Produto selecionado = tabelaProdutos.getSelectionModel().getSelectedItem();
            if (selecionado == null) {
                status.setText("Selecione um produto para editar.");
                return;
            }

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Editar Produto");

            TextField nomeField = new TextField(selecionado.getNome());
            TextField precoField = new TextField(selecionado.getPreco().toString());
            TextField estoqueField = new TextField(String.valueOf(selecionado.getEstoque()));

            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(10));
            grid.addRow(0, new Label("Nome:"), nomeField);
            grid.addRow(1, new Label("Preço:"), precoField);
            grid.addRow(2, new Label("Estoque:"), estoqueField);

            dialog.getDialogPane().setContent(grid);
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

            dialog.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    try {
                        selecionado.setNome(nomeField.getText().trim());
                        selecionado.setPreco(new BigDecimal(precoField.getText().trim()));
                        selecionado.setEstoque(Integer.parseInt(estoqueField.getText().trim()));
                        produtoService.atualizar(selecionado);
                        btnAtualizar.fire();
                        status.setText("Produto atualizado.");
                    } catch (Exception ex) {
                        status.setText("Erro: " + ex.getMessage());
                    }
                }
            });
        });

        // Botão para remover produto existente
        btnRemover.setOnAction(e -> {
            Produto selecionado = tabelaProdutos.getSelectionModel().getSelectedItem();
            if (selecionado == null) {
                status.setText("Selecione um produto para remover.");
                return;
            }
            try {
                produtoService.remover(selecionado.getId());
                btnAtualizar.fire();
                status.setText("Produto removido.");
            } catch (Exception ex) {
                status.setText("Erro: " + ex.getMessage());
            }
        });

        // Botão para voltar à tela anterior
        btnVoltar.setOnAction(e -> onBack.run());

        // Layout
        HBox topo = new HBox(10, btnAtualizar, btnAdicionar, btnEditar, btnRemover, btnVoltar);
        topo.setPadding(new Insets(10));

        VBox centro = new VBox(10, tabelaProdutos, status);
        centro.setPadding(new Insets(10));

        root.setTop(topo);
        root.setCenter(centro);

        // Carregar produtos inicialmente
        btnAtualizar.fire();
    }

    // Método para obter o root pane
    public Parent getRoot() {
        return root;
    }
}