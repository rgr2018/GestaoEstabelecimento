package br.com.fiap.gestaoestabelecimento.controlles;
import br.com.fiap.gestaoestabelecimento.adapters.controllers.DonoController;
import br.com.fiap.gestaoestabelecimento.domain.DonoUseCase;
import br.com.fiap.gestaoestabelecimento.domain.Dono;
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
class DonoControllerTest {

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private DonoUseCase donoUseCase;

    @InjectMocks
    private DonoController donoController;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(donoController)
                .build();
    }

    // -------------------------------------------------------------------------
    // GET /buscar-por-nome
    // -------------------------------------------------------------------------
    @Test
    void buscarPorNome_deveRetornarDonos() throws Exception {
        String nome = "Carlos";
        Iterable<Dono> donos = List.of(mock(Dono.class));

        when(donoUseCase.buscarDonoNomeUseCase(nome))
                .thenReturn(donos);

        mockMvc.perform(get("/donos/v1/buscar-por-nome")
                        .param("nome", nome))
                .andExpect(status().isOk());

        verify(donoUseCase, times(1))
                .buscarDonoNomeUseCase(nome);
    }

    // -------------------------------------------------------------------------
    // GET /buscar-por-email
    // -------------------------------------------------------------------------
    @Test
    void buscarPorEmail_deveRetornarDono() throws Exception {
        String email = "dono@email.com";
        Dono dono = mock(Dono.class);

        when(donoUseCase.buscardonoEmailUseCase(email))
                .thenReturn(dono);

        mockMvc.perform(get("/donos/v1/buscar-por-email")
                        .param("email", email))
                .andExpect(status().isOk());

        verify(donoUseCase, times(1))
                .buscardonoEmailUseCase(email);
    }

    // -------------------------------------------------------------------------
    // GET /buscar-por-id
    // -------------------------------------------------------------------------
    @Test
    void buscarPorId_deveRetornarDono() throws Exception {
        UUID id = UUID.randomUUID();
        Dono dono = mock(Dono.class);

        when(donoUseCase.buscardonoIdUseCase(id))
                .thenReturn(dono);

        mockMvc.perform(get("/donos/v1/buscar-por-id")
                        .param("Iddono", id.toString()))
                .andExpect(status().isOk());

        verify(donoUseCase, times(1))
                .buscardonoIdUseCase(id);
    }

    // -------------------------------------------------------------------------
    // GET /lista
    // -------------------------------------------------------------------------
    @Test
    void lista_deveRetornarListaDeDonos() throws Exception {
        Iterable<Dono> donos = List.of(mock(Dono.class));

        when(donoUseCase.listaTodosDonosUseCase())
                .thenReturn(donos);

        mockMvc.perform(get("/donos/v1/lista"))
                .andExpect(status().isOk());

        verify(donoUseCase, times(1))
                .listaTodosDonosUseCase();
    }

    // -------------------------------------------------------------------------
    // GET /validar-acesso
    // -------------------------------------------------------------------------
    @Test
    void validarAcesso_deveRetornarAcessoValido() throws Exception {
        ValidaUsuarioDTO dto = new ValidaUsuarioDTO("login", "senha", "email");

        when(donoUseCase.validaAcesso(any()))
                .thenReturn(any());

        mockMvc.perform(get("/donos/v1/validar-acesso")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(content().string("Acesso válido"));

        verify(donoUseCase, times(1))
                .validaAcesso(any());
    }

    // -------------------------------------------------------------------------
    // PUT /atualizar-login-senha
    // -------------------------------------------------------------------------
    @Test
    void atualizarLoginSenha_deveAtualizarComSucesso() throws Exception {
        AtualizaLoginSenhaDTO dto =
                new AtualizaLoginSenhaDTO("email@email.com", "login", "senha");

        doNothing().when(donoUseCase)
                .atualizaLogin(any());

        mockMvc.perform(put("/donos/v1/atualizar-login-senha")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(content()
                        .string("Login e senha atualizados com sucesso"));

        verify(donoUseCase, times(1))
                .atualizaLogin(any());
    }

    // -------------------------------------------------------------------------
    // PUT /atualizar
    // -------------------------------------------------------------------------
    @Test
    void atualizar_deveAtualizarDono() throws Exception {
        AtualizaDonoDTO dto = mock(AtualizaDonoDTO.class);
        Dono dono = mock(Dono.class);

        when(donoUseCase.atualizarDonoUserCase(any()))
                .thenReturn(dono);

        mockMvc.perform(put("/donos/v1/atualizar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(content().string("Dados de dono atualizados com sucesso"));

        verify(donoUseCase, times(1))
                .atualizarDonoUserCase(any());
    }

    // -------------------------------------------------------------------------
    // POST /incluir
    // -------------------------------------------------------------------------
    @Test
    void incluir_deveIncluirDono() throws Exception {
        IncluiDonoDTO dto = mock(IncluiDonoDTO.class);

        doNothing().when(donoUseCase)
                .incluirDonoUseCase(any());

        mockMvc.perform(post("/donos/v1/incluir")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(content()
                        .string("dono incluído com sucesso"));

        verify(donoUseCase, times(1))
                .incluirDonoUseCase(any());
    }

    // -------------------------------------------------------------------------
    // DELETE /excluir
    // -------------------------------------------------------------------------
    @Test
    void excluir_deveExcluirDono() throws Exception {
        String email = "dono@email.com";

        doNothing().when(donoUseCase)
                .deletarDonoPorEmailUseCase(email);

        mockMvc.perform(delete("/donos/v1/excluir")
                        .param("email", email))
                .andExpect(status().isOk())
                .andExpect(content()
                        .string("dono incluído com sucesso"));

        verify(donoUseCase, times(1))
                .deletarDonoPorEmailUseCase(email);
    }
}