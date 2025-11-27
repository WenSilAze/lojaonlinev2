-- Criar banco de dados
CREATE DATABASE IF NOT EXISTS lojaonlinev2;
USE lojaonlinev2;

-- Tabela de clientes
CREATE TABLE IF NOT EXISTS cliente (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    senha VARCHAR(100) NOT NULL,
    role VARCHAR(20) NOT NULL DEFAULT 'USER' -- novo campo para distinguir ADMIN/USER
);

-- Tabela de produtos
CREATE TABLE IF NOT EXISTS produto (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    preco DECIMAL(10,2) NOT NULL,
    estoque INT NOT NULL
);

-- Tabela de pedidos
CREATE TABLE IF NOT EXISTS pedido (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cliente_id INT NOT NULL,
    data_pedido TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(50) NOT NULL DEFAULT 'ABERTO',
    FOREIGN KEY (cliente_id) REFERENCES cliente(id)
);

-- Tabela de itens do pedido
CREATE TABLE IF NOT EXISTS item_pedido (
    id INT AUTO_INCREMENT PRIMARY KEY,
    pedido_id INT NOT NULL,
    produto_id INT NOT NULL,
    quantidade INT NOT NULL,
    subtotal DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (pedido_id) REFERENCES pedido(id),
    FOREIGN KEY (produto_id) REFERENCES produto(id)
);

-- Inserir clientes
INSERT INTO cliente (nome, email, senha, role) VALUES
('Admin', 'admin@loja.com', '123456', 'ADMIN'),
('Wendel', 'wendel@loja.com', '123456', 'USER');

-- Inserir produtos
INSERT INTO produto (nome, preco, estoque) VALUES
('Teclado Mecânico', 350.00, 10),
('Mouse Gamer', 199.90, 25),
('Monitor 24"', 899.00, 8);

-- Consultas de teste
SELECT * FROM cliente;
SELECT * FROM produto;
SELECT * FROM pedido;
SELECT * FROM item_pedido;