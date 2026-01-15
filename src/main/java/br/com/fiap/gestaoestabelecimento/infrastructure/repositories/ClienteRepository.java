package br.com.fiap.gestaoestabelecimento.infrastructure.repositories;
import br.com.fiap.gestaoestabelecimento.infrastructure.entities.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteEntity, UUID> {

    @Query(value = """
            select c
    from ClienteEntity c
    join fetch c.usuarioEntity u
    left join fetch u.enderecoEntity e
        """)
    List<ClienteEntity> listaTodosClientes();


    @Query(value = """
            select c
    from ClienteEntity c
    join fetch c.usuarioEntity u
    left join fetch u.enderecoEntity e
    where c.idCliente = :idCliente
        """)
    ClienteEntity buscaClientePorId(@Param("idCliente") UUID idCliente);

    @Query("""
    select c
    from ClienteEntity c
    join fetch c.usuarioEntity u
    left join fetch u.enderecoEntity e
    where u.nome = :nome
      and u.tipoUsuario = :tipoUsuario
""")
    List<ClienteEntity> buscarClientesPorNome(
            String nome,
            String tipoUsuario
    );

    @Query("""
    select  c
    from ClienteEntity c
    join fetch c.usuarioEntity u
    left join fetch u.enderecoEntity e
    where u.email = :email
      and u.tipoUsuario = :tipoUsuario
""")
    ClienteEntity buscarClientePorEmail(
            String email,
            String tipoUsuario
    );

}

