package br.com.fiap.gestaoestabelecimento.infrastructure.repositories;


import br.com.fiap.gestaoestabelecimento.infrastructure.entities.EstabelecimentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


@Repository
public interface EstabelecimentoRepository
        extends JpaRepository<EstabelecimentoEntity, UUID> {

    @Query(value = """
        select distinct e
        from EstabelecimentoEntity e
        join fetch e.enderecoEntity en
        left join fetch e.donoEntities d
        left join fetch e.cardapioEntities c
        where e.idEstabelecimento = :idEstabelecimento
       """)

    EstabelecimentoEntity buscaPorId( @Param("idEstabelecimento") UUID idEstabelecimento );

    @Query(value = """
        select distinct e
        from EstabelecimentoEntity e
        join fetch e.enderecoEntity en
        left join fetch e.donoEntities d
        left join fetch e.cardapioEntities c
       """)
    List<EstabelecimentoEntity> listaEstabelecimentos();

    @Query(value = """
        select distinct e
        from EstabelecimentoEntity e
        join fetch e.enderecoEntity en
        left join fetch e.donoEntities d
        left join fetch e.cardapioEntities c
        where e.nome like concat('%', :nome, '%')
       """)
    List<EstabelecimentoEntity> buscaPorNome( @Param("nome") String nome );

}