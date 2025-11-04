package br.com.fiap.gestaoestabelecimento.repositories;

import br.com.fiap.gestaoestabelecimento.dtos.IncluiClienteDTO;
import br.com.fiap.gestaoestabelecimento.entities.Cliente;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;



@Repository
public class ClienteRepositoryImp extends UsuarioRepository<Cliente> implements ClienteRepository {

    public ClienteRepositoryImp(JdbcClient jdbcClient) {
        super(jdbcClient);
    }

    @Override
    public Integer atualizaDetalhesCliente(Cliente cliente, Long id) {
        return jdbcClient
                .sql("UPDATE cliente SET data_aniversario = :data_aniversario, classificacao = :classificacao WHERE id = :id")
                .param("data_aniversario", cliente.getDataAniversario())
                .param("dataCadastro", cliente.getDataCadastro())
                .param("classificacao", cliente.getClassificacao())
                .param("id",id)
                .update();
    }

    @Override
    public Integer salvarClientes(IncluiClienteDTO cliente, Long id) {
        return jdbcClient
                .sql("""
                        INSERT INTO clientes 
                        (id, data_aniversario, data_cadastro, classificacao)
                        VALUES (:id, :data_aniversario, CURRENT_TIMESTAMP, :classificacao)
                        """)
                .param("id", id)
                .param("data_aniversario", cliente.getDataAniversario())
                .param("classificacao", cliente.getClassificacao())
                .update();
    }

    @Override

    public Integer salvarUsuarioCliente(IncluiClienteDTO usuario, Long id, String tipoUsuario) {
        return jdbcClient
                .sql("""
                        INSERT INTO usuarios (id, nome, email, login, senha, codigoTipoUsuario,  logradouro, numero, cidade, estado, cep, complemento, dataUltimaAlteracao)
                        VALUES (:id, :nome, :email, :login, :senha, :codigoTipoUsuario, :logradouro, :numero, :cidade, :estado, :cep, :complemento, CURRENT_TIMESTAMP)
                        """)
                .param("id", id)
                .param("nome", usuario.getNome())
                .param("email", usuario.getEmail())
                .param("login", usuario.getLogin())
                .param("senha", usuario.getSenha())
                .param("codigoTipoUsuario", tipoUsuario)
                .param("logradouro", usuario.getLogradouro())
                .param("numero", usuario.getNumero())
                .param("cidade", usuario.getCidade())
                .param("estado", usuario.getEstado())
                .param("cep", usuario.getCep())
                .param("complemento", usuario.getComplemento())
                .update();
    }
}


