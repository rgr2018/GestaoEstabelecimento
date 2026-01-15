 
# 🧾 ***Gestão de Estabelecimentos — API Backend***

Versão: 1.0
Tecnologia: Java 21 + Spring Boot 3.5.6
Banco: MySQL 8
Arquitetura: Clean Architecture (baseada em Casos de Uso)
Build: Maven
Execução: Docker Compose

# 📘 ***Sumário***

**-** **Descrição Geral**

**-** **Arquitetura do Projeto**

**-** **Configuração de Ambiente**

**-** **Docker**

**-** **Dependencia**

**-** **Build e Execução**

**-** **Classes de Domínio**

**-** **Modelo de Dados**

**-** **Endpoints da API**

**-** **Tratamento de Erros**


# 🧩 ***Descrição Geral***
Este projeto consiste no desenvolvimento de uma **API REST para Gestão de Estabelecimentos**, elaborada como **atividade acadêmica do curso Pós-Tech**. O objetivo é demonstrar a aplicação prática de conceitos avançados de **arquitetura de software**, **boas práticas de backend**, **testes automatizados** e **tratamento centralizado de exceções**.
A aplicação permite o gerenciamento completo de **Usuários**, **Clientes**, **Donos**, **Estabelecimentos** e **Cardápios**, respeitando regras de negócio bem definidas e garantindo consistência e integridade dos dados.



# 🏗️  ***Arquitetura do Projeto***

**Resumo**
- **Adapters**: Controllers e Gateways
- **Domain**: Entidades de domínio e interfaces
- **Use Cases**: Regras de negócio
- **Infrastructure**: Repositórios JPA, Entities e Mappers
- **Exceptions**: Exceções de negócio e validação

br.com.fiap.gestaoestabelecimento
```
├── adapters
│   ├── controllers    # Camada de entrada (HTTP)
│   └── gateways       # Camada de regras que conversa com os repositorios
│
├── application   
│   └──useCases        # Camada de de orquestração de negócio
│
├── config             # Configuração de Open API para documentação e exposição dos contratos
│  
├── domain             # Dominio e interfaces das classes de negócio
│
├── dtos               # Objetos de transferência de dados
│
├── exceptions         # Exceções e tratamento global
│
├── infraestructure
│   ├── entities       # Entitdades e atributos de banco de dados 
│   ├── mappers        # conversão de entidades e atributos em classes e objetos de negócio
│   └── repositories   # Interfaces de persistência
│
└── GestaoEstabelecimentoApplication.java
```
# ⚙️ ***Configuração de Ambiente***
🔹 Variáveis de Ambiente

Essas variáveis são carregadas automaticamente pelo Spring Boot ao usar o perfil docker:

spring.profiles.active=docker
spring.datasource.url=jdbc:mysql://mysql:3306/gestao_estabelecimento?useSSL=false&serverTimezone=America/Sao_Paulo&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=senha123
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

server.port=8081

**Logs SQL**
spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect


🔹 POM (Maven Configuration)

