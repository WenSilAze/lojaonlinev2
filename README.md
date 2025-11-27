# 🚀 Como rodar o projeto Loja Online

## 📋 Pré-requisitos
- **Java:** JDK 21 (verifique com `java -version`) e adicione o caminho (PATH) e a variável de ambiente
- **Maven:** 3.8+ (verifique com `mvn -v`) e adicione o caminho (PATH) e a variável de ambiente
- **Banco de Dados:** MySQL 8+ e **XAMPP de preferência**
- **IDE:** Visual Studio Code ou Eclipse

---

# 📦 Extensões necessárias no VS Code para rodar o projeto Loja Online

## ☕ Java
- **Extension Pack for Java**  
  (inclui várias ferramentas essenciais de uma vez)
  - Language Support for Java™ by Red Hat
  - Debugger for Java
  - Java Test Runner
  - Maven for Java
  - Project Manager for Java

## 🔧 Maven
- **Maven for Java**  
  Permite compilar, rodar e gerenciar dependências diretamente pelo VS Code.

## 🎨 JavaFX
- Não existe uma extensão específica para JavaFX.  
  O suporte vem do **Java Extension Pack** + configuração correta do `pom.xml`.  
  - Certifique-se de adicionar as dependências do JavaFX no `pom.xml`:
    ```xml
    <dependencies>
      <dependency>
        <groupId>org.openjfx</groupId>
        <artifactId>javafx-controls</artifactId>
        <version>21</version>
      </dependency>
      <dependency>
        <groupId>org.openjfx</groupId>
        <artifactId>javafx-fxml</artifactId>
        <version>21</version>
      </dependency>
    </dependencies>
    ```

## 🗄️ Banco de Dados (opcional)
- **SQLTools**  
  Para conectar e testar queries diretamente no MySQL dentro do VS Code.
- **SQLTools MySQL/MariaDB**  
  Driver para o SQLTools funcionar com MySQL.

---

## ✅ Resumindo
Instale no VS Code:
1. **Extension Pack for Java** (já traz tudo de Java e Maven).  
2. **SQLTools + SQLTools MySQL/MariaDB** (se quiser gerenciar o banco direto no VS Code).  

Com isso, você consegue:
- Compilar e rodar o projeto com Maven.  
- Depurar código Java.  
- Usar JavaFX sem precisar de extensão extra (apenas dependência no `pom.xml`).  

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
   VALUES ('Admin', 'admin@loja.com', '123456', 'ADMIN'),
          ('Usuário', 'user@loja.com', '123456', 'USER');

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

**Coloque o caminho do projeto antes dos códigos abaixo. Exemplo: cd C:\Users\wende\lojaonlineV2**

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
  Senha: `123456`

- **Usuário comum:**  
  Email: `user@loja.com`  
  Senha: `123456`

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
