package br.com.fiap.gestaoestabelecimento.mappers;

import br.com.fiap.gestaoestabelecimento.domain.Cliente;
import br.com.fiap.gestaoestabelecimento.dtos.AtualizaClienteDTO;
import br.com.fiap.gestaoestabelecimento.dtos.IncluiClienteDTO;
import br.com.fiap.gestaoestabelecimento.infrastructure.entities.ClienteEntity;
import br.com.fiap.gestaoestabelecimento.infrastructure.entities.UsuarioEntity;
import br.com.fiap.gestaoestabelecimento.infrastructure.mappers.ClienteMapper;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClienteMapperTest {

    @Test
    void toEntity_deveConverterIncluiClienteDTO() {
        IncluiClienteDTO dto = mock(IncluiClienteDTO.class);
        String aniversario = (String.valueOf(LocalDate.of(1990, 5, 20)));

        when(dto.dataAniversario()).thenReturn(String.valueOf(aniversario));

        ClienteEntity entity = ClienteMapper.toEntity(dto);

        assertNotNull(entity);
        assertEquals(aniversario, entity.getDataAniversario());
    }

    @Test
    void toEntityAtualiza_deveAtualizarClienteEAssociarUsuario() {
        AtualizaClienteDTO dto = mock(AtualizaClienteDTO.class);
        ClienteEntity clienteEntity = new ClienteEntity();
        UsuarioEntity usuarioEntity = new UsuarioEntity();

        String novoAniversario = (String.valueOf(LocalDate.of(1995, 8, 10)));
        when(dto.dataAniversario()).thenReturn(String.valueOf(novoAniversario));

        ClienteEntity atualizado =
                ClienteMapper.toEntityAtualiza(clienteEntity, usuarioEntity, dto);

        assertNotNull(atualizado);
        assertEquals(novoAniversario, atualizado.getDataAniversario());
        assertEquals(usuarioEntity, atualizado.getUsuarioEntity());
    }

    @Test
    void toDomain_deveConverterEntityComUsuario() {
        UUID idCliente = UUID.randomUUID();
        String aniversario = (String.valueOf(LocalDate.of(1988, 3, 15)));

        ClienteEntity entity = new ClienteEntity();
        entity.setIdCliente(idCliente);
        entity.setDataAniversario(String.valueOf(aniversario));

        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setIdUsuario(UUID.randomUUID());
        usuario.setNome("Cliente Teste");

        entity.setUsuarioEntity(usuario);

        Cliente cliente = ClienteMapper.toDomain(entity);

        assertNotNull(cliente);
        assertEquals(idCliente, cliente.getIdCliente());
        assertEquals(aniversario, cliente.getDataAniversario());
        assertNotNull(cliente.getIdUsuario());
    }

    @Test
    void toDomainAtualiza_deveConverterUsandoUsuarioInformado() {
        UUID idCliente = UUID.randomUUID();
        String aniversario = (String.valueOf(LocalDate.of(1992, 12, 25)));

        ClienteEntity entity = new ClienteEntity();
        entity.setIdCliente(idCliente);
        entity.setDataAniversario(String.valueOf(aniversario));

        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setIdUsuario(UUID.randomUUID());
        usuario.setNome("Usuário Atualizado");

        Cliente cliente = ClienteMapper.toDomainAtualiza(entity, usuario);

        assertNotNull(cliente);
        assertEquals(idCliente, cliente.getIdCliente());
        assertEquals(aniversario, cliente.getDataAniversario());
        assertNotNull(cliente.getIdUsuario());
    }
}