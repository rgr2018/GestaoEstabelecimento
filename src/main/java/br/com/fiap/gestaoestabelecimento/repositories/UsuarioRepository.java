package br.com.fiap.gestaoestabelecimento.repositories;

import br.com.fiap.gestaoestabelecimento.dtos.*;
import br.com.fiap.gestaoestabelecimento.entities.Usuario;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public abstract class UsuarioRepository<T extends Usuario> {

    protected final JdbcClient jdbcClient;

    protected UsuarioRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public Long ultimoId() {
        return this.jdbcClient
                .sql("SELECT MAX(id) AS id FROM usuarios")
                .query(Long.class)
                .single();
    }
    public List<UsuarioNameDTO> findByNome(String nome, String tipoUsuario) {
        return this.jdbcClient
                .sql("SELECT id, nome, email, login FROM usuarios WHERE nome LIKE CONCAT('%', :nome, '%') AND codigoTipoUsuario = :tipoUsuario")
                .param("nome", nome)
                .param("tipoUsuario", tipoUsuario)
                .query(UsuarioNameDTO.class)
                .list();
    }

    public Integer  validaAcesso(String login, String senha, String tipoUsuario) {
        return jdbcClient
                .sql("SELECT COUNT(*) FROM usuarios WHERE login = :login AND senha = :senha AND codigoTipoUsuario = :tipoUsuario")
                .param("login", login)
                .param("senha", senha)
                .param("tipoUsuario", tipoUsuario)
                .query(Integer.class)
                .single();
    }

    public Integer  validaExisteEmail(String email) {
        return jdbcClient
                .sql("SELECT COUNT(*) FROM usuarios WHERE email = :email")
                .param("email", email)
                .query(Integer.class)
                .single();
    }

    public Integer  atualizaLoginSenha(ValidaUsuarioDTO validaUsuarioDTO, String tipoUsuario) {
        return jdbcClient
                .sql("UPDATE usuarios SET login = :login, senha = :senha, dataUltimaAlteracao = CURRENT_DATE WHERE email = :email AND codigoTipoUsuario = :tipoUsuario")
                .param("login", validaUsuarioDTO.getLogin())
                .param("senha", validaUsuarioDTO.getSenha())
                .param("email", validaUsuarioDTO.getEmail())
                .param("tipoUsuario", tipoUsuario)
                .update();
    }

    public Integer atualizaDetalhesUsuario(AtualizaDetalhesClienteDTO atualizaDetalhesClienteDTO, String email, String tipoUsuario) {
        return jdbcClient
                .sql("UPDATE usuarios SET nome = :nome, logradouro = :logradouro, numero = :numero, cidade = :cidade, estado = :estado, cep = :cep, complemento = :complemento, dataUltimaAlteracao = CURRENT_TIMESTAMP WHERE email = :email AND codigoTipoUsuario = :tipoUsuario")
                .param("nome", atualizaDetalhesClienteDTO.getNome())
                .param("numero", atualizaDetalhesClienteDTO.getNumero())
                .param("logradouro", atualizaDetalhesClienteDTO.getLogradouro())
                .param("cidade", atualizaDetalhesClienteDTO.getCidade())
                .param("estado", atualizaDetalhesClienteDTO.getEstado())
                .param("cep", atualizaDetalhesClienteDTO.getCep())
                .param("complemento", atualizaDetalhesClienteDTO.getComplemento())
                .param("email", email)
                .param("tipoUsuario", tipoUsuario)
                .update();
    }

    public Integer delete(String email, String tipoUsuario) {
        return jdbcClient
                .sql("DELETE FROM usuarios WHERE email = :email AND codigoTipoUsuario = :tipoUsuario")
                .param("email", email)
                .param("tipoUsuario", tipoUsuario)
                .update();
    }
}
