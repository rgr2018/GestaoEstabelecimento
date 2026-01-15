package br.com.fiap.gestaoestabelecimento.infrastructure.repositories;
import br.com.fiap.gestaoestabelecimento.infrastructure.entities.DonoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DonoRepository extends JpaRepository<DonoEntity, UUID> {

    @Query(value = """
             select d
    from DonoEntity d
    join fetch d.usuarioEntity u
    left join fetch u.enderecoEntity e
        """)
    List<DonoEntity> listaTodosDonos();


    @Query(value = """
    select d
    from DonoEntity d
    join fetch d.usuarioEntity u
    left join fetch u.enderecoEntity e
    where d.idDono = :idDono
        """)
    DonoEntity buscaDonoPorId(@Param("idDono") UUID idDono);

    @Query("""
    select d
    from DonoEntity d
    join fetch d.usuarioEntity u
    left join fetch u.enderecoEntity e
    where u.nome = :nome
      and u.tipoUsuario = :tipoUsuario
""")
    List<DonoEntity> buscarDonosPorNome(
            String nome,
            String tipoUsuario
    );

    @Query("""
    select d
    from DonoEntity d
    join fetch d.usuarioEntity u
    left join fetch u.enderecoEntity e
    where u.email = :email
      and u.tipoUsuario = :tipoUsuario
""")
    DonoEntity buscarDonoPorEmail(
            String email,
            String tipoUsuario
    );

}
