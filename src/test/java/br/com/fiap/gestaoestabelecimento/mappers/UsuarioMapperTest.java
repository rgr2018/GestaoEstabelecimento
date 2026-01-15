package br.com.fiap.gestaoestabelecimento.mappers;

import br.com.fiap.gestaoestabelecimento.domain.Usuario;
import br.com.fiap.gestaoestabelecimento.dtos.AtualizaUsuarioDTO;
import br.com.fiap.gestaoestabelecimento.dtos.IncluiUsuarioDTO;
import br.com.fiap.gestaoestabelecimento.infrastructure.entities.EnderecoEntity;
import br.com.fiap.gestaoestabelecimento.infrastructure.entities.UsuarioEntity;
import br.com.fiap.gestaoestabelecimento.infrastructure.mappers.UsuarioMapper;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioMapperTest {

    @Test
    void toEntity_deveConverterIncluiUsuarioDTOParaUsuarioEntity() {
        IncluiUsuarioDTO dto = mock(IncluiUsuarioDTO.class);

        when(dto.nome()).thenReturn("João");
        when(dto.email()).thenReturn("joao@email.com");
        when(dto.login()).thenReturn("joao123");
        when(dto.senha()).thenReturn("senha123");

        UsuarioEntity entity = UsuarioMapper.toEntity(dto, "CLIENTE");

        assertNotNull(entity);
        assertEquals("João", entity.getNome());
        assertEquals("joao@email.com", entity.getEmail());
        assertEquals("joao123", entity.getLogin());
        assertEquals("senha123", entity.getSenha());
        assertEquals("CLIENTE", entity.getTipoUsuario());
        assertNotNull(entity.getDataUltimaAlteracao());
    }

    @Test
    void toEntity_deveClonarUsuarioEntity() {
        UsuarioEntity original = new UsuarioEntity();
        UUID id = UUID.randomUUID();

        original.setIdUsuario(id);
        original.setNome("Maria");
        original.setEmail("maria@email.com");
        original.setLogin("maria123");
        original.setSenha("senha");
        original.setTipoUsuario("DONO");

        UsuarioEntity clone = UsuarioMapper.toEntity(original);

        assertNotNull(clone);
        assertEquals(id, clone.getIdUsuario());
        assertEquals("Maria", clone.getNome());
        assertEquals("maria@email.com", clone.getEmail());
        assertEquals("maria123", clone.getLogin());
        assertEquals("senha", clone.getSenha());
        assertEquals("DONO", clone.getTipoUsuario());
        assertNotNull(clone.getDataUltimaAlteracao());
    }

    @Test
    void toEntityAtualiza_deveAtualizarUsuarioComEndereco() {
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        AtualizaUsuarioDTO dto = mock(AtualizaUsuarioDTO.class);
        EnderecoEntity endereco = new EnderecoEntity();

        when(dto.nome()).thenReturn("Carlos");
        when(dto.email()).thenReturn("carlos@email.com");
        when(dto.login()).thenReturn("carlos123");
        when(dto.senha()).thenReturn("novaSenha");

        UsuarioEntity atualizado =
                UsuarioMapper.toEntityAtualiza(usuarioEntity, endereco, dto, "ADMIN");

        assertNotNull(atualizado);
        assertEquals("Carlos", atualizado.getNome());
        assertEquals("carlos@email.com", atualizado.getEmail());
        assertEquals("carlos123", atualizado.getLogin());
        assertEquals("novaSenha", atualizado.getSenha());
        assertEquals("ADMIN", atualizado.getTipoUsuario());
        assertEquals(endereco, atualizado.getEnderecoEntity());
        assertNotNull(atualizado.getDataUltimaAlteracao());
    }

    @Test
    void toDomain_deveConverterUsuarioEntityComEndereco() {
        UsuarioEntity entity = new UsuarioEntity();
        UUID id = UUID.randomUUID();

        entity.setIdUsuario(id);
        entity.setNome("Ana");
        entity.setEmail("ana@email.com");
        entity.setLogin("ana123");
        entity.setSenha("senha");
        entity.setTipoUsuario("CLIENTE");
        entity.setDataUltimaAlteracao("2026-01-07T10:00");

        EnderecoEntity endereco = new EnderecoEntity();
        endereco.setLogradouro("Rua A");
        entity.setEnderecoEntity(endereco);

        Usuario domain = UsuarioMapper.toDomain(entity);

        assertNotNull(domain);
        assertEquals(id, domain.getIdUsuario());
        assertEquals("Ana", domain.getNome());
        assertEquals("ana@email.com", domain.getEmail());
        assertEquals("ana123", domain.getLogin());
        assertEquals("senha", domain.getSenha());
        assertEquals("CLIENTE", domain.getTipoUsuario());
        assertNotNull(domain.getEndereco());
    }

    @Test
    void toDomain_deveConverterUsuarioEntitySemEndereco() {
        UsuarioEntity entity = new UsuarioEntity();
        entity.setIdUsuario(UUID.randomUUID());
        entity.setNome("Pedro");

        Usuario domain = UsuarioMapper.toDomain(entity);

        assertNotNull(domain);
        assertEquals("Pedro", domain.getNome());
        assertNull(domain.getEndereco());
    }
}
