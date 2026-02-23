package br.com.fiap.gestaoestabelecimento.usecases;
import br.com.fiap.gestaoestabelecimento.domain.ClienteUseCase;
import br.com.fiap.gestaoestabelecimento.domain.*;
import br.com.fiap.gestaoestabelecimento.dtos.*;
import br.com.fiap.gestaoestabelecimento.interfaces.ClienteGateway;
import br.com.fiap.gestaoestabelecimento.interfaces.UsuarioGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteUseCaseTest {

    @Mock
    private ClienteGateway clienteGateway;

    @Mock
    private UsuarioGateway usuarioGateway;

    @InjectMocks
    private ClienteUseCase clienteUseCase;

    // -------------------------------------------------------------------------
    // incluirClienteUseCase
    // -------------------------------------------------------------------------
    @Test
    void incluirClienteUseCase_deveSalvarCliente() {
        IncluiClienteDTO dto = mock(IncluiClienteDTO.class);
        Cliente cliente = mock(Cliente.class);

        when(clienteGateway.saveCliente(any(IncluiClienteDTO.class)))
                .thenReturn(cliente);

        clienteUseCase.incluirClienteUseCase(dto);

        verify(clienteGateway).saveCliente(dto);
    }

    // -------------------------------------------------------------------------
    // atualizaLogin
    // -------------------------------------------------------------------------
    @Test
    void atualizaLogin_deveCriarDTOEAtualizarLoginSenha() {
        AtualizaLoginSenhaDTO input = mock(AtualizaLoginSenhaDTO.class);

        when(input.email()).thenReturn("cliente@email.com");
        when(input.login()).thenReturn("loginNovo");
        when(input.senha()).thenReturn("senhaNova");

        ArgumentCaptor<AtualizaLoginSenhaUsuarioDTO> captor =
                ArgumentCaptor.forClass(AtualizaLoginSenhaUsuarioDTO.class);

        doNothing().when(usuarioGateway).atualizarLoginSenha(any());

        assertDoesNotThrow(() -> clienteUseCase.atualizaLogin(input));

        verify(usuarioGateway, times(1)).atualizarLoginSenha(captor.capture());

        AtualizaLoginSenhaUsuarioDTO dtoGerado = captor.getValue();

        assertEquals("cliente@email.com", dtoGerado.email());
        assertEquals("loginNovo", dtoGerado.login());
        assertEquals("senhaNova", dtoGerado.senha());
        assertEquals("CLIENTE", dtoGerado.tipoUsuario());
    }

    // -------------------------------------------------------------------------
    // atualizarClienteUserCase
    // -------------------------------------------------------------------------
    @Test
    void atualizarClienteUserCase_deveAtualizarERetornarCliente() {
        AtualizaClienteDTO dto = mock(AtualizaClienteDTO.class);
        Cliente cliente = mock(Cliente.class);

        when(clienteGateway.updateCliente(dto)).thenReturn(cliente);

        Cliente resultado = clienteUseCase.atualizarClienteUserCase(dto);

        assertNotNull(resultado);
        assertEquals(cliente, resultado);
        verify(clienteGateway, times(1)).updateCliente(dto);
    }

    // -------------------------------------------------------------------------
    // buscarClienteIdUseCase
    // -------------------------------------------------------------------------
    @Test
    void buscarClienteIdUseCase_deveRetornarCliente() {
        UUID id = UUID.randomUUID();
        Cliente cliente = mock(Cliente.class);

        when(clienteGateway.getCliente(id)).thenReturn(cliente);

        Cliente resultado = clienteUseCase.buscarClienteIdUseCase(id);

        assertNotNull(resultado);
        assertEquals(cliente, resultado);
        verify(clienteGateway, times(1)).getCliente(id);
    }

    // -------------------------------------------------------------------------
    // validaAcesso
    // -------------------------------------------------------------------------
    @Test
    void validaAcesso_deveDelegarParaUsuarioGateway() {
        ValidaUsuarioDTO dto = mock(ValidaUsuarioDTO.class);

        when(dto.login()).thenReturn("login");
        when(dto.senha()).thenReturn("senha");
        when(usuarioGateway.validaAcessoUsuario("login", "senha"))
                .thenReturn("CLIENTE");

        String resultado = clienteUseCase.validaAcesso(dto);

        assertEquals("CLIENTE", resultado);
        verify(usuarioGateway, times(1))
                .validaAcessoUsuario("login", "senha");
    }

    // -------------------------------------------------------------------------
    // buscarClienteEmailUseCase
    // -------------------------------------------------------------------------
    @Test
    void buscarClienteEmailUseCase_deveBuscarPorEmail() {
        String email = "cliente@email.com";
        Cliente cliente = mock(Cliente.class);

        when(clienteGateway.buscarClientePorEmail(email)).thenReturn(cliente);

        Cliente resultado = clienteUseCase.buscarClienteEmailUseCase(email);

        assertNotNull(resultado);
        assertEquals(cliente, resultado);
        verify(clienteGateway, times(1)).buscarClientePorEmail(email);
    }

    // -------------------------------------------------------------------------
    // buscarClienteNomeUseCase
    // -------------------------------------------------------------------------
    @Test
    void buscarClienteNomeUseCase_deveBuscarPorNome() {
        String nome = "João";
        List<Cliente> clientes = List.of(mock(Cliente.class));

        when(clienteGateway.buscarClientePorNome(nome)).thenReturn(clientes);

        Iterable<Cliente> resultado = clienteUseCase.buscarClienteNomeUseCase(nome);

        assertNotNull(resultado);
        assertEquals(clientes, resultado);
        verify(clienteGateway, times(1)).buscarClientePorNome(nome);
    }

    // -------------------------------------------------------------------------
    // listarTodosClientesUseCase
    // -------------------------------------------------------------------------
    @Test
    void listarTodosClientesUseCase_deveRetornarLista() {
        List<Cliente> clientes = List.of(
                mock(Cliente.class),
                mock(Cliente.class)
        );

        when(clienteGateway.listaTodosClientes()).thenReturn(clientes);

        Iterable<Cliente> resultado = clienteUseCase.listarTodosClientesUseCase();

        assertNotNull(resultado);
        assertEquals(clientes, resultado);
        verify(clienteGateway, times(1)).listaTodosClientes();
    }

    // -------------------------------------------------------------------------
    // deletarClientePorEmailUseCase
    // -------------------------------------------------------------------------
    @Test
    void deletarClientePorEmailUseCase_deveExcluirCliente() {
        String email = "cliente@email.com";

        doNothing().when(clienteGateway).deleteCliente(email);

        assertDoesNotThrow(() -> clienteUseCase.deletarClientePorEmailUseCase(email));

        verify(clienteGateway, times(1)).deleteCliente(email);
    }
}