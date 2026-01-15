package br.com.fiap.gestaoestabelecimento.infrastructure.mappers;

import br.com.fiap.gestaoestabelecimento.domain.Cardapio;
import br.com.fiap.gestaoestabelecimento.dtos.AtualizaCardapioDTO;
import br.com.fiap.gestaoestabelecimento.dtos.IncluiCardapioDTO;
import br.com.fiap.gestaoestabelecimento.infrastructure.entities.CardapioEntity;
import br.com.fiap.gestaoestabelecimento.infrastructure.entities.EstabelecimentoEntity;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public class CardapioMapper {

    private CardapioMapper() {}

    public static CardapioEntity toEntity(@NotNull IncluiCardapioDTO dto) {
        CardapioEntity entity = new CardapioEntity();
        entity.setNome(dto.nome());
        entity.setDescricao(dto.descricao());
        entity.setPreco(dto.preco());
        entity.setIndicadorDisponibilidade(dto.indicadorDisponibilidade());
        entity.setFotoPrato(dto.fotoPrato());
        return entity;
    }

    public static CardapioEntity toEntityAtualiza(@NotNull AtualizaCardapioDTO dto, CardapioEntity cardapio, EstabelecimentoEntity estabelecimentoEntity) {

        cardapio.setNome(dto.nome());
        cardapio.setDescricao(dto.descricao());
        cardapio.setPreco(dto.preco());
        cardapio.setIndicadorDisponibilidade(dto.indicadorDisponibilidade());
        cardapio.setFotoPrato(dto.fotoPrato());

        Set<EstabelecimentoEntity> estabelecimentos = new HashSet<>();
        estabelecimentos.add(estabelecimentoEntity);
        cardapio.setEstabelecimentoEntities(estabelecimentos);

        return cardapio;
    }

    public static Cardapio toDomain(@NotNull CardapioEntity entity) {
        Cardapio cardapio = new Cardapio(
                entity.getNome(),
                entity.getDescricao(),
                entity.getPreco(),
                entity.getIndicadorDisponibilidade(),
                entity.getFotoPrato()
        );
        cardapio.setId(entity.getIdCardapio());
        return cardapio;
    }
}