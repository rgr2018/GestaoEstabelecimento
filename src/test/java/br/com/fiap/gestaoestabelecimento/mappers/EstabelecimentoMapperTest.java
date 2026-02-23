package br.com.fiap.gestaoestabelecimento.mappers;

import br.com.fiap.gestaoestabelecimento.domain.Estabelecimento;
import br.com.fiap.gestaoestabelecimento.dtos.AtualizaEstabelecimentoDTO;
import br.com.fiap.gestaoestabelecimento.dtos.IncluiEstabelecimentoDTO;
import br.com.fiap.gestaoestabelecimento.infrastructure.entities.*;
import br.com.fiap.gestaoestabelecimento.infrastructure.mappers.EstabelecimentoMapper;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EstabelecimentoMapperTest {

    @Test
    void toEntity_deveConverterIncluiEstabelecimentoDTOParaEntity() {
        IncluiEstabelecimentoDTO dto = mock(IncluiEstabelecimentoDTO.class);

        when(dto.nome()).thenReturn("Restaurante Teste");
        when(dto.cnpj()).thenReturn("12345678000199");
        when(dto.tipoCozinha()).thenReturn("Italiana");
        when(dto.tipoEstabelecimento()).thenReturn("Restaurante");
        when(dto.horarioAberturaDiaSemana()).thenReturn("8:00");
        when(dto.horarioFechamentoDiaSemana()).thenReturn("18:00");
        when(dto.horarioAberturaFeriadoFimSemana()).thenReturn("9:00");
        when(dto.horarioFechamentoFeriadoFimSemana()).thenReturn("16:00");

        EstabelecimentoEntity entity = EstabelecimentoMapper.toEntity(dto);

        assertNotNull(entity);
        assertNotNull(entity.getDataHoraCadastro());
        assertEquals("Restaurante Teste", entity.getNome());
        assertEquals("12345678000199", entity.getCNPJ());
        assertEquals("Italiana", entity.getTipoCozinha());
        assertEquals("Restaurante", entity.getTipoEstabelecimento());
        assertEquals("8:00", entity.getHorarioAberturaDiaSemana());
        assertEquals("18:00", entity.getHorarioFechamentoDiaSemana());
        assertEquals("9:00", entity.getHorarioAberturaFeriadoFimSemana());
        assertEquals("16:00", entity.getHorarioFechamentoFeriadoFimSemana());
    }

    @Test
    void toEntityAtualiza_deveAtualizarEstabelecimentoEntity() {
        EstabelecimentoEntity entity = new EstabelecimentoEntity();
        AtualizaEstabelecimentoDTO dto = mock(AtualizaEstabelecimentoDTO.class);

        UUID id = UUID.randomUUID();

        when(dto.idEstabelecimento()).thenReturn(id);
        when(dto.nome()).thenReturn("Restaurante Atualizado");
        when(dto.cnpj()).thenReturn("98765432000100");
        when(dto.tipoCozinha()).thenReturn("Japonesa");
        when(dto.tipoEstabelecimento()).thenReturn("Delivery");
        when(dto.horarioAberturaDiaSemana()).thenReturn("10:00");
        when(dto.horarioFechamentoDiaSemana()).thenReturn("22:00");
        when(dto.horarioAberturaFeriadoFimSemana()).thenReturn("(11:00");
        when(dto.horarioFechamentoFeriadoFimSemana()).thenReturn("20:00");

        EstabelecimentoEntity atualizado =
                EstabelecimentoMapper.toEntityAtualiza(entity, dto);

        assertNotNull(atualizado);
        assertEquals(id, atualizado.getIdEstabelecimento());
        assertNotNull(atualizado.getDataHoraCadastro());
        assertEquals("Restaurante Atualizado", atualizado.getNome());
        assertEquals("98765432000100", atualizado.getCNPJ());
        assertEquals("Japonesa", atualizado.getTipoCozinha());
        assertEquals("Delivery", atualizado.getTipoEstabelecimento());
    }

    @Test
    void toDomain_deveConverterEntityCompletaParaDomain() {
        // Arrange
        UUID id = UUID.randomUUID();

        EstabelecimentoEntity entity = new EstabelecimentoEntity();
        entity.setIdEstabelecimento(id);
        entity.setNome("Restaurante Domain");
        entity.setCNPJ("11111111000111");
        entity.setTipoCozinha("Brasileira");
        entity.setTipoEstabelecimento("Restaurante");

        EnderecoEntity endereco = new EnderecoEntity();
        endereco.setLogradouro("Rua Teste");
        entity.setEnderecoEntity(endereco);

        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setIdUsuario(UUID.randomUUID());
        usuario.setEmail("dono@email.com");

        DonoEntity dono = new DonoEntity();
        dono.setIdDono(UUID.randomUUID());
        dono.setUsuarioEntity(usuario);

        CardapioEntity cardapio = new CardapioEntity();
        cardapio.setIdCardapio(UUID.randomUUID());
        cardapio.setNome("Prato Teste");

        entity.setDonoEntities(Set.of(dono));
        entity.setCardapioEntities(Set.of(cardapio));

        // Act
        Estabelecimento domain = EstabelecimentoMapper.toDomain(entity);

        // Assert
        assertNotNull(domain);
        assertEquals(id, domain.getIdEstabelecimento());
        assertEquals("Restaurante Domain", domain.getNome());
        assertEquals("11111111000111", domain.getCnpj());

        assertNotNull(domain.getEndereco());
        assertEquals(1, domain.getDonos().size());
        assertEquals(1, domain.getCardapios().size());
    }

    @Test
    void toDomain_deveRetornarNullQuandoEntityForNull() {
        Estabelecimento domain = EstabelecimentoMapper.toDomain(null);
        assertNull(domain);
    }

    @Test
    void toDomain_deveRetornarListasVaziasQuandoNaoHouverRelacionamentos() {
        EstabelecimentoEntity entity = new EstabelecimentoEntity();
        entity.setIdEstabelecimento(UUID.randomUUID());

        Estabelecimento domain = EstabelecimentoMapper.toDomain(entity);

        assertNotNull(domain);
        assertNotNull(domain.getDonos());
        assertNotNull(domain.getCardapios());
        assertTrue(domain.getDonos().isEmpty());
        assertTrue(domain.getCardapios().isEmpty());
    }
}