O projeto usa Java 21, Spring Boot 3.5.6 e OpenAPI (Swagger UI):
```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>3.5.6</version>
		<relativePath/> <!-- lookup parent from repository -->
	</parent>
	<groupId>br.com.fiap</groupId>
	<artifactId>gestaoestabelecimento</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<name>gestaoestabelecimento</name>
	<description>Projeto de Gestao de estabelecimento</description>
	<url/>
	<licenses>
		<license/>
	</licenses>
	<developers>
		<developer/>
	</developers>
	<scm>
		<connection/>
		<developerConnection/>
		<tag/>
		<url/>
	</scm>
	<properties>
		<java.version>21</java.version>
	</properties>
	<dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-jdbc</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-jpa</artifactId>
		</dependency>
		<dependency>
			<groupId>com.mysql</groupId>
			<artifactId>mysql-connector-j</artifactId>
			<scope>runtime</scope>
		</dependency>

		<dependency>
			<groupId>org.projectlombok</groupId>
			<artifactId>lombok</artifactId>
			<optional>true</optional>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>
        <dependency>
            <groupId>com.h2database</groupId>
            <artifactId>h2</artifactId>
            <scope>runtime</scope>
        </dependency>
		<dependency>
			<groupId>org.junit.jupiter</groupId>
			<artifactId>junit-jupiter</artifactId>
			<version>5.11.1</version>
			<scope>test</scope>
		</dependency>
        <dependency>
            <groupId>org.mockito</groupId>
            <artifactId>mockito-junit-jupiter</artifactId>
            <scope>test</scope>
        </dependency>
		<dependency>
			<groupId>org.springdoc</groupId>
			<artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
			<version>2.8.13</version>
		</dependency>
        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-databind</artifactId>
            <version>2.16.1</version> <!-- Verifique a versão mais recente no Maven Central -->
        </dependency>
        <dependency>
            <groupId>org.jetbrains</groupId>
            <artifactId>annotations</artifactId>
            <version>23.0.0</version>
            <scope>compile</scope>
        </dependency>
    </dependencies>

	<build>
        <resources>
            <!-- Recursos que serão filtrados, exceto arquivos .properties -->
            <resource>
                <directory>src/main/resources</directory>
                <filtering>true</filtering>
                <excludes>
                    <exclude>**/*.properties</exclude> <!-- Não filtra arquivos .properties -->
                </excludes>
            </resource>

            <!-- Recursos que não serão filtrados: apenas arquivos .properties -->
            <resource>
                <directory>src/main/resources</directory>
                <filtering>false</filtering>
                <includes>
                    <include>**/*.properties</include> <!-- Apenas os .properties, sem filtro -->
                </includes>
            </resource>
        </resources>
		<plugins>
            <plugin>
                <groupId>org.jacoco</groupId>
                <artifactId>jacoco-maven-plugin</artifactId>
                <version>0.8.12</version>
                <configuration>
                    <excludes>
                        <!-- Configurações -->
                        <exclude>**/config/**</exclude>
                        <exclude>**/*Config.*</exclude>

                        <!-- DTOs -->
                        <exclude>**/dto/**</exclude>
                        <exclude>**/dtos/**</exclude>
                        <exclude>**/*DTO.*</exclude>

                        <!-- Entidades JPA -->
                        <exclude>**/entity/**</exclude>
                        <exclude>**/entities/**</exclude>
                        <exclude>**/*Entity.*</exclude>

                        <!-- Classes geradas / util -->
                        <exclude>**/generated/**</exclude>
                        <exclude>**/*Application.*</exclude>
                    </excludes>
                </configuration>
                <executions>

                    <!-- Prepara o agente -->
                    <execution>
                        <id>prepare-agent</id>
                        <goals>
                            <goal>prepare-agent</goal>
                        </goals>
                    </execution>

                    <!-- Gera o relatório -->
                    <execution>
                        <id>report</id>
                        <phase>test</phase>
                        <goals>
                            <goal>report</goal>
                        </goals>
                    </execution>

                </executions>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.14.0</version>
                <configuration>
                    <release>21</release>
                </configuration>
            </plugin>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
				<configuration>
					<excludes>
						<exclude>
							<groupId>org.projectlombok</groupId>
							<artifactId>lombok</artifactId>
						</exclude>
					</excludes>
				</configuration>
			</plugin>
		</plugins>
	</build>

</project>
```


