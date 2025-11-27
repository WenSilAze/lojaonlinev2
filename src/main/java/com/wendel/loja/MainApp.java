package com.wendel.loja;

// importações
import com.wendel.loja.model.Cliente;
import com.wendel.loja.view.LoginView;
import com.wendel.loja.view.LojaView;
import com.wendel.loja.view.AdminProdutoView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

// Classe principal da aplicação
public class MainApp extends Application {

    private Stage stage;
    private Cliente clienteLogado; // guarda o cliente atual

    @Override
    public void start(Stage primaryStage) {
        this.stage = primaryStage;
        showLogin();
        stage.setTitle("Loja Online");
        stage.show();
    }

    // Tela de login
    public void showLogin() {
        LoginView view = new LoginView(this::onLoginSuccess);
        stage.setScene(new Scene(view.getRoot(), 420, 300));
    }

    // Quando login for bem-sucedido
    private void onLoginSuccess(Cliente cliente) {
        this.clienteLogado = cliente;
        LojaView lojaView = new LojaView(cliente, this::showAdmin);
        stage.setScene(new Scene(lojaView.getRoot(), 800, 600));
    }

    // Tela de administração de produtos
    private void showAdmin() {
        if (clienteLogado == null || !"ADMIN".equalsIgnoreCase(clienteLogado.getRole())) {
            // segurança extra: só abre se for admin
            return;
        }

        AdminProdutoView adminView = new AdminProdutoView(clienteLogado.getId(), () -> {
            LojaView lojaView = new LojaView(clienteLogado, this::showAdmin);
            stage.setScene(new Scene(lojaView.getRoot(), 800, 600));
        });

        stage.setScene(new Scene(adminView.getRoot(), 700, 500));
    }

    public static void main(String[] args) {
        launch(args);
    }
}