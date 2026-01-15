package br.com.fiap.gestaoestabelecimento.infrastructure.mappers;

import br.com.fiap.gestaoestabelecimento.domain.Dono;
import br.com.fiap.gestaoestabelecimento.dtos.IncluiDonoDTO;
import br.com.fiap.gestaoestabelecimento.infrastructure.entities.DonoEntity;
import br.com.fiap.gestaoestabelecimento.infrastructure.entities.EstabelecimentoEntity;
import br.com.fiap.gestaoestabelecimento.infrastructure.entities.UsuarioEntity;

import java.util.HashSet;
import java.util.Set;

public class DonoMapper {
    private DonoMapper() {}

    public static DonoEntity toEntity(IncluiDonoDTO dto, EstabelecimentoEntity estabelecimento) {
        DonoEntity entity = new DonoEntity();
        Set<EstabelecimentoEntity> estabelecimentos = new HashSet<>();
        estabelecimentos.add(estabelecimento);
        entity.setEstabelecimentoEntities(estabelecimentos);
        return entity;
    }



    public static DonoEntity toEntityAtualiza(DonoEntity donoEntity, EstabelecimentoEntity estabelecimentoEntity, UsuarioEntity usuarioEntity) {
        donoEntity.setUsuarioEntity(usuarioEntity);
        donoEntity.getEstabelecimentoEntities().add(estabelecimentoEntity);
        return donoEntity;
    }

    public static Dono toDomain(DonoEntity entity) {

        UsuarioEntity usuario = entity.getUsuarioEntity();

        return new Dono(
                entity.getIdDono(),
                usuario != null
                        ? UsuarioMapper.toDomain(usuario)
                        : null
        );
    }


}