# ***Docker***
🔹 Arquivo docker-compose.yml
```
services:
  mysql:
    image: mysql:8.0
    environment:
      MYSQL_ROOT_PASSWORD: senha123
      MYSQL_DATABASE: gestao_estabelecimento
    ports:
      - "3307:3306"
    volumes:
      - mysql_data:/var/lib/mysql

  gestao_app:
    build: .
    ports:
      - "8082:8081"
    environment:
      SPRING_PROFILES_ACTIVE: docker
      SPRING_DATASOURCE_URL: jdbc:mysql://mysql:3306/gestao_estabelecimento?useSSL=false&serverTimezone=America/Sao_Paulo&allowPublicKeyRetrieval=true
      SPRING_DATASOURCE_USERNAME: root
      SPRING_DATASOURCE_PASSWORD: senha123
      SPRING_DATASOURCE_DRIVER_CLASS_NAME: com.mysql.cj.jdbc.Driver
      SERVER_PORT: 8081
    depends_on:
      - mysql

volumes:
  mysql_data:
  ```
# 🔹 Dockerfile
**Etapa 1: Build com Maven e JDK 21**
```
FROM maven:3.9.6-eclipse-temurin-21 AS builder
WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY ./src ./src
RUN mvn clean package -DskipTests

**Etapa 2: Imagem final leve com JDK 21**
FROM eclipse-temurin:21-jdk
WORKDIR /app
```
**# Cria grupo e usuário (para segurança)**
```
RUN groupadd appgroup && useradd -r -g appgroup appuser

COPY --from=builder /app/target/*.jar app.jar
RUN chown appuser:appgroup app.jar

ENV SPRING_DATASOURCE_URL=jdbc:mysql://mysql:3307/gestao_estabelecimento?useSSL=false&serverTimezone=America/Sao_Paulo
ENV SPRING_DATASOURCE_USERNAME=root
ENV SPRING_DATASOURCE_PASSWORD=senha123
ENV SPRING_DATASOURCE_DRIVER_CLASS_NAME=com.mysql.cj.jdbc.Driver
ENV SERVER_PORT=8081

EXPOSE 8081

USER appuser

ENTRYPOINT ["java", "-jar", "app.jar"]

***Healthcheck (requer actuator)***
HEALTHCHECK --interval=30s --timeout=3s --start-period=10s CMD curl -f http://localhost:8081/actuator/health || exit 1
```

# 🚀 Build e Execução
🔹 Rodar localmente (sem Docker)
mvn spring-boot:run


A API estará disponível em:
```
http://localhost:8081
```
🔹 Rodar com Docker Compose
docker-compose up --build


A aplicação estará acessível em:
```
http://localhost:8082
```
  
# 📦 ***Classes de Domínio***
Este módulo contém as classes de domínio do sistema de Gestão de Estabelecimentos.
Elas representam os principais conceitos do negócio, seguindo princípios de Domain-Driven Design (DDD), mantendo regras e atributos essenciais desacoplados de frameworks e camadas externas.

🍽️ **Classe Cardapio**
  - Descrição:
  Representa um item do cardápio de um estabelecimento, contendo informações comerciais e de disponibilidade do prato.

  - Responsabilidade:
  Modelar pratos oferecidos por um estabelecimento
  Armazenar dados necessários para exibição, precificação e controle de disponibilidade

  - Atributos
  ```
| Atributo                 | Tipo   | Descrição                                 |
| ------------------------ | ------ | ----------------------------------------- |
| id                       | UUID   | Identificador único do prato              |
| nome                     | String | Nome do prato                             |
| descricao                | String | Descrição detalhada                       |
| preco                    | Double | Preço do prato                            |
| indicadorDisponibilidade | String | Indica se o prato está disponível (S / N) |
| fotoPrato                | String | URL da imagem do prato                    |

```
Observações:
A classe é simples e focada no domínio
Não possui dependências de frameworks
O id pode ser atribuído posteriormente (ex: persistência)

👤 ***Classe Usuario***
 - Descrição:
  Classe base que representa um usuário do sistema.
  É estendida por tipos específicos como Cliente e Dono.

- Responsabilidade:
  Centralizar atributos comuns a todos os usuários
  Servir como base para especializações

