package br.com.fiap.gestaoestabelecimento.controlles;
import br.com.fiap.gestaoestabelecimento.adapters.controllers.ClienteController;
import br.com.fiap.gestaoestabelecimento.domain.ClienteUseCase;
import br.com.fiap.gestaoestabelecimento.domain.Cliente;
import br.com.fiap.gestaoestabelecimento.dtos.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ClienteControllerTest {

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private ClienteUseCase clienteUseCase;

    @InjectMocks
    private ClienteController clienteController;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(clienteController)
                .build();
    }

    // -------------------------------------------------------------------------
    // GET /buscar-por-nome
    // -------------------------------------------------------------------------
    @Test
    void buscarPorNome_deveRetornarClientes() throws Exception {
        String nome = "João";
        Iterable<Cliente> clientes = List.of(mock(Cliente.class));

        when(clienteUseCase.buscarClienteNomeUseCase(nome))
                .thenReturn(clientes);

        mockMvc.perform(get("/clientes/v1/buscar-por-nome")
                        .param("nome", nome))
                .andExpect(status().isOk());

        verify(clienteUseCase, times(1))
                .buscarClienteNomeUseCase(nome);
    }

    // -------------------------------------------------------------------------
    // GET /buscar-por-email
    // -------------------------------------------------------------------------
    @Test
    void buscarPorEmail_deveRetornarCliente() throws Exception {
        String email = "teste@email.com";
        Cliente cliente = mock(Cliente.class);

        when(clienteUseCase.buscarClienteEmailUseCase(email))
                .thenReturn(cliente);

        mockMvc.perform(get("/clientes/v1/buscar-por-email")
                        .param("email", email))
                .andExpect(status().isOk());

        verify(clienteUseCase, times(1))
                .buscarClienteEmailUseCase(email);
    }

    // -------------------------------------------------------------------------
    // GET /buscar-por-id
    // -------------------------------------------------------------------------
    @Test
    void buscarPorId_deveRetornarCliente() throws Exception {
        UUID id = UUID.randomUUID();
        Cliente cliente = mock(Cliente.class);

        when(clienteUseCase.buscarClienteIdUseCase(id))
                .thenReturn(cliente);

        mockMvc.perform(get("/clientes/v1/buscar-por-id")
                        .param("Idcliente", id.toString()))
                .andExpect(status().isOk());

        verify(clienteUseCase, times(1))
                .buscarClienteIdUseCase(id);
    }

    // -------------------------------------------------------------------------
    // GET /lista
    // -------------------------------------------------------------------------
    @Test
    void lista_deveRetornarListaDeClientes() throws Exception {
        Iterable<Cliente> clientes = List.of(mock(Cliente.class));

        when(clienteUseCase.listarTodosClientesUseCase())
                .thenReturn(clientes);

        mockMvc.perform(get("/clientes/v1/lista"))
                .andExpect(status().isOk());

        verify(clienteUseCase, times(1))
                .listarTodosClientesUseCase();
    }

    // -------------------------------------------------------------------------
    // GET /validar-acesso
    // -------------------------------------------------------------------------
    @Test
    void validarAcesso_deveRetornarAcessoValido() throws Exception {
        ValidaUsuarioDTO dto = new ValidaUsuarioDTO("login", "senha","email");

        when(clienteUseCase.validaAcesso(any()))
                .thenReturn("OK");

        mockMvc.perform(get("/clientes/v1/validar-acesso")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(content().string("Acesso válido"));

        verify(clienteUseCase, times(1))
                .validaAcesso(any());
    }

    // -------------------------------------------------------------------------
    // PUT /atualizar-login-senha
    // -------------------------------------------------------------------------
    @Test
    void atualizarLoginSenha_deveAtualizarComSucesso() throws Exception {
        AtualizaLoginSenhaDTO dto =
                new AtualizaLoginSenhaDTO("email@email.com", "login", "senha");

        doNothing().when(clienteUseCase)
                .atualizaLogin(any());

        mockMvc.perform(put("/clientes/v1/atualizar-login-senha")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(content()
                        .string("Login e senha atualizados com sucesso"));

        verify(clienteUseCase, times(1))
                .atualizaLogin(any());
    }

    // -------------------------------------------------------------------------
    // PUT /atualizar
    // -------------------------------------------------------------------------
    @Test
    void atualizar_deveAtualizarCliente() throws Exception {
        AtualizaClienteDTO dto = mock(AtualizaClienteDTO.class);

        when(clienteUseCase.atualizarClienteUserCase(any()))
                .thenReturn(mock(Cliente.class));

        mockMvc.perform(put("/clientes/v1/atualizar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(content()
                        .string("Dados de cliente atualizados com sucesso"));

        verify(clienteUseCase, times(1))
                .atualizarClienteUserCase(any());
    }

    // -------------------------------------------------------------------------
    // POST /incluir
    // -------------------------------------------------------------------------
    @Test
    void incluir_deveIncluirCliente() throws Exception {
        IncluiClienteDTO dto = mock(IncluiClienteDTO.class);

        doNothing().when(clienteUseCase)
                .incluirClienteUseCase(any());

        mockMvc.perform(post("/clientes/v1/incluir")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(content()
                        .string("Cliente incluído com sucesso"));

        verify(clienteUseCase, times(1))
                .incluirClienteUseCase(any());
    }

    // -------------------------------------------------------------------------
    // DELETE /excluir
    // -------------------------------------------------------------------------
    @Test
    void excluir_deveExcluirCliente() throws Exception {
        String email = "email@email.com";

        doNothing().when(clienteUseCase)
                .deletarClientePorEmailUseCase(email);

        mockMvc.perform(delete("/clientes/v1/excluir")
                        .param("email", email))
                .andExpect(status().isOk())
                .andExpect(content()
                        .string("Cliente incluído com sucesso"));

        verify(clienteUseCase, times(1))
                .deletarClientePorEmailUseCase(email);
    }
}