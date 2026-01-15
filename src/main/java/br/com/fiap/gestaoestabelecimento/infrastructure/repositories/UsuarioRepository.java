package br.com.fiap.gestaoestabelecimento.infrastructure.repositories;

import br.com.fiap.gestaoestabelecimento.infrastructure.entities.ClienteEntity;
import br.com.fiap.gestaoestabelecimento.infrastructure.entities.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, UUID> {

    // ATUALIZA LOGIN E SENHA
    @Modifying
    @Query(value = """
        UPDATE usuario 
        SET login = :login, senha = :senha 
        WHERE email = :email AND tipo_usuario = :tipoUsuario
        """, nativeQuery = true)
    int atualizaLoginSenhaUsuario(
            @Param("email") String email,
            @Param("login") String login,
            @Param("senha") String senha,
            @Param("tipoUsuario") String tipoUsuario
    );


    // VALIDA EMAIL
    @Query(value = "SELECT COUNT(*) FROM usuario WHERE email = :email", nativeQuery = true)
    long validePorEmail(@Param("email") String email);

    // VALIDA ACESSO
    @Query(value = """
        SELECT * 
        FROM usuario 
        WHERE login = :login 
        AND senha = :senha 
        """, nativeQuery = true)
    String validaAcessoUsuario(
            @Param("login") String login,
            @Param("senha") String senha
    );


    // BUSCA POR E-MAIL
    @Query(value = """
        
        SELECT BIN_TO_UUID(id_usuario) FROM usuario 
        WHERE email = :email 
        AND tipo_usuario = :tipoUsuario
        """, nativeQuery = true)
    UUID buscaIdPorEmail(
            @Param("email") String email,
            @Param("tipoUsuario") String tipoUsuario
    );

    // BUSCA POR E-MAIL
    @Query(value = """
        SELECT BIN_TO_UUID(id_endereco) FROM usuario 
        WHERE email = :email 
        """, nativeQuery = true)
    UUID buscaIdEnderecoPorEmail(
            @Param("email") String email
    );

    // BUSCA POR E-MAIL
    @Query(value = """
        SELECT BIN_TO_UUID(id_cliente) FROM cliente 
        WHERE id_usuario = :id_usuario 
        """, nativeQuery = true)
    UUID buscaIdclientePoridUsuario(
            @Param("id_usuario") UUID id_usuario

    );

    // BUSCA POR E-MAIL
    @Query(value = """
        SELECT BIN_TO_UUID(id_dono) FROM dono 
        WHERE id_usuario = :id_usuario 
        """, nativeQuery = true)
    UUID buscaIddonoPoridUsuario(
            @Param("id_usuario") UUID id_usuario

    );



}