-  Atributos:
```
| Atributo            | Tipo     | Descrição                  |
| ------------------- | -------- | -------------------------- |
| idUsuario           | UUID     | Identificador do usuário   |
| nome                | String   | Nome completo              |
| email               | String   | Email do usuário           |
| login               | String   | Login de acesso            |
| senha               | String   | Senha do usuário           |
| tipoUsuario         | String   | Tipo (CLIENTE, DONO, etc.) |
| dataUltimaAlteracao | String   | Última alteração cadastral |
| endereco            | Endereco | Endereço associado         |

```
Observações:
  Implementa herança para reutilização de código
  Mantém o domínio independente da infraestrutura

🧑‍💼 ***Classe Cliente***

- Descrição:
  Especialização da classe Usuario, representando um cliente do sistema.

- Responsabilidade:
  Representar clientes finais
  Adicionar informações específicas do cliente

- Atributos Adicionais: 
```
| Atributo        | Tipo   | Descrição                      |
| --------------- | ------ | ------------------------------ |
| idCliente       | UUID   | Identificador único do cliente |
| dataAniversario | String | Data de nascimento             |

Relação de Herança
Usuario
 └── Cliente
```

- Observações:
Reutiliza dados de Usuario
Mantém imutabilidade dos campos específicos

🧑‍🍳 ***Classe Dono***
- Descrição:
  Especialização da classe Usuario, representando o dono de um estabelecimento.

- Responsabilidade:
  Representar usuários com permissão administrativa
  Associar-se a estabelecimentos

- Atributos Adicionais:
```
| Atributo | Tipo | Descrição             |
| -------- | ---- | --------------------- |
| idDono   | UUID | Identificador do dono |

Relação de Herança
Usuario
 └── Dono
```

- Observações:
  Donos podem estar associados a um ou mais estabelecimentos
  Herda credenciais e dados pessoais de Usuario

🏠 ***Classe Endereco***
- Descrição:
  Representa o endereço físico associado a usuários e estabelecimentos.

- Responsabilidade:
  Centralizar dados de localização
  Evitar duplicação de informações de endereço

- Atributos:
```
| Atributo    | Tipo   | Descrição                 |
| ----------- | ------ | ------------------------- |
| idEndereco  | UUID   | Identificador do endereço |
| logradouro  | String | Rua/Avenida               |
| numero      | String | Número                    |
| bairro      | String | Bairro                    |
| cidade      | String | Cidade                    |
| estado      | String | Estado                    |
| cep         | String | CEP                       |
| complemento | String | Complemento               |

```
- Observações:
  Classe imutável, exceto pelo idEndereco
  Utilizada por múltiplos agregados

🏢 ***Classe Estabelecimento***

- Descrição:
  Representa um estabelecimento comercial cadastrado no sistema.

- Responsabilidade:
  Centralizar dados institucionais
  Relacionar donos, cardápios e endereço

- Atributos:
```
| Atributo                          | Tipo           | Descrição                        |
| --------------------------------- | -------------- | -------------------------------- |
| idEstabelecimento                 | UUID           | Identificador do estabelecimento |
| nome                              | String         | Nome comercial                   |
| cnpj                              | String         | CNPJ                             |
| tipoCozinha                       | String         | Tipo de culinária                |
| tipoEstabelecimento               | String         | Restaurante, lanchonete, etc     |
| horarioAberturaDiaSemana          | String         | Horário de abertura              |
| horarioFechamentoDiaSemana        | String         | Horário de fechamento            |
| horarioAberturaFeriadoFimSemana   | String         | Horário especial                 |
| horarioFechamentoFeriadoFimSemana | String         | Fechamento especial              |
| endereco                          | Endereco       | Endereço do estabelecimento      |
| donos                             | List<Dono>     | Donos associados                 |
| cardapios                         | List<Cardapio> | Itens do cardápio                |

```
```
Relações
Estabelecimento
 ├── Endereco
 ├── List<Dono>
 └── List<Cardapio>
 ```

