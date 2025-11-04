# Projeto: Gestão de Estabelecimento

## 1. 🔍 Visão Geral
Sistema de gestão de estabelecimentos, desenvolvido em **Spring Boot (Java 21)**, com foco em **cadastro e gerenciamento de usuários**, **clientes** e **donos de restaurantes**.

O projeto segue boas práticas de arquitetura REST, padroniza respostas HTTP, e utiliza **MySQL** como banco de dados relacional, com opção de execução containerizada via **Docker**.

---

## 2. ⚙️ Configuração do Ambiente (Docker + Spring Boot + MySQL)

### Arquivo `application-docker.properties`
```properties
spring.profiles.active=docker
spring.datasource.url=jdbc:mysql://mysql:3306/gestao_estabelecimento?useSSL=false&serverTimezone=America/Sao_Paulo&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=senha123
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

server.port=8081

# Logs SQL
spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
```

### Arquivo `docker-compose.yml`
```yaml
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

### Dockerfile
```dockerfile
# Etapa 1: Build com Maven e JDK 21
FROM maven:3.9.6-eclipse-temurin-21 AS builder
WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY ./src ./src
RUN mvn clean package -DskipTests

# Etapa 2: Imagem final leve com JDK 21
FROM eclipse-temurin:21-jdk
WORKDIR /app

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

HEALTHCHECK --interval=30s --timeout=3s --start-period=10s CMD curl -f http://localhost:8081/actuator/health || exit 1
```

---

## 3. 🧱 Dependências Maven (trecho `pom.xml`)
Principais dependências utilizadas:
```xml
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
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
    <scope>runtime</scope>
  </dependency>
  <dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>2.8.13</version>
  </dependency>
  <dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <optional>true</optional>
  </dependency>
</dependencies>
```

---

## 4. 🚀 Guia de Execução

### 🧩 Passos para subir a aplicação via Docker
1. **Gerar o `.jar` localmente:**
   ```bash
   mvn clean package -DskipTests
   ```
2. **Subir os containers:**
   ```bash
   docker-compose up --build
   Acessar o diretorio mysql-init e executar o comando abaixo para copiar o script sql:
    docker cp init.sql gestaoestabelecimento-mysql-1:/init.sql  

   Acessar o mysql do dockerutiliznado o comando abaixo:
   docker exec -it mysql-container mysql -u root -senha123 gestao_estabelecimento    

   Execute:
    mysql -u root -p gestao_estabelecimento < /init.sql
    
     digite Use gestao_estabelecimento 
     Acesse as tabelas para verificar o conteúdo

   ```
3. **Acessar a aplicação:**
   - API: [http://localhost:8082](http://localhost:8082)
   - Banco: `localhost:3307`

---

## 5. 🧩 Estrutura do Banco de Dados

### 🧾 Tabela `usuarios`
| Campo | Tipo | Descrição | Restrições |
|-------|------|------------|-------------|
| id | BIGINT | Identificador único do usuário | PK, Auto Increment |
| nome | VARCHAR(255) | Nome completo do usuário | - |
| email | VARCHAR(255) | E-mail de login | Único |
| login | VARCHAR(255) | Nome de login | - |
| senha | VARCHAR(255) | Senha criptografada | - |
| codigoTipoUsuario | VARCHAR(3) | Código que define o tipo (Cliente/Dono) | FK -> tipoUsuario |
| logradouro | VARCHAR(255) | Endereço | - |
| numero | VARCHAR(255) | Número | - |
| cidade | VARCHAR(255) | Cidade | - |
| estado | VARCHAR(255) | UF | - |
| cep | VARCHAR(255) | CEP | - |
| complemento | VARCHAR(255) | Complemento | - |
| dataUltimaAlteracao | DATETIME | Data da última atualização | - |

### 🧾 Tabela `tipoUsuario`
| Campo | Tipo | Descrição | Restrições |
|--------|------|------------|-------------|
| codigoTipoUsuario | VARCHAR(3) | Código identificador do tipo de usuário | PK |
| descricaoTipoUsuario | VARCHAR(255) | Descrição (ex: Cliente, Dono) | - |

### 🧾 Tabela `clientes`
| Campo | Tipo | Descrição | Restrições |
|--------|------|------------|-------------|
| id | BIGINT | Identificador (mesmo id de `usuarios`) | PK, FK -> usuarios |
| data_aniversario | VARCHAR(20) | Data de nascimento | - |
| data_cadastro | VARCHAR(20) | Data de registro no sistema | - |
| classificacao | VARCHAR(50) | Categoria do cliente | - |

### 🧾 Tabela `donos_restaurantes`
| Campo | Tipo | Descrição | Restrições |
|--------|------|------------|-------------|
| id | BIGINT | Identificador (mesmo id de `usuarios`) | PK, FK -> usuarios |
| nome_estabelecimento | VARCHAR(100) | Nome do restaurante | - |
| tipo_estabelecimento | VARCHAR(50) | Tipo (bar, pizzaria, padaria, etc.) | - |

### 🔗 Relacionamentos
- `usuarios.codigoTipoUsuario` → `tipoUsuario.codigoTipoUsuario`
- `clientes.id` → `usuarios.id`
- `donos_restaurantes.id` → `usuarios.id`

---

## 6. 🌐 Endpoints REST

### 👤 Usuários
| Método | Endpoint | Descrição |
|---------|-----------|------------|
| `GET` | `/usuarios/{nome}` | Consulta usuário pelo nome |
| `POST` | `/usuarios` | Cadastra novo usuário |
| `PUT` | `/usuarios/atualizar-detalhes` | Atualiza informações do usuário |
| `PUT` | `/usuarios/alterar-senha` | Atualiza senha do usuário |
| `DELETE` | `/usuarios/{id}` | Remove usuário pelo ID |

### 🧍 Clientes
| Método | Endpoint | Descrição |
|---------|-----------|------------|
| `POST` | `/clientes` | Cria um novo cliente |
| `GET` | `/clientes` | Lista todos os clientes |
| `GET` | `/clientes/{id}` | Consulta cliente por ID |
| `PUT` | `/clientes/{id}` | Atualiza informações do cliente |
| `DELETE` | `/clientes/{id}` | Remove cliente |

### 🍽️ Donos de Restaurantes
| Método | Endpoint | Descrição |
|---------|-----------|------------|
| `POST` | `/donos` | Cadastra dono e seu restaurante |
| `GET` | `/donos` | Lista todos os donos |
| `GET` | `/donos/{id}` | Consulta dono por ID |
| `PUT` | `/donos/{id}` | Atualiza dados do dono/restaurante |
| `DELETE` | `/donos/{id}` | Exclui dono/restaurante |

---

## 7. 🧩 Boas Práticas e Logs
- `spring.jpa.show-sql=true` exibe os comandos SQL executados.
- Exceções personalizadas tratadas via `@ControllerAdvice`.
- Uso do padrão **DTO** e **Service Layer** para desacoplamento.

---

## 8. 👨‍💻 Autoria
Desenvolvido por **Rafael Rodrigues**  
Projeto acadêmico com fins de aprendizado e demonstração de boas práticas em **Spring Boot**, **Docker**, **MySQL** e **REST API**.

