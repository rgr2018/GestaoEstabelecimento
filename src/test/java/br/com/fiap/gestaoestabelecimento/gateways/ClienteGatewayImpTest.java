package br.com.fiap.gestaoestabelecimento.gateways;

import br.com.fiap.gestaoestabelecimento.adapters.gateways.ClienteGatewayImp;
import br.com.fiap.gestaoestabelecimento.domain.Cliente;
import br.com.fiap.gestaoestabelecimento.dtos.*;
import br.com.fiap.gestaoestabelecimento.exceptions.BusinessException;
import br.com.fiap.gestaoestabelecimento.exceptions.ValidationException;
import br.com.fiap.gestaoestabelecimento.infrastructure.entities.*;
import br.com.fiap.gestaoestabelecimento.infrastructure.repositories.ClienteRepository;
import br.com.fiap.gestaoestabelecimento.infrastructure.repositories.EnderecoRepository;
import br.com.fiap.gestaoestabelecimento.infrastructure.repositories.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClienteGatewayImpTest {

    @Mock
    private ClienteRepository clienteRepository;
    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private EnderecoRepository enderecoRepository;
    @InjectMocks
    private ClienteGatewayImp clienteGateway;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void buscarClientePorNome_deveRetornarCliente() {

        UUID idUsuario = UUID.randomUUID();

        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setIdUsuario(idUsuario);
        usuarioEntity.setNome("Joao");

        ClienteEntity clienteEntity = new ClienteEntity();
        clienteEntity.setUsuarioEntity(usuarioEntity);

        when(clienteRepository.buscarClientesPorNome(any(), any()))
                .thenReturn((List.of(clienteEntity)));

        Iterable<Cliente> cliente = clienteGateway.buscarClientePorNome("Joao");

        assertNotNull(cliente);
        List<Cliente> clientes = StreamSupport
                .stream(cliente.spliterator(), false)
                .toList();

        assertEquals(1, clientes.size());
        assertEquals("Joao", clientes.get(0).getNome());

        verify(clienteRepository).buscarClientesPorNome("Joao","CLIENTE");
    }

    @Test
    void buscarClientePorNome_deveLancarExcecaoQuandoListaVazia() {
        when(clienteRepository.buscarClientesPorNome(any(), any()))
                .thenReturn(List.of());

        BusinessException ex = assertThrows(
                BusinessException.class,
                () -> clienteGateway.buscarClientePorNome("Joao")
        );

        assertEquals("Nenhum cliente encontrado com nome Joao", ex.getMessage());
    }

    @Test
    void buscarClientePorEmail_deveLancarExcecaoQuandoClienteNaoExiste() {
        when(clienteRepository.buscarClientePorEmail(any(), any()))
                .thenReturn(null);

        BusinessException ex = assertThrows(
                BusinessException.class,
                () -> clienteGateway.buscarClientePorEmail("email@teste.com")
        );

        assertEquals("Nenhum cliente encontrado com nome email@teste.com", ex.getMessage());
    }

    @Test
    void buscarClientePorEmail_deveRetornarCliente() {

        UUID idUsuario = UUID.randomUUID();

        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setIdUsuario(idUsuario);
        usuarioEntity.setNome("Cliente Teste");

        ClienteEntity clienteEntity = new ClienteEntity();
        clienteEntity.setUsuarioEntity(usuarioEntity);

        when(clienteRepository.buscarClientePorEmail(any(), any()))
                .thenReturn(clienteEntity);

        Cliente cliente = clienteGateway.buscarClientePorEmail("teste@email.com");

        assertNotNull(cliente);
        assertEquals(idUsuario, cliente.getIdUsuario());
    }

    @Test
    void saveCliente_deveLancarValidationException_quandoEmailDuplicado() {
        IncluiClienteDTO dto = mock(IncluiClienteDTO.class);
        IncluiUsuarioDTO usuarioDTO = mock(IncluiUsuarioDTO.class);

        when(dto.usuarioDTO()).thenReturn(usuarioDTO);
        when(usuarioDTO.email()).thenReturn("email@test.com");
        when(usuarioRepository.validePorEmail(any())).thenReturn(1L);

        assertThrows(ValidationException.class,
                () -> clienteGateway.saveCliente(dto));
    }

    @Test
    void updateCliente_deveLancarExcecaoQuandoUsuarioDTONulo() {
        AtualizaClienteDTO dto = mock(AtualizaClienteDTO.class);

        when(dto.usuarioDTO()).thenReturn(null);

        BusinessException ex = assertThrows(
                BusinessException.class,
                () -> clienteGateway.updateCliente(dto)
        );

        assertEquals("O campo usuarioDTO não pode ser nulo", ex.getMessage());
    }

    @Test
    void updateCliente_deveLancarExcecaoQuandoEnderecoNulo() {
        AtualizaClienteDTO dto = mock(AtualizaClienteDTO.class);
        AtualizaUsuarioDTO usuarioDTO = mock(AtualizaUsuarioDTO.class);

        when(dto.usuarioDTO()).thenReturn(usuarioDTO);
        when(usuarioDTO.endereco()).thenReturn(null);

        BusinessException ex = assertThrows(
                BusinessException.class,
                () -> clienteGateway.updateCliente(dto)
        );

        assertEquals("O campo endereco dentro de usuarioDTO não pode ser nulo", ex.getMessage());
    }

    @Test
    void updateCliente_deveLancarExcecaoQuandoClienteNaoEncontrado() {
        AtualizaClienteDTO dto = mock(AtualizaClienteDTO.class);
        AtualizaUsuarioDTO usuarioDTO = mock(AtualizaUsuarioDTO.class);
        AtualizaEnderecoDTO enderecoDTO = mock(AtualizaEnderecoDTO.class);

        UUID idCliente = UUID.randomUUID();

        when(dto.usuarioDTO()).thenReturn(usuarioDTO);
        when(usuarioDTO.endereco()).thenReturn(enderecoDTO);
        when(dto.idCliente()).thenReturn(idCliente);

        when(clienteRepository.findById(idCliente))
                .thenReturn(Optional.empty());

        BusinessException ex = assertThrows(
                BusinessException.class,
                () -> clienteGateway.updateCliente(dto)
        );

        assertEquals("Cliente não encontrado", ex.getMessage());
    }

    @Test
    void updateCliente_deveLancarExcecaoQuandoUsuarioNaoEncontrado() {
        AtualizaClienteDTO dto = mock(AtualizaClienteDTO.class);
        AtualizaUsuarioDTO usuarioDTO = mock(AtualizaUsuarioDTO.class);
        AtualizaEnderecoDTO enderecoDTO = mock(AtualizaEnderecoDTO.class);

        UUID idCliente = UUID.randomUUID();
        UUID idUsuario = UUID.randomUUID();

        when(dto.idCliente()).thenReturn(idCliente);
        when(dto.usuarioDTO()).thenReturn(usuarioDTO);
        when(usuarioDTO.idUsuario()).thenReturn(idUsuario);
        when(usuarioDTO.endereco()).thenReturn(enderecoDTO);

        when(clienteRepository.findById(idCliente))
                .thenReturn(Optional.of(new ClienteEntity()));
        when(usuarioRepository.findById(idUsuario))
                .thenReturn(Optional.empty());

        BusinessException ex = assertThrows(
                BusinessException.class,
                () -> clienteGateway.updateCliente(dto)
        );

        assertEquals("Usuário não encontrado", ex.getMessage());
    }

    @Test
    void updateCliente_deveLancarExcecaoQuandoEnderecoNaoEncontrado() {
        AtualizaClienteDTO dto = mock(AtualizaClienteDTO.class);
        AtualizaUsuarioDTO usuarioDTO = mock(AtualizaUsuarioDTO.class);
        AtualizaEnderecoDTO enderecoDTO = mock(AtualizaEnderecoDTO.class);

        UUID idCliente = UUID.randomUUID();
        UUID idUsuario = UUID.randomUUID();
        UUID idEndereco = UUID.randomUUID();

        when(dto.idCliente()).thenReturn(idCliente);
        when(dto.usuarioDTO()).thenReturn(usuarioDTO);
        when(usuarioDTO.idUsuario()).thenReturn(idUsuario);
        when(usuarioDTO.endereco()).thenReturn(enderecoDTO);
        when(enderecoDTO.idEndereco()).thenReturn(idEndereco);

        when(clienteRepository.findById(idCliente))
                .thenReturn(Optional.of(new ClienteEntity()));
        when(usuarioRepository.findById(idUsuario))
                .thenReturn(Optional.of(new UsuarioEntity()));
        when(enderecoRepository.findById(idEndereco))
                .thenReturn(Optional.empty());

        BusinessException ex = assertThrows(
                BusinessException.class,
                () -> clienteGateway.updateCliente(dto)
        );

        assertEquals("Endereço não encontrado", ex.getMessage());
    }
    @Test
    void updateCliente_deveAtualizarClienteComSucesso() {
        AtualizaClienteDTO dto = mock(AtualizaClienteDTO.class);
        AtualizaUsuarioDTO usuarioDTO = mock(AtualizaUsuarioDTO.class);
        AtualizaEnderecoDTO enderecoDTO = mock(AtualizaEnderecoDTO.class);

        UUID idCliente = UUID.randomUUID();
        UUID idUsuario = UUID.randomUUID();
        UUID idEndereco = UUID.randomUUID();

        ClienteEntity clienteEntity = new ClienteEntity();
        clienteEntity.setIdCliente(idCliente);

        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setIdUsuario(idUsuario);

        EnderecoEntity enderecoEntity = new EnderecoEntity();
        enderecoEntity.setIdEndereco(idEndereco);

        when(dto.idCliente()).thenReturn(idCliente);

        when(dto.usuarioDTO()).thenReturn(usuarioDTO);
        when(usuarioDTO.idUsuario()).thenReturn(idUsuario);

        when(usuarioDTO.endereco()).thenReturn(enderecoDTO);
        when(enderecoDTO.idEndereco()).thenReturn(idEndereco);

        when(clienteRepository.findById(idCliente))
                .thenReturn(Optional.of(clienteEntity));

        when(usuarioRepository.findById(idUsuario))
                .thenReturn(Optional.of(usuarioEntity));

        when(enderecoRepository.findById(idEndereco))
                .thenReturn(Optional.of(enderecoEntity));

        Cliente resultado = clienteGateway.updateCliente(dto);

        assertNotNull(resultado);
        assertEquals(idCliente, resultado.getIdCliente());
    }

    @Test
    void getCliente_deveLancarExcecao_quandoIdNulo() {
        assertThrows(BusinessException.class,
                () -> clienteGateway.getCliente(null));
    }

    @Test
    void getCliente_deveLancarExcecaoQuandoClienteNaoEncontrado() {
        UUID id = UUID.randomUUID();

        when(clienteRepository.buscaClientePorId(id))
                .thenReturn(null);

        BusinessException ex = assertThrows(
                BusinessException.class,
                () -> clienteGateway.getCliente(id)
        );

        assertEquals("Nenhum cliente encontrado com nome " + id, ex.getMessage());
    }
    @Test
    void listaTodosClientes_deveRetornarListaDeClientes() {

        UsuarioEntity usuario = mock(UsuarioEntity.class);
        ClienteEntity clienteEntity = new ClienteEntity();
        clienteEntity.setIdCliente(UUID.randomUUID());
        clienteEntity.setUsuarioEntity(usuario);

        when(clienteRepository.listaTodosClientes())
                .thenReturn(List.of(clienteEntity));

        Iterable<Cliente> resultado = clienteGateway.listaTodosClientes();

        List<Cliente> clientes = StreamSupport
                .stream(resultado.spliterator(), false)
                .toList();

        assertEquals(1, clientes.size());
    }
    @Test
    void listaTodosClientes_deveLancarExcecaoQuandoListaVazia() {
        when(clienteRepository.listaTodosClientes())
                .thenReturn(List.of());

        BusinessException ex = assertThrows(
                BusinessException.class,
                () -> clienteGateway.listaTodosClientes()
        );

        assertEquals("Nenhum cliente encontrado", ex.getMessage());
    }


    @Test
    void deleteCliente_deveExcluirComSucesso() {
        when(usuarioRepository.validePorEmail("teste@email.com"))
                .thenReturn(1L);

        UUID idUsuario = UUID.randomUUID();
        UUID idEndereco = UUID.randomUUID();
        UUID idCliente = UUID.randomUUID();

        when(usuarioRepository.buscaIdPorEmail(any(), any()))
                .thenReturn(idUsuario);
        when(usuarioRepository.buscaIdEnderecoPorEmail(any()))
                .thenReturn(idEndereco);
        when(usuarioRepository.buscaIdclientePoridUsuario(any()))
                .thenReturn(idCliente);

        assertDoesNotThrow(() ->
                clienteGateway.deleteCliente("teste@email.com"));

        verify(enderecoRepository).deleteById(idEndereco);
        verify(clienteRepository).deleteById(idCliente);
        verify(usuarioRepository).deleteById(idUsuario);
    }
    @Test
    void deleteCliente_deveLancarExcecaoQuandoEmailNulo() {
        BusinessException ex = assertThrows(
                BusinessException.class,
                () -> clienteGateway.deleteCliente(null)
        );

        assertEquals("O campo usuarioDTO não pode ser nulo", ex.getMessage());
    }
    @Test
    void deleteCliente_deveLancarValidationExceptionQuandoEmailNaoExiste() {
        when(usuarioRepository.validePorEmail(any()))
                .thenReturn(0L);

        ValidationException ex = assertThrows(
                ValidationException.class,
                () -> clienteGateway.deleteCliente("email@teste.com")
        );

        assertEquals("Email não encontrado", ex.getMessage());
    }
}