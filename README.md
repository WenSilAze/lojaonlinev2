# 🚀 Como rodar o projeto Loja Online

## 📋 Pré-requisitos
- **Java:** JDK 21 (verifique com `java -version`)
- **Maven:** 3.8+ (verifique com `mvn -v`)
- **Banco de Dados:** MySQL 8+
- **IDE (opcional):** IntelliJ IDEA ou Eclipse

---

## 🗄️ Configuração do Banco de Dados

1. **Criar o banco:**
   ```sql
   CREATE DATABASE lojaonline CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
   USE lojaonline;
   ```

2. **Criar as tabelas:**
   ```sql
   CREATE TABLE cliente (
     id INT AUTO_INCREMENT PRIMARY KEY,
     nome VARCHAR(100) NOT NULL,
     email VARCHAR(150) NOT NULL UNIQUE,
     senha VARCHAR(200) NOT NULL,
     role VARCHAR(20) NOT NULL DEFAULT 'USER'
   );

   CREATE TABLE produto (
     id INT AUTO_INCREMENT PRIMARY KEY,
     nome VARCHAR(100) NOT NULL,
     preco DECIMAL(10,2) NOT NULL,
     estoque INT NOT NULL DEFAULT 0
   );

   CREATE TABLE pedido (
     id INT AUTO_INCREMENT PRIMARY KEY,
     cliente_id INT NOT NULL,
     data_pedido TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
     status VARCHAR(50) NOT NULL DEFAULT 'ABERTO',
     FOREIGN KEY (cliente_id) REFERENCES cliente(id)
   );

   CREATE TABLE item_pedido (
     id INT AUTO_INCREMENT PRIMARY KEY,
     pedido_id INT NOT NULL,
     produto_id INT NOT NULL,
     quantidade INT NOT NULL,
     subtotal DECIMAL(10,2) NOT NULL,
     FOREIGN KEY (pedido_id) REFERENCES pedido(id),
     FOREIGN KEY (produto_id) REFERENCES produto(id)
   );
   ```

3. **Inserir dados de teste (opcional):**
   ```sql
   INSERT INTO cliente (nome, email, senha, role)
   VALUES ('Admin', 'admin@loja.com', 'admin', 'ADMIN'),
          ('Usuário', 'user@loja.com', 'user', 'USER');

   INSERT INTO produto (nome, preco, estoque)
   VALUES ('Playstation 5', 3200.00, 10),
          ('Mouse Gamer', 199.90, 25),
          ('Teclado Mecânico', 320.00, 10),
          ('Monitor 24"', 899.00, 0);
   ```

---

## ⚙️ Configuração da Conexão

No arquivo `src/main/java/com/wendel/loja/util/Conexao.java`, ajuste para seu MySQL local:

```java
public class Conexao {
    private static final String URL = "jdbc:mysql://localhost:3306/lojaonline?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";      // seu usuário
    private static final String PASS = "sua_senha"; // sua senha

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
```

---

## ▶️ Como executar

1. **Compilar o projeto:**
   ```bash
   mvn clean compile
   ```

2. **Rodar com JavaFX (plugin Maven):**
   ```bash
   mvn javafx:run
   ```

3. **Ou gerar JAR e executar manualmente:**
   ```bash
   mvn package
   java --module-path target/lib --add-modules javafx.controls,javafx.fxml -jar target/lojaonline-1.0.0.jar
   ```

---

## 🔑 Credenciais de teste

- **Admin:**  
  Email: `admin@loja.com`  
  Senha: `admin`

- **Usuário comum:**  
  Email: `user@loja.com`  
  Senha: `user`

---

## 🛠️ Funcionalidades

- Login com autenticação
- Listagem de produtos
- Carrinho de compras
- Fechamento de pedido com controle de estoque
- Administração de produtos (CRUD)
- Voltar para login

---

## ❗ Problemas comuns

- **Erro de coluna inexistente:** usar `data_pedido` em vez de `data`.  
- **Falha de conexão MySQL:** verifique usuário/senha em `Conexao.java` e se o banco está rodando.  
- **JavaFX não inicializa:** rode com `--add-modules javafx.controls,javafx.fxml` ou use `mvn javafx:run`.  
- **Encoding:** adicione no `pom.xml`:
  ```xml
  <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
  ```
```

---
