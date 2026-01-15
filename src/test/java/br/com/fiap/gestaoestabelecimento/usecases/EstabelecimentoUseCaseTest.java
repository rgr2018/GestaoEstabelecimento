package br.com.fiap.gestaoestabelecimento.usecases;
import br.com.fiap.gestaoestabelecimento.adapters.gateways.EstabelecimentoGatewayImp;
import br.com.fiap.gestaoestabelecimento.application.EstabelecimentoUseCase;
import br.com.fiap.gestaoestabelecimento.domain.Estabelecimento;
import br.com.fiap.gestaoestabelecimento.dtos.AtualizaEstabelecimentoDTO;
import br.com.fiap.gestaoestabelecimento.dtos.IncluiEstabelecimentoDTO;
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
class EstabelecimentoUseCaseTest {

    @Mock
    private EstabelecimentoGatewayImp estabelecimentoGatewayImp;

    @InjectMocks
    private EstabelecimentoUseCase estabelecimentoUseCase;

    // -------------------------------------------------------------------------
    // incluirEstabelecimentoUseCase
    // -------------------------------------------------------------------------
    @Test
    void incluirEstabelecimentoUseCase_deveSalvarERetornarEstabelecimento() {
        IncluiEstabelecimentoDTO dto = mock(IncluiEstabelecimentoDTO.class);
        Estabelecimento estabelecimento = mock(Estabelecimento.class);

        when(estabelecimentoGatewayImp.saveEstabelecimento(dto))
                .thenReturn(estabelecimento);

        Estabelecimento resultado =
                estabelecimentoUseCase.incluirEstabelecimentoUseCase(dto);

        assertNotNull(resultado);
        assertEquals(estabelecimento, resultado);
        verify(estabelecimentoGatewayImp, times(1))
                .saveEstabelecimento(dto);
    }

    // -------------------------------------------------------------------------
    // updateEstabelecimentoUseCase
    // -------------------------------------------------------------------------
    @Test
    void updateEstabelecimentoUseCase_deveAtualizarEstabelecimento() {
        AtualizaEstabelecimentoDTO dto = mock(AtualizaEstabelecimentoDTO.class);

        doNothing().when(estabelecimentoGatewayImp)
                .updateEstabelecimento(dto);

        assertDoesNotThrow(() ->
                estabelecimentoUseCase.updateEstabelecimentoUseCase(dto)
        );

        verify(estabelecimentoGatewayImp, times(1))
                .updateEstabelecimento(dto);
    }

    // -------------------------------------------------------------------------
    // buscarEstabelecimentoIdUseCase
    // -------------------------------------------------------------------------
    @Test
    void buscarEstabelecimentoIdUseCase_deveBuscarPorId() {
        UUID id = UUID.randomUUID();
        Estabelecimento estabelecimento = mock(Estabelecimento.class);

        when(estabelecimentoGatewayImp.getEstabelecimento(id))
                .thenReturn(estabelecimento);

        Estabelecimento resultado =
                estabelecimentoUseCase.buscarEstabelecimentoIdUseCase(id);

        assertNotNull(resultado);
        assertEquals(estabelecimento, resultado);
        verify(estabelecimentoGatewayImp, times(1))
                .getEstabelecimento(id);
    }

    // -------------------------------------------------------------------------
    // buscarEstabelecimentoNomeUseCase
    // -------------------------------------------------------------------------
    @Test
    void buscarEstabelecimentoNomeUseCase_deveBuscarPorNome() {
        String nome = "Restaurante XPTO";
        List<Estabelecimento> lista =
                List.of(mock(Estabelecimento.class));

        when(estabelecimentoGatewayImp.buscaPorNome(nome))
                .thenReturn(lista);

        List<Estabelecimento> resultado =
                estabelecimentoUseCase.buscarEstabelecimentoNomeUseCase(nome);

        assertNotNull(resultado);
        assertEquals(lista, resultado);
        verify(estabelecimentoGatewayImp, times(1))
                .buscaPorNome(nome);
    }

    // -------------------------------------------------------------------------
    // deleteEstabelecimentoUseCase
    // -------------------------------------------------------------------------
    @Test
    void deleteEstabelecimentoUseCase_deveExcluirEstabelecimento() {
        UUID id = UUID.randomUUID();

        doNothing().when(estabelecimentoGatewayImp)
                .deleteEstabelecimento(id);

        assertDoesNotThrow(() ->
                estabelecimentoUseCase.deleteEstabelecimentoUseCase(id)
        );

        verify(estabelecimentoGatewayImp, times(1))
                .deleteEstabelecimento(id);
    }

    // -------------------------------------------------------------------------
    // listarEstabelecimentoUseCase
    // -------------------------------------------------------------------------
    @Test
    void listarEstabelecimentoUseCase_deveListarTodos() {
        List<Estabelecimento> lista =
                List.of(
                        mock(Estabelecimento.class),
                        mock(Estabelecimento.class)
                );

        when(estabelecimentoGatewayImp.getAllEstabelecimento())
                .thenReturn(lista);

        Iterable<Estabelecimento> resultado =
                estabelecimentoUseCase.listarEstabelecimentoUseCase();

        assertNotNull(resultado);
        assertEquals(lista, resultado);
        verify(estabelecimentoGatewayImp, times(1))
                .getAllEstabelecimento();
    }
}