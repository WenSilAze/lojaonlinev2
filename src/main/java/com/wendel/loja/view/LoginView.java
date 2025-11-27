package com.wendel.loja.view;

// importações
import com.wendel.loja.model.Cliente;
import com.wendel.loja.service.AuthService;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

// Classe para a interface de login
public class LoginView {

    private final VBox root = new VBox(12);
    private final AuthService authService = new AuthService();

    // Callback agora recebe Cliente completo
    public LoginView(java.util.function.Consumer<Cliente> onLoginSuccess) {
        Label title = new Label("Login - Loja Online");

        TextField email = new TextField();
        email.setPromptText("Email");

        PasswordField senha = new PasswordField();
        senha.setPromptText("Senha");

        Button btnLogin = new Button("Entrar");
        Button btnRegister = new Button("Registrar");

        Label feedback = new Label();

        // Ação de login
        btnLogin.setOnAction(e -> {
            try {
                Cliente cliente = authService.login(email.getText().trim(), senha.getText().trim());
                if (cliente != null) {
                    onLoginSuccess.accept(cliente); // passa Cliente inteiro
                } else {
                    feedback.setText("Credenciais inválidas.");
                }
            } catch (Exception ex) {
                feedback.setText("Erro: " + ex.getMessage());
            }
        });

        // Ação de registro
        btnRegister.setOnAction(e -> {
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Cadastro de Usuário");

            TextField nomeField = new TextField();
            nomeField.setPromptText("Nome");

            TextField emailField = new TextField(email.getText());
            emailField.setPromptText("Email");

            PasswordField senhaField = new PasswordField();
            senhaField.setPromptText("Senha");

            VBox dialogContent = new VBox(10);
            dialogContent.setPadding(new Insets(10));
            dialogContent.getChildren().addAll(
                new Label("Nome:"), nomeField,
                new Label("Email:"), emailField,
                new Label("Senha:"), senhaField
            );

            dialog.getDialogPane().setContent(dialogContent);
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

            dialog.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    String nome = nomeField.getText().trim();
                    String emailInput = emailField.getText().trim();
                    String senhaInput = senhaField.getText().trim();

                    if (nome.isEmpty() || emailInput.isEmpty() || senhaInput.isEmpty()) {
                        feedback.setText("Preencha todos os campos.");
                        return;
                    }

                    try {
                        Cliente novo = authService.registrar(nome, emailInput, senhaInput);
                        feedback.setText("Registrado com sucesso! ID: " + novo.getId());
                    } catch (Exception ex) {
                        feedback.setText("Erro: " + ex.getMessage());
                    }
                }
            });
        });

        // Layout
        root.getChildren().addAll(title, email, senha, btnLogin, btnRegister, feedback);
        root.setPadding(new Insets(16));
    }

    public Parent getRoot() {
        return root;
    }
}