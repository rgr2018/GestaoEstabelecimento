package br.com.fiap.gestaoestabelecimento.mappers;

import br.com.fiap.gestaoestabelecimento.domain.Dono;
import br.com.fiap.gestaoestabelecimento.dtos.IncluiDonoDTO;
import br.com.fiap.gestaoestabelecimento.infrastructure.entities.DonoEntity;
import br.com.fiap.gestaoestabelecimento.infrastructure.entities.EstabelecimentoEntity;
import br.com.fiap.gestaoestabelecimento.infrastructure.entities.UsuarioEntity;
import br.com.fiap.gestaoestabelecimento.infrastructure.mappers.DonoMapper;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class DonoMapperTest {

    @Test
    void toEntity_deveCriarDonoComEstabelecimento() {
        IncluiDonoDTO dto = mock(IncluiDonoDTO.class);
        EstabelecimentoEntity estabelecimento = new EstabelecimentoEntity();

        DonoEntity entity = DonoMapper.toEntity(dto, estabelecimento);

        assertNotNull(entity);
        assertNotNull(entity.getEstabelecimentoEntities());
        assertEquals(1, entity.getEstabelecimentoEntities().size());
        assertTrue(entity.getEstabelecimentoEntities().contains(estabelecimento));
    }

    @Test
    void toEntityAtualiza_deveAssociarUsuarioEAdicionarEstabelecimento() {
        DonoEntity donoEntity = new DonoEntity();
        donoEntity.setEstabelecimentoEntities(new HashSet<>());

        EstabelecimentoEntity estabelecimento = new EstabelecimentoEntity();
        UsuarioEntity usuario = new UsuarioEntity();

        DonoEntity atualizado =
                DonoMapper.toEntityAtualiza(donoEntity, estabelecimento, usuario);

        assertNotNull(atualizado);
        assertEquals(usuario, atualizado.getUsuarioEntity());
        assertTrue(atualizado.getEstabelecimentoEntities().contains(estabelecimento));
    }

    @Test
    void toDomain_deveConverterComUsuario() {
        DonoEntity entity = new DonoEntity();
        UUID idDono = UUID.randomUUID();
        entity.setIdDono(idDono);

        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setIdUsuario(UUID.randomUUID());
        usuario.setNome("Dono Teste");

        entity.setUsuarioEntity(usuario);

        Dono dono = DonoMapper.toDomain(entity);

        assertNotNull(dono);
        assertEquals(idDono, dono.getIdDono());
        assertNotNull(dono.getIdUsuario());
    }

}