package br.com.fiap.gestaoestabelecimento.repositories;

import br.com.fiap.gestaoestabelecimento.dtos.IncluiDonoDTO;
import br.com.fiap.gestaoestabelecimento.entities.Dono;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
public class DonoRepositoryImp extends UsuarioRepository<Dono> implements DonoRepository {

    protected DonoRepositoryImp(JdbcClient jdbcClient) {
        super(jdbcClient);
    }

    @Override
    public Integer salvarDonoUsuario(IncluiDonoDTO dono, Long id, String tipoUsuario) {
        return jdbcClient
                .sql("""
                        INSERT INTO usuarios 
                        (id , nome, email, login, senha, codigoTipoUsuario,  logradouro, numero, cidade, estado, cep, complemento, dataUltimaAlteracao)
                        VALUES (:id, :nome, :email, :login, :senha, :codigoTipoUsuario, :logradouro, :numero, :cidade, :estado, :cep, :complemento, CURRENT_TIMESTAMP)
                        """)
                .param("id", id)
                .param("nome", dono.getNome())
                .param("email", dono.getEmail())
                .param("login", dono.getLogin())
                .param("senha", dono.getSenha())
                .param("codigoTipoUsuario", tipoUsuario)
                .param("logradouro", dono.getLogradouro())
                .param("numero", dono.getNumero())
                .param("cidade", dono.getCidade())
                .param("estado", dono.getEstado())
                .param("cep", dono.getCep())
                .param("complemento", dono.getComplemento())
                .update();
    }


    @Override
    public Integer salvaDono(IncluiDonoDTO dono, Long id)  {
        return jdbcClient
                .sql("""
                        INSERT INTO donos_restaurantes 
                        (id, nome_estabelecimento, tipo_estabelecimento)
                        VALUES (:id, :nomeEstabelecimento, :tipoEstabelecimento)
                        """)
                .param("id", id)
                .param("nomeEstabelecimento", dono.getNomeEstabelecimento())
                .param("tipoEstabelecimento", dono.getTipoEstabelecimento())
                .update();
    }

    @Override
    public Integer atualizaDetalhesDono(Dono dono, Long id) {
        return jdbcClient
                .sql("UPDATE donos_restaurantes SET nome_estabelecimento = :nomeEstabelecimento, tipo_estabelecimento = :tipoEstabelecimento WHERE id = :id")
                .param("nomeEstabelecimento", dono.getNomeEstabelecimento())
                .param("tipoEstabelecimento", dono.getTipoEstabelecimento())
                .param("id",id)
                .update();
    }

}