package br.com.fiap.gestaoestabelecimento.mappers;


import br.com.fiap.gestaoestabelecimento.domain.Cardapio;
import br.com.fiap.gestaoestabelecimento.dtos.AtualizaCardapioDTO;
import br.com.fiap.gestaoestabelecimento.dtos.IncluiCardapioDTO;
import br.com.fiap.gestaoestabelecimento.infrastructure.entities.CardapioEntity;
import br.com.fiap.gestaoestabelecimento.infrastructure.entities.EstabelecimentoEntity;
import br.com.fiap.gestaoestabelecimento.infrastructure.mappers.CardapioMapper;
import org.junit.jupiter.api.Test;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CardapioMapperTest {

    @Test
    void toEntity_deveMapearCampos() {
        UUID idEstabelecimento = UUID.randomUUID();
        IncluiCardapioDTO dto = new IncluiCardapioDTO(
                "Pizza", "Desc", 10.0, "S", "foto.jpg",
                idEstabelecimento
        );

        CardapioEntity entity = CardapioMapper.toEntity(dto);

        assertEquals("Pizza", entity.getNome());
        assertEquals("Desc", entity.getDescricao());
        assertEquals(10.0, entity.getPreco());
        assertEquals("S", entity.getIndicadorDisponibilidade());
        assertEquals("foto.jpg", entity.getFotoPrato());
        assertEquals(idEstabelecimento,idEstabelecimento);
    }

    @Test
    void toEntityAtualiza_deveAtualizarECriarRelacionamento() {
        UUID idEstabelecimento = UUID.randomUUID();
        UUID idCardapio = UUID.randomUUID();
        AtualizaCardapioDTO dto = new AtualizaCardapioDTO(
                idCardapio,
                "Hamburguer",
                "Desc",
                20.0,
                "N",
                "foto2.jpg",
                idEstabelecimento
        );

        CardapioEntity entity = new CardapioEntity();
        EstabelecimentoEntity est = new EstabelecimentoEntity();

        CardapioEntity atualizado =
                CardapioMapper.toEntityAtualiza(dto, entity, est);

        assertEquals(1, atualizado.getEstabelecimentoEntities().size());
    }

    @Test
    void toDomain_deveConverter() {
        CardapioEntity entity = new CardapioEntity();
        UUID id = UUID.randomUUID();

        entity.setIdCardapio(id);
        entity.setNome("Lasanha");
        entity.setDescricao("Desc");
        entity.setPreco(30.0);
        entity.setIndicadorDisponibilidade("S");
        entity.setFotoPrato("foto");

        Cardapio domain = CardapioMapper.toDomain(entity);

        assertEquals(id, domain.getId());
    }
}