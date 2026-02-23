package br.com.fiap.gestaoestabelecimento.mappers;

import br.com.fiap.gestaoestabelecimento.domain.Endereco;
import br.com.fiap.gestaoestabelecimento.dtos.AtualizaEnderecoDTO;
import br.com.fiap.gestaoestabelecimento.dtos.IncluiEnderecoDTO;
import br.com.fiap.gestaoestabelecimento.infrastructure.entities.EnderecoEntity;
import br.com.fiap.gestaoestabelecimento.infrastructure.mappers.EnderecoMapper;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class EnderecoMapperTest {

    @Test
    void toEntity_deveConverterIncluiEnderecoDTOParaEntity() {
        IncluiEnderecoDTO dto = mock(IncluiEnderecoDTO.class);

        when(dto.logradouro()).thenReturn("Rua A");
        when(dto.numero()).thenReturn("123");
        when(dto.bairro()).thenReturn("Centro");
        when(dto.cidade()).thenReturn("São Paulo");
        when(dto.estado()).thenReturn("SP");
        when(dto.cep()).thenReturn("01000-000");
        when(dto.complemento()).thenReturn("Apto 10");

        EnderecoEntity entity = EnderecoMapper.toEntity(dto);

        assertNotNull(entity);
        assertEquals("Rua A", entity.getLogradouro());
        assertEquals("123", entity.getNumero());
        assertEquals("Centro", entity.getBairro());
        assertEquals("São Paulo", entity.getCidade());
        assertEquals("SP", entity.getEstado());
        assertEquals("01000-000", entity.getCep());
        assertEquals("Apto 10", entity.getComplemento());
    }

    @Test
    void toEntityAtualiza_deveAtualizarCamposDoEnderecoEntity() {
        EnderecoEntity entity = new EnderecoEntity();
        AtualizaEnderecoDTO dto = mock(AtualizaEnderecoDTO.class);

        when(dto.logradouro()).thenReturn("Rua B");
        when(dto.numero()).thenReturn("456");
        when(dto.bairro()).thenReturn("Bela Vista");
        when(dto.cidade()).thenReturn("Campinas");
        when(dto.estado()).thenReturn("SP");
        when(dto.cep()).thenReturn("13000-000");
        when(dto.complemento()).thenReturn("Casa");

        EnderecoEntity atualizado = EnderecoMapper.toEntityAtualiza(entity, dto);

        assertNotNull(atualizado);
        assertEquals("Rua B", atualizado.getLogradouro());
        assertEquals("456", atualizado.getNumero());
        assertEquals("Bela Vista", atualizado.getBairro());
        assertEquals("Campinas", atualizado.getCidade());
        assertEquals("SP", atualizado.getEstado());
        assertEquals("13000-000", atualizado.getCep());
        assertEquals("Casa", atualizado.getComplemento());
    }

    @Test
    void toDomain_deveConverterEnderecoEntityParaDomain() {
        EnderecoEntity entity = new EnderecoEntity();
        UUID id = UUID.randomUUID();

        entity.setIdEndereco(id);
        entity.setLogradouro("Rua C");
        entity.setNumero("789");
        entity.setBairro("Moema");
        entity.setCidade("São Paulo");
        entity.setEstado("SP");
        entity.setCep("04500-000");
        entity.setComplemento("Bloco B");

        Endereco endereco = EnderecoMapper.toDomain(entity);

        assertNotNull(endereco);
        assertEquals(id, endereco.getIdEndereco());
        assertEquals("Rua C", endereco.getLogradouro());
        assertEquals("789", endereco.getNumero());
        assertEquals("Moema", endereco.getBairro());
        assertEquals("São Paulo", endereco.getCidade());
        assertEquals("SP", endereco.getEstado());
        assertEquals("04500-000", endereco.getCep());
        assertEquals("Bloco B", endereco.getComplemento());
    }
}
