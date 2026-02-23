package br.com.fiap.gestaoestabelecimento.gateways;

import br.com.fiap.gestaoestabelecimento.adapters.gateways.CardapioGatewayImp;
import br.com.fiap.gestaoestabelecimento.domain.Cardapio;
import br.com.fiap.gestaoestabelecimento.dtos.AtualizaCardapioDTO;
import br.com.fiap.gestaoestabelecimento.dtos.IncluiCardapioDTO;
import br.com.fiap.gestaoestabelecimento.exceptions.BusinessException;
import br.com.fiap.gestaoestabelecimento.infrastructure.entities.CardapioEntity;
import br.com.fiap.gestaoestabelecimento.infrastructure.entities.EstabelecimentoEntity;
import br.com.fiap.gestaoestabelecimento.infrastructure.repositories.CardapioRepository;
import br.com.fiap.gestaoestabelecimento.infrastructure.repositories.EstabelecimentoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CardapioGatewayImpTest {

    @Mock
    private CardapioRepository cardapioRepository;

    @Mock
    private EstabelecimentoRepository estabelecimentoRepository;

    @InjectMocks
    private CardapioGatewayImp gateway;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveCardapio_deveSalvarComSucesso() {
        IncluiCardapioDTO dto = mock(IncluiCardapioDTO.class);
        UUID idEstabelecimento = UUID.randomUUID();
        EstabelecimentoEntity estabelecimento = new EstabelecimentoEntity();
        CardapioEntity salvo = new CardapioEntity();

        when(dto.idEstabelecimento()).thenReturn(idEstabelecimento);
        when(estabelecimentoRepository.findById(idEstabelecimento))
                .thenReturn(Optional.of(estabelecimento));
        when(cardapioRepository.save(any())).thenReturn(salvo);

        Cardapio result = gateway.saveCardapio(dto);

        assertNotNull(result);
        verify(cardapioRepository).save(any(CardapioEntity.class));
    }

    @Test
    void saveCardapio_deveLancarExcecaoQuandoEstabelecimentoNaoExiste() {
        IncluiCardapioDTO dto = mock(IncluiCardapioDTO.class);
        UUID id = UUID.randomUUID();

        when(dto.idEstabelecimento()).thenReturn(id);
        when(estabelecimentoRepository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(BusinessException.class,
                () -> gateway.saveCardapio(dto));
    }

    @Test
    void updateCardapio_deveAtualizarComSucesso() {
        AtualizaCardapioDTO dto = mock(AtualizaCardapioDTO.class);
        UUID idCardapio = UUID.randomUUID();
        UUID idEstabelecimento = UUID.randomUUID();

        CardapioEntity cardapio = new CardapioEntity();
        EstabelecimentoEntity estabelecimento = new EstabelecimentoEntity();

        when(dto.idCardapio()).thenReturn(idCardapio);
        when(dto.idEstabelecimento()).thenReturn(idEstabelecimento);
        when(cardapioRepository.findById(idCardapio))
                .thenReturn(Optional.of(cardapio));
        when(estabelecimentoRepository.findById(idEstabelecimento))
                .thenReturn(Optional.of(estabelecimento));

        assertDoesNotThrow(() -> gateway.updateCardapio(dto));
    }

    @Test
    void updateCardapio_deveLancarExcecaoQuandoCardapioNaoExiste() {
        AtualizaCardapioDTO dto = mock(AtualizaCardapioDTO.class);
        UUID idCardapio = UUID.randomUUID();

        when(dto.idCardapio()).thenReturn(idCardapio);
        when(cardapioRepository.findById(idCardapio))
                .thenReturn(Optional.empty());

        BusinessException ex = assertThrows(
                BusinessException.class,
                () -> gateway.updateCardapio(dto)
        );

        assertEquals("Cardápio não encontrado", ex.getMessage());
        verify(estabelecimentoRepository, never()).findById(any());
    }
    @Test
    void deleteCardapio_deveExcluirComSucesso() {
        UUID id = UUID.randomUUID();
        CardapioEntity entity = new CardapioEntity();

        when(cardapioRepository.findById(id))
                .thenReturn(Optional.of(entity));

        gateway.deleteCardapio(id);

        verify(cardapioRepository).delete(entity);
    }

    @Test
    void getCardapio_deveRetornarCardapio() {
        UUID id = UUID.randomUUID();
        CardapioEntity entity = new CardapioEntity();

        when(cardapioRepository.findById(id))
                .thenReturn(Optional.of(entity));

        Cardapio result = gateway.getCardapio(id);

        assertNotNull(result);
    }

    @Test
    void updateCardapio_deveLancarExcecaoQuandoEstabelecimentoNaoExiste() {
        AtualizaCardapioDTO dto = mock(AtualizaCardapioDTO.class);
        UUID idCardapio = UUID.randomUUID();
        UUID idEstabelecimento = UUID.randomUUID();

        when(dto.idCardapio()).thenReturn(idCardapio);
        when(dto.idEstabelecimento()).thenReturn(idEstabelecimento);

        when(cardapioRepository.findById(idCardapio))
                .thenReturn(Optional.of(new CardapioEntity()));

        when(estabelecimentoRepository.findById(idEstabelecimento))
                .thenReturn(Optional.empty());

        BusinessException ex = assertThrows(
                BusinessException.class,
                () -> gateway.updateCardapio(dto)
        );

        assertEquals("Estabelecimento não encontrado", ex.getMessage());
    }

    @Test
    void getAllCardapio_deveRetornarLista() {
        when(cardapioRepository.findAll())
                .thenReturn(List.of(new CardapioEntity()));

        List<Cardapio> result = gateway.getAllCardapio();

        assertEquals(1, result.size());
    }

    @Test
    void getCardapio_deveLancarExcecaoQuandoNaoEncontrado() {
        UUID id = UUID.randomUUID();

        when(cardapioRepository.findById(id))
                .thenReturn(Optional.empty());

        BusinessException ex = assertThrows(
                BusinessException.class,
                () -> gateway.getCardapio(id)
        );

        assertEquals("Cardápio não encontrado", ex.getMessage());
    }
    @Test
    void deleteCardapio_deveLancarExcecaoQuandoNaoEncontrado() {
        UUID id = UUID.randomUUID();

        when(cardapioRepository.findById(id))
                .thenReturn(Optional.empty());

        BusinessException ex = assertThrows(
                BusinessException.class,
                () -> gateway.deleteCardapio(id)
        );

        assertEquals("Cardápio não encontrado", ex.getMessage());
        verify(cardapioRepository, never()).delete(any());
    }

}