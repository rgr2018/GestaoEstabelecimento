package br.com.fiap.gestaoestabelecimento.controlles;
import br.com.fiap.gestaoestabelecimento.adapters.controllers.EstabelecimentoController;
import br.com.fiap.gestaoestabelecimento.domain.EstabelecimentoUseCase;
import br.com.fiap.gestaoestabelecimento.domain.Estabelecimento;
import br.com.fiap.gestaoestabelecimento.dtos.AtualizaEstabelecimentoDTO;
import br.com.fiap.gestaoestabelecimento.dtos.IncluiEstabelecimentoDTO;
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

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class EstabelecimentoControllerTest {

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private EstabelecimentoUseCase estabelecimentoUseCase;

    @InjectMocks
    private EstabelecimentoController estabelecimentoController;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(estabelecimentoController)
                .build();
    }

    // -------------------------------------------------------------------------
    // GET /buscar-por-id
    // -------------------------------------------------------------------------
    @Test
    void buscarPorId_deveRetornarEstabelecimento() throws Exception {
        UUID id = UUID.randomUUID();
        Estabelecimento estabelecimento = mock(Estabelecimento.class);

        when(estabelecimentoUseCase.buscarEstabelecimentoIdUseCase(id))
                .thenReturn(estabelecimento);

        mockMvc.perform(get("/estabelecimentos/v1/buscar-por-id")
                        .param("IdEstabelecimento", id.toString()))
                .andExpect(status().isOk());

        verify(estabelecimentoUseCase, times(1))
                .buscarEstabelecimentoIdUseCase(id);
    }

    // -------------------------------------------------------------------------
    // GET /buscar-por-nome
    // -------------------------------------------------------------------------
    @Test
    void buscarPorNome_deveRetornarListaDeEstabelecimentos() throws Exception {
        String nome = "Restaurante";
        List<Estabelecimento> lista = List.of(mock(Estabelecimento.class));

        when(estabelecimentoUseCase.buscarEstabelecimentoNomeUseCase(nome))
                .thenReturn(lista);

        mockMvc.perform(get("/estabelecimentos/v1/buscar-por-nome")
                        .param("nome", nome))
                .andExpect(status().isOk());

        verify(estabelecimentoUseCase, times(1))
                .buscarEstabelecimentoNomeUseCase(nome);
    }

    // -------------------------------------------------------------------------
    // GET /lista
    // -------------------------------------------------------------------------
    @Test
    void lista_deveRetornarListaDeEstabelecimentos() throws Exception {
        Iterable<Estabelecimento> estabelecimentos =
                Collections.singletonList(mock(Estabelecimento.class));

        when(estabelecimentoUseCase.listarEstabelecimentoUseCase())
                .thenReturn(estabelecimentos);

        mockMvc.perform(get("/estabelecimentos/v1/lista"))
                .andExpect(status().isOk());

        verify(estabelecimentoUseCase, times(1))
                .listarEstabelecimentoUseCase();
    }

    // -------------------------------------------------------------------------
    // PUT /atualizar
    // -------------------------------------------------------------------------
    @Test
    void atualizar_deveAtualizarEstabelecimento() throws Exception {
        AtualizaEstabelecimentoDTO dto = mock(AtualizaEstabelecimentoDTO.class);

        doNothing().when(estabelecimentoUseCase)
                .updateEstabelecimentoUseCase(any());

        mockMvc.perform(put("/estabelecimentos/v1/atualizar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(content()
                        .string("Estabelecimento atualizado com sucesso"));

        verify(estabelecimentoUseCase, times(1))
                .updateEstabelecimentoUseCase(any());
    }

    // -------------------------------------------------------------------------
    // POST /incluir
    // -------------------------------------------------------------------------
    @Test
    void incluir_deveIncluirEstabelecimento() throws Exception {
        IncluiEstabelecimentoDTO dto = mock(IncluiEstabelecimentoDTO.class);

        when(estabelecimentoUseCase.incluirEstabelecimentoUseCase(any()))
                .thenReturn(mock(Estabelecimento.class));

        mockMvc.perform(post("/estabelecimentos/v1/incluir")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(content()
                        .string("Estabelecimento incluído com sucesso"));

        verify(estabelecimentoUseCase, times(1))
                .incluirEstabelecimentoUseCase(any());
    }

    // -------------------------------------------------------------------------
    // DELETE /excluir
    // -------------------------------------------------------------------------
    @Test
    void excluir_deveExcluirEstabelecimento() throws Exception {
        UUID id = UUID.randomUUID();

        doNothing().when(estabelecimentoUseCase)
                .deleteEstabelecimentoUseCase(id);

        mockMvc.perform(delete("/estabelecimentos/v1/excluir")
                        .param("idEstabelecimento", id.toString()))
                .andExpect(status().isOk())
                .andExpect(content()
                        .string("Estabelecimento excluido com sucesso"));

        verify(estabelecimentoUseCase, times(1))
                .deleteEstabelecimentoUseCase(id);
    }
}