# 📊 ***Modelo de Dados***
O modelo de dados foi projetado para suportar o gerenciamento de usuários, clientes, donos, estabelecimentos, cardápios e endereços, garantindo normalização, reutilização de entidades e relacionamentos bem definidos.

🧑 ***Usuário (Usuario)***

 Representa qualquer usuário do sistema, podendo assumir diferentes perfis (CLIENTE, DONO, etc.).

- Principais atributos:
  Identificação e autenticação
  Tipo de usuário
  Endereço associado

- Relacionamentos:
  Muitos usuários podem estar associados a um endereço (ManyToOne)
  Um usuário pode se tornar Cliente ou Dono (relacionamento 1:1)

👤 ***Cliente (Cliente)***

Especialização do usuário com informações específicas de cliente.

Relacionamentos:
  1:1 com Usuario
  Não possui relacionamento direto com Estabelecimento

🧑‍🍳 ***Dono (Dono)***

Especialização do usuário responsável por um ou mais estabelecimentos.

- Relacionamentos:
  1:1 com Usuario
  N:N com Estabelecimento (um dono pode administrar vários estabelecimentos e vice-versa)

🏢 ***Estabelecimento (Estabelecimento)***
Entidade central do domínio, representando restaurantes, lanchonetes, etc.

- Relacionamentos:
  N:1 com Endereco
  N:N com Dono
  N:N com Cardapio

🍽️ ***Cardápio (Cardapio)***

Representa pratos ou itens comercializados por um ou mais estabelecimentos.

- Relacionamentos:
  N:N com Estabelecimento

🏠 ***Endereço (Endereco)***

Entidade compartilhada por usuários e estabelecimentos.

Relacionamentos:
  1:N com Usuario
  1:N com Estabelecimento

🔗 ***Relacionamentos Resumidos***

| Entidade A | Relacionamento | Entidade B      |
|------------|----------------|-----------------|
| Usuario    | 1 : 1          | Cliente         |
| Usuario    | 1 : 1          | Dono            |
| Endereco   | 1 : N          | Usuario         |
| Endereco   | 1 : N          | Estabelecimento |
| Dono       | N : N          | Estabelecimento |
| Cardapio   | N : N          | Estabelecimento |

```
USUARIO (idUsuario PK)
 ├─ nome
 ├─ email
 ├─ login
 ├─ senha
 ├─ tipoUsuario
 ├─ dataUltimaAlteracao
 └─ idEndereco FK

CLIENTE (idCliente PK)
 ├─ dataAniversario
 └─ idUsuario FK (1:1)

DONO (idDono PK)
 └─ idUsuario FK (1:1)

ENDERECO (idEndereco PK)
 ├─ logradouro
 ├─ numero
 ├─ bairro
 ├─ cidade
 ├─ estado
 ├─ cep
 └─ complemento

ESTABELECIMENTO (idEstabelecimento PK)
 ├─ nome
 ├─ CNPJ
 ├─ tipoCozinha
 ├─ tipoEstabelecimento
 ├─ horarios
 └─ idEndereco FK

CARDAPIO (idCardapio PK)
 ├─ nome
 ├─ descricao
 ├─ preco
 ├─ indicadorDisponibilidade
 ├─ fotoPrato
 └─ dataHoraCadastro
```
# 📡 ***Endpoints da API***

Esta documentação descreve os endpoints disponíveis na API Gestão de Estabelecimento, organizados por domínio funcional. O objetivo é apoiar a compreensão, uso e avaliação acadêmica do projeto.

