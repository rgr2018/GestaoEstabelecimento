package br.com.fiap.gestaoestabelecimento.gateways;
import br.com.fiap.gestaoestabelecimento.adapters.gateways.EstabelecimentoGatewayImp;
import br.com.fiap.gestaoestabelecimento.domain.Estabelecimento;
import br.com.fiap.gestaoestabelecimento.infrastructure.entities.EnderecoEntity;
import br.com.fiap.gestaoestabelecimento.infrastructure.entities.EstabelecimentoEntity;
import br.com.fiap.gestaoestabelecimento.dtos.*;
import br.com.fiap.gestaoestabelecimento.exceptions.BusinessException;
import br.com.fiap.gestaoestabelecimento.infrastructure.repositories.EnderecoRepository;
import br.com.fiap.gestaoestabelecimento.infrastructure.repositories.EstabelecimentoRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static org.mockito.Mockito.*;
import br.com.fiap.gestaoestabelecimento.dtos.AtualizaEstabelecimentoDTO;
import br.com.fiap.gestaoestabelecimento.dtos.IncluiEstabelecimentoDTO;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.*;

class EstabelecimentoGatewayImpTest {

    @Mock
    private EstabelecimentoRepository estabelecimentoRepository;
    @Mock
    private EnderecoRepository enderecoRepository;

    @InjectMocks
    private EstabelecimentoGatewayImp gateway;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveEstabelecimento_deveSalvarComSucesso() {
        IncluiEstabelecimentoDTO dto = mock(IncluiEstabelecimentoDTO.class);
        IncluiEnderecoDTO enderecoDTO = mock(IncluiEnderecoDTO.class);

        when(dto.endereco()).thenReturn(enderecoDTO);

        Estabelecimento resultado = gateway.saveEstabelecimento(dto);

        assertNotNull(resultado);
        verify(enderecoRepository).save(any());
        verify(estabelecimentoRepository).save(any());
    }

    @Test
    void updateEstabelecimento_deveAtualizarComSucesso() {
        AtualizaEstabelecimentoDTO dto = mock(AtualizaEstabelecimentoDTO.class);
        AtualizaEnderecoDTO enderecoDTO = mock(AtualizaEnderecoDTO.class);
        UUID id = UUID.randomUUID();

        when(dto.idEstabelecimento()).thenReturn(id);
        when(dto.endereco()).thenReturn(enderecoDTO);
        when(enderecoDTO.idEndereco()).thenReturn(UUID.randomUUID());

        when(estabelecimentoRepository.findById(id))
                .thenReturn(Optional.of(new EstabelecimentoEntity()));
        when(enderecoRepository.findById(any()))
                .thenReturn(Optional.of(new EnderecoEntity()));

        assertDoesNotThrow(() -> gateway.updateEstabelecimento(dto));
    }

    @Test
    void updateEstabelecimento_deveLancarExcecao_quandoEstabelecimentoNaoEncontrado() {
        AtualizaEstabelecimentoDTO dto = mock(AtualizaEstabelecimentoDTO.class);
        when(dto.idEstabelecimento()).thenReturn(UUID.randomUUID());

        when(estabelecimentoRepository.findById(any()))
                .thenReturn(Optional.empty());

        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> gateway.updateEstabelecimento(dto)
        );

        assertEquals("Estabelecimento não cadastrado", exception.getMessage());
    }

    @Test
    void updateEstabelecimento_deveLancarExcecao_quandoEnderecoNaoEncontrado() {
        AtualizaEstabelecimentoDTO dto = mock(AtualizaEstabelecimentoDTO.class);
        AtualizaEnderecoDTO enderecoDTO = mock(AtualizaEnderecoDTO.class);

        UUID idEstabelecimento = UUID.randomUUID();
        UUID idEndereco = UUID.randomUUID();

        when(dto.idEstabelecimento()).thenReturn(idEstabelecimento);
        when(dto.endereco()).thenReturn(enderecoDTO);
        when(enderecoDTO.idEndereco()).thenReturn(idEndereco);

        when(estabelecimentoRepository.findById(idEstabelecimento))
                .thenReturn(Optional.of(new EstabelecimentoEntity()));

        when(enderecoRepository.findById(idEndereco))
                .thenReturn(Optional.empty());

        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> gateway.updateEstabelecimento(dto)
        );

        assertEquals("Endereço não encontrado", exception.getMessage());
    }
    @Test
    void getEstabelecimento_deveLancarExcecao_quandoIdNulo() {
        assertThrows(BusinessException.class,
                () -> gateway.getEstabelecimento(null));
    }

    @Test
    void buscaPorNome_deveRetornarLista() {
        when(estabelecimentoRepository.buscaPorNome("Teste"))
                .thenReturn(List.of(new EstabelecimentoEntity()));

        List<Estabelecimento> lista = gateway.buscaPorNome("Teste");

        assertFalse(lista.isEmpty());
    }

    @Test
    void deleteEstabelecimento_deveExcluirComSucesso() {
        UUID id = UUID.randomUUID();
        EstabelecimentoEntity entity = new EstabelecimentoEntity();

        when(estabelecimentoRepository.findById(id))
                .thenReturn(Optional.of(entity));

        assertDoesNotThrow(() -> gateway.deleteEstabelecimento(id));

        verify(estabelecimentoRepository).delete(entity);
    }

    @Test
    void deleteEstabelecimento_deveLancarExcecao_quandoNaoEncontrado() {
        UUID id = UUID.randomUUID();

        when(estabelecimentoRepository.findById(id))
                .thenReturn(Optional.empty());

        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> gateway.deleteEstabelecimento(id)
        );

        assertEquals("Estabelecimento não cadastrado", exception.getMessage());
    }

    @Test
    void getEstabelecimento_deveLancarExcecao_quandoNaoEncontrado() {
        UUID id = UUID.randomUUID();

        when(estabelecimentoRepository.buscaPorId(id))
                .thenReturn(null);

        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> gateway.getEstabelecimento(id)
        );

        assertEquals(
                "Nenhum cliente encontrado com nome " + id,
                exception.getMessage()
        );
    }

    @Test
    void getAllEstabelecimento_deveRetornarLista() {
        when(estabelecimentoRepository.listaEstabelecimentos())
                .thenReturn(List.of(new EstabelecimentoEntity()));

        List<Estabelecimento> resultado = gateway.getAllEstabelecimento();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
    }

    @Test
    void buscaPorNome_deveLancarExcecao_quandoNomeNulo() {
        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> gateway.buscaPorNome(null)
        );

        assertEquals("O campo nome está nulo", exception.getMessage());
    }

}