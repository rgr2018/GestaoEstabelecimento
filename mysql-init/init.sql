SET FOREIGN_KEY_CHECKS = 0;

CREATE TABLE Endereco (
    idEndereco CHAR(36) PRIMARY KEY,
    logradouro VARCHAR(200) NOT NULL,
    numero VARCHAR(20),
    bairro VARCHAR(20),
    cidade VARCHAR(30),
    estado VARCHAR(20),
    cep VARCHAR(9) NOT NULL,
    complemento VARCHAR(200)
) ENGINE=InnoDB;

CREATE TABLE Usuario (
    idUsuario CHAR(36) PRIMARY KEY,
    nome VARCHAR(200) NOT NULL,
    email VARCHAR(200) NOT NULL,
    login VARCHAR(200) NOT NULL,
    senha VARCHAR(200) NOT NULL,
    tipoUsuario VARCHAR(100) NOT NULL,
    dataUltimaAlteracao VARCHAR(50),
    id_endereco CHAR(36),

    CONSTRAINT fk_usuario_endereco
        FOREIGN KEY (id_endereco)
        REFERENCES Endereco(idEndereco)
) ENGINE=InnoDB;

CREATE TABLE Cliente (
    idCliente CHAR(36) PRIMARY KEY,
    dataAniversario VARCHAR(20),
    dataHoraCadastro DATETIME,
    idUsuario CHAR(36) NOT NULL UNIQUE,

    CONSTRAINT fk_cliente_usuario
        FOREIGN KEY (idUsuario)
        REFERENCES Usuario(idUsuario)
) ENGINE=InnoDB;

CREATE TABLE Dono (
    idDono CHAR(36) PRIMARY KEY,
    dataHoraCadastro DATETIME,
    idUsuario CHAR(36) NOT NULL UNIQUE,

    CONSTRAINT fk_dono_usuario
        FOREIGN KEY (idUsuario)
        REFERENCES Usuario(idUsuario)
) ENGINE=InnoDB;

CREATE TABLE Estabelecimento (
    idEstabelecimento CHAR(36) PRIMARY KEY,
    nome VARCHAR(200) NOT NULL,
    CNPJ VARCHAR(15) NOT NULL,
    tipoCozinha VARCHAR(100) NOT NULL,
    tipoEstabelecimento VARCHAR(200) NOT NULL,
    HorarioAberturaDiaSemana VARCHAR(20) NOT NULL,
    HorarioFechamentoDiaSemana VARCHAR(20) NOT NULL,
    HorarioAberturaFeriadoFimSemana VARCHAR(20) NOT NULL,
    HorarioFechamentoFeriadoFimSemana VARCHAR(20) NOT NULL,
    dataHoraCadastro DATETIME,
    id_endereco CHAR(36),

    CONSTRAINT fk_estabelecimento_endereco
        FOREIGN KEY (id_endereco)
        REFERENCES Endereco(idEndereco)
) ENGINE=InnoDB;

CREATE TABLE Cardapio (
    idCardapio CHAR(36) PRIMARY KEY,
    nome VARCHAR(200) NOT NULL,
    descricao TEXT NOT NULL,
    preco DECIMAL(10,2) NOT NULL,
    indicadorDisponibilidade VARCHAR(1) NOT NULL,
    fotoPrato VARCHAR(255) NOT NULL,
    dataHoraCadastro DATETIME
) ENGINE=InnoDB;

CREATE TABLE dono_estabelecimento (
    idDono CHAR(36) NOT NULL,
    idEstabelecimento CHAR(36) NOT NULL,

    PRIMARY KEY (idDono, idEstabelecimento),

    CONSTRAINT fk_dono_estab_dono
        FOREIGN KEY (idDono)
        REFERENCES Dono(idDono),

    CONSTRAINT fk_dono_estab_estab
        FOREIGN KEY (idEstabelecimento)
        REFERENCES Estabelecimento(idEstabelecimento)
) ENGINE=InnoDB;

CREATE TABLE cardapio_estabelecimento (
    idCardapio CHAR(36) NOT NULL,
    idEstabelecimento CHAR(36) NOT NULL,

    PRIMARY KEY (idCardapio, idEstabelecimento),

    CONSTRAINT fk_cardapio_estab_cardapio
        FOREIGN KEY (idCardapio)
        REFERENCES Cardapio(idCardapio),

    CONSTRAINT fk_cardapio_estab_estab
        FOREIGN KEY (idEstabelecimento)
        REFERENCES Estabelecimento(idEstabelecimento)
) ENGINE=InnoDB;

INSERT INTO Endereco VALUES (
    UUID(), 'Rua das Flores', '100', 'Centro',
    'São Paulo', 'SP', '01000-000', 'Apto 12'
);

INSERT INTO Usuario
SELECT
    UUID(),
    'João Silva',
    'joao@email.com',
    'joaosilva',
    '123456',
    'CLIENTE',
    NOW(),
    idEndereco
FROM Endereco LIMIT 1;

INSERT INTO Cliente
SELECT
    UUID(),
    '1990-01-01',
    NOW(),
    idUsuario
FROM Usuario
WHERE tipoUsuario = 'CLIENTE'
LIMIT 1;

INSERT INTO Usuario
VALUES (
    UUID(),
    'Maria Souza',
    'maria@email.com',
    'mariasouza',
    '123456',
    'DONO',
    NOW(),
    (SELECT idEndereco FROM Endereco LIMIT 1)
);

INSERT INTO Dono
SELECT
    UUID(),
    NOW(),
    idUsuario
FROM Usuario
WHERE tipoUsuario = 'DONO'
LIMIT 1;

INSERT INTO Estabelecimento
SELECT
    UUID(),
    'Restaurante Sabor Caseiro',
    '12345678000199',
    'Brasileira',
    'Restaurante',
    '08:00',
    '18:00',
    '09:00',
    '16:00',
    NOW(),
    idEndereco
FROM Endereco LIMIT 1;

INSERT INTO Cardapio VALUES (
    UUID(),
    'Feijoada',
    'Feijoada completa com acompanhamentos',
    49.90,
    'S',
    'https://img.com/feijoada.jpg',
    NOW()
);

INSERT INTO dono_estabelecimento
SELECT d.idDono, e.idEstabelecimento
FROM Dono d, Estabelecimento e
LIMIT 1;

INSERT INTO cardapio_estabelecimento
SELECT c.idCardapio, e.idEstabelecimento
FROM Cardapio c, Estabelecimento e
LIMIT 1;

SET FOREIGN_KEY_CHECKS = 1;