📌 **Cliente**
```
GET /usuarios/v1/nome
Descrição: Busca clientes pelo nome informado como parâmetro.
```
```
GET /usuarios/v1/buscar-por-email
Descrição: Busca um cliente a partir do e-mail informado.
```
```
GET /clientes/v1/valida-acesso
Descrição: Valida o acesso de um cliente com base em login e senha.
```
```
PUT /clientes/v1/atualizar-login-senha
Descrição: Atualiza login e senha de um cliente existente.
```
```
PUT /clientes/v1/atualizar
Descrição: Atualiza os dados cadastrais completos de um cliente.
```
```
POST /clientes/v1/incluir
Descrição: Realiza o cadastro de um novo cliente no sistema.
```
```
DELETE /clientes/v1/excluir
Descrição: Exclui um cliente com base no e-mail informado.
```
```
GET /clientes/v1/lista
Descrição: Lista todos os clientes cadastrados.
```
```
GET /clientes/v1/busca-por-id
Descrição: Busca um cliente pelo identificador único (UUID).
```

🏪 ***Estabelecimento***
```
GET /estabelecimentos/v1/nome
Descrição: Busca estabelecimentos pelo nome.
```
```
GET /estabelecimentos/v1/buscar-por-id
Descrição: Busca um estabelecimento pelo seu identificador único.
```
```
GET /estabelecimentos/v1/lista
Descrição: Lista todos os estabelecimentos cadastrados.
```
```
PUT /estabelecimentos/v1/atualizar
Descrição: Atualiza os dados de um estabelecimento.
```
```
POST /estabelecimentos/v1/incluir
Descrição: Cadastra um novo estabelecimento.
```
```
DELETE /estabelecimentos/v1/excluir
Descrição: Remove um estabelecimento do sistema.
```

👤 ***Dono***
```
GET /usuarios/v1/buscar-por-email
Descrição: Busca dados de um dono a partir do e-mail.
```
```
GET /usuarios/v1/buscar-por-nome
Descrição: Busca donos pelo nome.
```
```
PUT /clientes/v1/atualizar-login-senha
Descrição: Atualiza login e senha do dono.
```
```
GET /donos/v1/validar-acesso
Descrição: Valida as credenciais de acesso do dono.
```
```
POST /donos/v1/incluir
Descrição: Cadastra um novo dono vinculado a um estabelecimento.
```
```
DELETE /donos/v1/excluir
Descrição: Exclui um dono do sistema.
```
```
GET /donos/v1/lista
Descrição: Lista todos os donos cadastrados.
```
```
GET /donos/v1/buscar-por-id
Descrição: Busca um dono pelo seu identificador.
```
```
PUT /donos/v1/atualizar
Descrição: Atualiza os dados do dono.
```

🍽️ ***Cardápio***
```
GET /cardapios/v1/buscar-por-id
Descrição: Busca um cardápio pelo identificador.
```
```
PUT /cardapios/v1/atualizar
Descrição: Atualiza os dados de um item de cardápio.
```
```
GET /cardapios/v1/lista
Descrição: Lista os itens de cardápio de um estabelecimento.
```
```
POST /cardapios/v1/incluir
Descrição: Cadastra um novo item de cardápio para um estabelecimento.
```
```
DELETE /cardapios/v1/excluir
Descrição: Remove um item de cardápio.
```
🔍 ***Swagger UI***

A documentação interativa dos endpoints estará disponível após subir o container em:

http://localhost:8082/swagger-ui/index.html


# ⚙️ ***Tratamento de Erros***
Exceção	HTTP	
Estrutura da Resposta

```ValidationException	400 Bad Request	{ "mensagem": "Dados inválidos" }```

```ResourceNotFoundException	404 Not Found	{ "mensagem": "Recurso não encontrado" }```

```UnauthorizedException	401 Unauthorized	{ "mensagem": "Acesso negado" }```

```Exception	500 Internal Server Error	{ "mensagem": "Erro interno no servidor" }```

🧠 Regras de Negócio

Caso de Uso	Regra Principal
```
Buscar Cliente/Dono	Retorna erro 404 se não encontrado

Validar Acesso	Login e senha obrigatórios

Atualizar Senha	Atualiza dataUltimaAlteracao

Incluir Cliente/Dono	Email deve ser único

Excluir Cliente/Dono	Requer cadastro existente
```



