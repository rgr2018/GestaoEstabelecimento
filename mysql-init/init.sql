CREATE TABLE usuarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome  VARCHAR(255),
    email  VARCHAR(255),
    login  VARCHAR(255),
    senha  VARCHAR(255),
    codigoTipoUsuario VARCHAR(3),
    logradouro VARCHAR(255),
    numero VARCHAR(255),
    cidade VARCHAR(255),
    estado VARCHAR(255),
    cep VARCHAR(255),
    complemento VARCHAR(255),
    dataUltimaAlteracao  DATETIME
);

CREATE TABLE tipoUsuario (
    codigoTipoUsuario VARCHAR(3) PRIMARY KEY,
    descricaoTipoUsuario VARCHAR(255)
);

CREATE TABLE clientes (
    id BIGINT PRIMARY KEY,
    data_aniversario VARCHAR(20),
    data_cadastro VARCHAR(20),
    classificacao VARCHAR(50),
    FOREIGN KEY (id) REFERENCES usuarios(id) ON DELETE CASCADE
);

CREATE TABLE donos_restaurantes (
    id BIGINT PRIMARY KEY,
    nome_estabelecimento VARCHAR(100),
    tipo_estabelecimento VARCHAR(50),
    FOREIGN KEY (id) REFERENCES usuarios(id) ON DELETE CASCADE
);

INSERT INTO tipoUsuario (codigoTipoUsuario, descricaoTipoUsuario) VALUES
('001', 'Cliente'),
('002', 'Dono de Restaurante'),
('003', 'Administrador');

INSERT INTO usuarios (id, nome, email, login, senha, codigoTipoUsuario, logradouro, numero, cidade, estado, cep, complemento, dataUltimaAlteracao)
VALUES
(1, 'Rafael Gavranic', 'rafael@gmail.com', 'rafael123', 'senha123', '001', 'Rua das Flores', '100', 'São Paulo', 'SP', '01000-000', 'Apto 10', NOW()),
(2, 'João Silva', 'joao@gmail.com', 'joao456', 'senha456', '002', 'Rua Verde', '200', 'Campinas', 'SP', '13000-000', NULL, NOW()),
(3, 'Maria Oliveira', 'maria@gmail.com', 'maria789', 'senha789', '001', 'Av. Paulista', '1500', 'São Paulo', 'SP', '01310-100', 'Bloco B', NOW()),
(4, 'Fernanda Costa', 'fernanda@gmail.com', 'fernanda321', 'senha321', '002', 'Rua Azul', '90', 'Rio de Janeiro', 'RJ', '22220-040', 'Loja 1', NOW()),
(5, 'Carlos Pereira', 'carlos@gmail.com', 'carlos654', 'senha654', '001', 'Rua das Laranjeiras', '50', 'Curitiba', 'PR', '80000-200', NULL, NOW()),
(6, 'Lucas Andrade', 'lucas@gmail.com', 'lucas852', 'senha852', '002', 'Av. Beira Mar', '220', 'Florianópolis', 'SC', '88015-000', 'Piso 2', NOW()),
(7, 'Admin Geral', 'admin@gmail.com', 'admin', 'admin123', '003', 'Rua Central', '1', 'São Paulo', 'SP', '01001-000', NULL, NOW());

INSERT INTO clientes (id, data_aniversario, data_cadastro, classificacao)
VALUES
(1, '1990-05-10', '2024-10-01', 'Ouro'),
(3, '1985-09-22', '2023-06-15', 'Prata'),
(5, '1992-12-03', '2025-01-10', 'Bronze');

INSERT INTO donos_restaurantes (id, nome_estabelecimento, tipo_estabelecimento)
VALUES
(2, 'Restaurante Bom Sabor', 'Restaurante'),
(4, 'Pizzaria da Nanda', 'Pizzaria'),
(6, 'Padaria do Lucas', 'Padaria');