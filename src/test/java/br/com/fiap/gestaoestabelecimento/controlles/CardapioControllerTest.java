package br.com.fiap.gestaoestabelecimento.controlles;

import br.com.fiap.gestaoestabelecimento.adapters.controllers.CardapioController;
import br.com.fiap.gestaoestabelecimento.application.CardapioUseCase;
import br.com.fiap.gestaoestabelecimento.domain.Cardapio;
import br.com.fiap.gestaoestabelecimento.dtos.AtualizaCardapioDTO;
import br.com.fiap.gestaoestabelecimento.dtos.IncluiCardapioDTO;
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
class CardapioControllerTest {

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private CardapioUseCase cardapioUseCase;

    @InjectMocks
    private CardapioController cardapioController;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(cardapioController)
                .build();
    }

    // -------------------------------------------------------------------------
    // GET /buscar-por-id
    // -------------------------------------------------------------------------
    @Test
    void buscarPorId_deveRetornarCardapio() throws Exception {
        UUID id = UUID.randomUUID();
        Cardapio cardapio = mock(Cardapio.class);

        when(cardapioUseCase.buscarCardapioIdUseCase(id))
                .thenReturn(cardapio);

        mockMvc.perform(get("/cardapios/v1/buscar-por-id")
                        .param("IdCardapio", id.toString()))
                .andExpect(status().isOk());

        verify(cardapioUseCase, times(1))
                .buscarCardapioIdUseCase(id);
    }

    // -------------------------------------------------------------------------
    // GET /lista
    // -------------------------------------------------------------------------
    @Test
    void lista_deveRetornarListaDeCardapios() throws Exception {
        List<Cardapio> lista = List.of(mock(Cardapio.class));

        when(cardapioUseCase.listarCardapioUseCase())
                .thenReturn(lista);

        mockMvc.perform(get("/cardapios/v1/lista"))
                .andExpect(status().isOk());

        verify(cardapioUseCase, times(1))
                .listarCardapioUseCase();
    }

    // -------------------------------------------------------------------------
    // PUT /atualizar
    // -------------------------------------------------------------------------
    @Test
    void atualizar_deveAtualizarCardapio() throws Exception {
        AtualizaCardapioDTO dto = mock(AtualizaCardapioDTO.class);

        doNothing().when(cardapioUseCase)
                .updateCardapioUseCase(any(AtualizaCardapioDTO.class));

        mockMvc.perform(put("/cardapios/v1/atualizar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(content()
                        .string("Item de cardapio atualizado com sucesso"));

        verify(cardapioUseCase, times(1))
                .updateCardapioUseCase(any(AtualizaCardapioDTO.class));
    }

    // -------------------------------------------------------------------------
    // POST /incluir
    // -------------------------------------------------------------------------
    @Test
    void incluir_deveIncluirCardapio() throws Exception {
        IncluiCardapioDTO dto = mock(IncluiCardapioDTO.class);
        Cardapio cardapio = mock(Cardapio.class);

        when(cardapioUseCase.incluirCardapioUseCase(any()))
                .thenReturn(cardapio);

        mockMvc.perform(post("/cardapios/v1/incluir")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(content().string("Cardapio incluído com sucesso"));

        verify(cardapioUseCase, times(1))
                .incluirCardapioUseCase(any());
    }

    // -------------------------------------------------------------------------
    // DELETE /excluir
    // -------------------------------------------------------------------------
    @Test
    void excluir_deveExcluirCardapio() throws Exception {
        UUID id = UUID.randomUUID();

        doNothing().when(cardapioUseCase)
                .deleteCardapioUseCase(id);

        mockMvc.perform(delete("/cardapios/v1/excluir")
                        .param("idCardapio", id.toString()))
                .andExpect(status().isOk())
                .andExpect(content()
                        .string("Cardapio excluido com sucesso"));

        verify(cardapioUseCase, times(1))
                .deleteCardapioUseCase(id);
    }
}