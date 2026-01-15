package br.com.fiap.gestaoestabelecimento.usecases;

import br.com.fiap.gestaoestabelecimento.adapters.gateways.CardapioGatewayImp;
import br.com.fiap.gestaoestabelecimento.application.CardapioUseCase;
import br.com.fiap.gestaoestabelecimento.domain.Cardapio;
import br.com.fiap.gestaoestabelecimento.dtos.AtualizaCardapioDTO;
import br.com.fiap.gestaoestabelecimento.dtos.IncluiCardapioDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CardapioUseCaseTest {

    @Mock
    private CardapioGatewayImp cardapioGatewayImp;

    @InjectMocks
    private CardapioUseCase cardapioUseCase;

    // ----------------------------------------------------------------
    // incluirCardapioUseCase
    // ----------------------------------------------------------------
    @Test
    void incluirCardapioUseCase_deveSalvarERetornarCardapio() {
        IncluiCardapioDTO dto = mock(IncluiCardapioDTO.class);
        Cardapio cardapio = mock(Cardapio.class);

        when(cardapioGatewayImp.saveCardapio(dto)).thenReturn(cardapio);

        Cardapio resultado = cardapioUseCase.incluirCardapioUseCase(dto);

        assertNotNull(resultado);
        assertEquals(cardapio, resultado);
        verify(cardapioGatewayImp, times(1)).saveCardapio(dto);
    }

    // ----------------------------------------------------------------
    // updateCardapioUseCase
    // ----------------------------------------------------------------
    @Test
    void updateCardapioUseCase_deveAtualizarCardapio() {
        AtualizaCardapioDTO dto = mock(AtualizaCardapioDTO.class);

        doNothing().when(cardapioGatewayImp).updateCardapio(dto);

        assertDoesNotThrow(() -> cardapioUseCase.updateCardapioUseCase(dto));

        verify(cardapioGatewayImp, times(1)).updateCardapio(dto);
    }

    // ----------------------------------------------------------------
    // buscarCardapioIdUseCase
    // ----------------------------------------------------------------
    @Test
    void buscarCardapioIdUseCase_deveRetornarCardapio() {
        UUID id = UUID.randomUUID();
        Cardapio cardapio = mock(Cardapio.class);

        when(cardapioGatewayImp.getCardapio(id)).thenReturn(cardapio);

        Cardapio resultado = cardapioUseCase.buscarCardapioIdUseCase(id);

        assertNotNull(resultado);
        assertEquals(cardapio, resultado);
        verify(cardapioGatewayImp, times(1)).getCardapio(id);
    }

    // ----------------------------------------------------------------
    // listarCardapioUseCase
    // ----------------------------------------------------------------
    @Test
    void listarCardapioUseCase_deveRetornarListaDeCardapios() {
        List<Cardapio> lista = List.of(
                mock(Cardapio.class),
                mock(Cardapio.class)
        );

        when(cardapioGatewayImp.getAllCardapio()).thenReturn(lista);

        Iterable<Cardapio> resultado = cardapioUseCase.listarCardapioUseCase();

        assertNotNull(resultado);
        assertEquals(lista, resultado);
        verify(cardapioGatewayImp, times(1)).getAllCardapio();
    }

    // ----------------------------------------------------------------
    // deleteCardapioUseCase
    // ----------------------------------------------------------------
    @Test
    void deleteCardapioUseCase_deveExcluirCardapio() {
        UUID id = UUID.randomUUID();

        doNothing().when(cardapioGatewayImp).deleteCardapio(id);

        assertDoesNotThrow(() -> cardapioUseCase.deleteCardapioUseCase(id));

        verify(cardapioGatewayImp, times(1)).deleteCardapio(id);
    }
}