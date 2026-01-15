package br.com.fiap.gestaoestabelecimento.gateways;

import br.com.fiap.gestaoestabelecimento.adapters.gateways.DonoGatewayImp;
import br.com.fiap.gestaoestabelecimento.domain.Dono;
import br.com.fiap.gestaoestabelecimento.dtos.*;
import br.com.fiap.gestaoestabelecimento.exceptions.BusinessException;
import br.com.fiap.gestaoestabelecimento.exceptions.ValidationException;
import br.com.fiap.gestaoestabelecimento.infrastructure.entities.*;
import br.com.fiap.gestaoestabelecimento.infrastructure.repositories.*;
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

class DonoGatewayImpTest {

    @Mock
    private DonoRepository donoRepository;
    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private EnderecoRepository enderecoRepository;
    @Mock
    private EstabelecimentoRepository estabelecimentoRepository;


    @InjectMocks
    private DonoGatewayImp donoGateway;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void buscarDonoPorNome_deveRetornarLista() {
        UUID idUsuario = UUID.randomUUID();

        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setIdUsuario(idUsuario);
        usuarioEntity.setNome("Joao");

        DonoEntity donoEntity = new DonoEntity();
        donoEntity.setUsuarioEntity(usuarioEntity);

        when(donoRepository.buscarDonosPorNome(any(), any()))
                .thenReturn((List.of(donoEntity)));

        Iterable<Dono> dono = donoGateway.buscarDonoPorNome("Joao");

        assertNotNull(dono);
        List<Dono> donos = StreamSupport
                .stream(dono.spliterator(), false)
                .toList();

        assertEquals(1, donos.size());
        assertEquals("Joao", donos.get(0).getNome());

        verify(donoRepository).buscarDonosPorNome("Joao","DONO");

    }

    @Test
    void buscarDonoPorEmail_deveLancarExcecaoQuandoNaoEncontrado() {
        when(donoRepository.buscarDonoPorEmail(any(), any()))
                .thenReturn(null);

        BusinessException ex = assertThrows(
                BusinessException.class,
                () -> donoGateway.buscarDonoPorEmail("email@teste.com")
        );

        assertEquals("Usuario não existe", ex.getMessage());
    }
    @Test
    void buscarDonoPorNome_deveLancarExcecao_quandoNaoEncontrar() {
        when(donoRepository.buscarDonosPorNome(any(), any()))
                .thenReturn(List.of());

        assertThrows(BusinessException.class,
                () -> donoGateway.buscarDonoPorNome("Teste"));
    }

    @Test
    void buscarDonoPorEmail_deveRetornarDono() {

        UUID idUsuario = UUID.randomUUID();
        UsuarioEntity usuarioEntity = new UsuarioEntity();

        usuarioEntity.setIdUsuario(idUsuario);
        usuarioEntity.setEmail("teste@email.com");

        DonoEntity donoEntity = new DonoEntity();
        donoEntity.setUsuarioEntity(usuarioEntity);

        when(donoRepository.buscarDonoPorEmail(any(), any()))
                .thenReturn(donoEntity);

        Dono dono = donoGateway.buscarDonoPorEmail("teste@email.com");

        assertNotNull(dono);
        assertEquals(idUsuario, dono.getIdUsuario());

        assertNotNull(dono);
    }

    @Test
    void saveDono_deveLancarExcecaoQuandoEstabelecimentoNaoEncontrado() {
        IncluiDonoDTO dto = mock(IncluiDonoDTO.class);
        IncluiUsuarioDTO usuarioDTO = mock(IncluiUsuarioDTO.class);
        IncluiEnderecoDTO enderecoDTO = mock(IncluiEnderecoDTO.class);

        when(dto.usuarioDTO()).thenReturn(usuarioDTO);
        when(usuarioDTO.email()).thenReturn("email@teste.com");
        when(usuarioDTO.endereco()).thenReturn(enderecoDTO);
        when(dto.idEstabelecimento()).thenReturn(UUID.randomUUID());

        when(donoRepository.buscarDonoPorEmail(any(), any()))
                .thenReturn(null);
        when(estabelecimentoRepository.findById(any()))
                .thenReturn(Optional.empty());

        BusinessException ex = assertThrows(
                BusinessException.class,
                () -> donoGateway.saveDono(dto)
        );

        assertEquals("Estabelecimento não encontrado", ex.getMessage());
    }

    @Test
    void saveDono_deveLancarValidationException_quandoEmailDuplicado() {
        IncluiDonoDTO dto = mock(IncluiDonoDTO.class);
        IncluiUsuarioDTO usuarioDTO = mock(IncluiUsuarioDTO.class);

        when(dto.usuarioDTO()).thenReturn(usuarioDTO);
        when(usuarioDTO.email()).thenReturn("email@test.com");
        when(donoRepository.buscarDonoPorEmail(any(), any()))
                .thenReturn(new DonoEntity());

        assertThrows(ValidationException.class,
                () -> donoGateway.saveDono(dto));
    }
    @Test
    void updateDono_deveLancarExcecaoQuandoEnderecoNulo() {
        AtualizaDonoDTO dto = mock(AtualizaDonoDTO.class);
        AtualizaUsuarioDTO usuarioDTO = mock(AtualizaUsuarioDTO.class);

        when(dto.usuarioDTO()).thenReturn(usuarioDTO);
        when(usuarioDTO.endereco()).thenReturn(null);

        BusinessException ex = assertThrows(
                BusinessException.class,
                () -> donoGateway.updateDono(dto)
        );

        assertEquals("O campo endereco dentro de usuarioDTO não pode ser nulo", ex.getMessage());
    }
    @Test
    void updateDono_deveLancarExcecaoQuandoDonoNaoEncontrado() {
        AtualizaDonoDTO dto = mock(AtualizaDonoDTO.class);
        AtualizaUsuarioDTO usuarioDTO = mock(AtualizaUsuarioDTO.class);
        AtualizaEnderecoDTO enderecoDTO = mock(AtualizaEnderecoDTO.class);

        when(dto.usuarioDTO()).thenReturn(usuarioDTO);
        when(usuarioDTO.endereco()).thenReturn(enderecoDTO);
        when(dto.idDono()).thenReturn(UUID.randomUUID());

        when(donoRepository.findById(any()))
                .thenReturn(Optional.empty());

        BusinessException ex = assertThrows(
                BusinessException.class,
                () -> donoGateway.updateDono(dto)
        );

        assertEquals("Dono não encontrado", ex.getMessage());
    }
    @Test
    void updateDono_deveLancarExcecaoQuandoUsuarioNaoEncontrado() {
        AtualizaDonoDTO dto = mock(AtualizaDonoDTO.class);
        AtualizaUsuarioDTO usuarioDTO = mock(AtualizaUsuarioDTO.class);
        AtualizaEnderecoDTO enderecoDTO = mock(AtualizaEnderecoDTO.class);

        UUID idDono = UUID.randomUUID();
        UUID idUsuario = UUID.randomUUID();

        when(dto.idDono()).thenReturn(idDono);
        when(dto.usuarioDTO()).thenReturn(usuarioDTO);
        when(usuarioDTO.idUsuario()).thenReturn(idUsuario);
        when(usuarioDTO.endereco()).thenReturn(enderecoDTO);

        when(donoRepository.findById(idDono))
                .thenReturn(Optional.of(new DonoEntity()));
        when(usuarioRepository.findById(idUsuario))
                .thenReturn(Optional.empty());

        BusinessException ex = assertThrows(
                BusinessException.class,
                () -> donoGateway.updateDono(dto)
        );

        assertEquals("Usuário não encontrado", ex.getMessage());
    }
    @Test
    void updateDono_deveLancarExcecaoQuandoEnderecoNaoEncontrado() {
        AtualizaDonoDTO dto = mock(AtualizaDonoDTO.class);
        AtualizaUsuarioDTO usuarioDTO = mock(AtualizaUsuarioDTO.class);
        AtualizaEnderecoDTO enderecoDTO = mock(AtualizaEnderecoDTO.class);

        UUID idDono = UUID.randomUUID();
        UUID idUsuario = UUID.randomUUID();
        UUID idEndereco = UUID.randomUUID();

        when(dto.idDono()).thenReturn(idDono);
        when(dto.usuarioDTO()).thenReturn(usuarioDTO);
        when(usuarioDTO.idUsuario()).thenReturn(idUsuario);
        when(usuarioDTO.endereco()).thenReturn(enderecoDTO);
        when(enderecoDTO.idEndereco()).thenReturn(idEndereco);

        when(donoRepository.findById(idDono))
                .thenReturn(Optional.of(new DonoEntity()));
        when(usuarioRepository.findById(idUsuario))
                .thenReturn(Optional.of(new UsuarioEntity()));
        when(enderecoRepository.findById(idEndereco))
                .thenReturn(Optional.empty());

        BusinessException ex = assertThrows(
                BusinessException.class,
                () -> donoGateway.updateDono(dto)
        );

        assertEquals("Endereço não encontrado", ex.getMessage());
    }
    @Test
    void updateDono_deveLancarExcecaoQuandoEstabelecimentoNaoEncontrado() {
        AtualizaDonoDTO dto = mock(AtualizaDonoDTO.class);
        AtualizaUsuarioDTO usuarioDTO = mock(AtualizaUsuarioDTO.class);
        AtualizaEnderecoDTO enderecoDTO = mock(AtualizaEnderecoDTO.class);

        UUID idDono = UUID.randomUUID();
        UUID idUsuario = UUID.randomUUID();
        UUID idEndereco = UUID.randomUUID();
        UUID idEstabelecimento = UUID.randomUUID();

        when(dto.idDono()).thenReturn(idDono);
        when(dto.idEstabelecimento()).thenReturn(idEstabelecimento);
        when(dto.usuarioDTO()).thenReturn(usuarioDTO);
        when(usuarioDTO.idUsuario()).thenReturn(idUsuario);
        when(usuarioDTO.endereco()).thenReturn(enderecoDTO);
        when(enderecoDTO.idEndereco()).thenReturn(idEndereco);

        when(donoRepository.findById(idDono))
                .thenReturn(Optional.of(new DonoEntity()));
        when(usuarioRepository.findById(idUsuario))
                .thenReturn(Optional.of(new UsuarioEntity()));
        when(enderecoRepository.findById(idEndereco))
                .thenReturn(Optional.of(new EnderecoEntity()));
        when(estabelecimentoRepository.findById(idEstabelecimento))
                .thenReturn(Optional.empty());

        BusinessException ex = assertThrows(
                BusinessException.class,
                () -> donoGateway.updateDono(dto)
        );

        assertEquals("Estabelecimento não encontrado", ex.getMessage());
    }

    @Test
    void updateDono_deveLancarExcecaoQuandoUsuarioDTONulo() {
        AtualizaDonoDTO dto = mock(AtualizaDonoDTO.class);

        when(dto.usuarioDTO()).thenReturn(null);

        BusinessException ex = assertThrows(
                BusinessException.class,
                () -> donoGateway.updateDono(dto)
        );

        assertEquals("O campo usuarioDTO não pode ser nulo", ex.getMessage());
    }

    @Test
    void updateDono_deveAtualizarComSucesso() {
        AtualizaDonoDTO dto = mock(AtualizaDonoDTO.class);
        AtualizaUsuarioDTO usuarioDTO = mock(AtualizaUsuarioDTO.class);
        AtualizaEnderecoDTO enderecoDTO = mock(AtualizaEnderecoDTO.class);

        UUID idDono = UUID.randomUUID();
        UUID idUsuario = UUID.randomUUID();
        UUID idEndereco = UUID.randomUUID();
        UUID idEstabelecimento = UUID.randomUUID();

        DonoEntity donoEntity = new DonoEntity();
        donoEntity.setIdDono(idDono);

        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setIdUsuario(idUsuario);

        EnderecoEntity enderecoEntity = new EnderecoEntity();
        enderecoEntity.setIdEndereco(idEndereco);

        EstabelecimentoEntity estabelecimentoEntity = new EstabelecimentoEntity();
        estabelecimentoEntity.setIdEstabelecimento(idEstabelecimento);

        when(dto.idDono()).thenReturn(idDono);

        when(dto.usuarioDTO()).thenReturn(usuarioDTO);
        when(usuarioDTO.idUsuario()).thenReturn(idUsuario);

        when(usuarioDTO.endereco()).thenReturn(enderecoDTO);
        when(enderecoDTO.idEndereco()).thenReturn(idEndereco);

        when(dto.idEstabelecimento()).thenReturn(idEstabelecimento);


        when(donoRepository.findById(idDono))
                .thenReturn(Optional.of(donoEntity));

        when(usuarioRepository.findById(idUsuario))
                .thenReturn(Optional.of(usuarioEntity));

        when(enderecoRepository.findById(idEndereco))
                .thenReturn(Optional.of(enderecoEntity));

        when(estabelecimentoRepository.findById(idEstabelecimento))
                .thenReturn(Optional.of(estabelecimentoEntity));

        Dono resultado = donoGateway.updateDono(dto);

        assertNotNull(resultado);
        assertEquals(idDono, resultado.getIdDono());

        verify(donoRepository).findById(idDono);
        verify(usuarioRepository).findById(idUsuario);
        verify(enderecoRepository).findById(idEndereco);
        verify(estabelecimentoRepository).findById(idEstabelecimento);
    }

    @Test
    void getDono_deveLancarExcecao_quandoIdNulo() {
        assertThrows(BusinessException.class,
                () -> donoGateway.getDono(null));
    }

    @Test
    void getDono_deveLancarExcecaoQuandoNaoEncontrado() {
        UUID id = UUID.randomUUID();

        when(donoRepository.buscaDonoPorId(id))
                .thenReturn(null);

        BusinessException ex = assertThrows(
                BusinessException.class,
                () -> donoGateway.getDono(id)
        );

        assertEquals("Usuario não existe", ex.getMessage());
    }

    @Test
    void listaTodosDonos_deveRetornarLista() {

        UsuarioEntity usuario = mock(UsuarioEntity.class);

        DonoEntity donoEntity = new DonoEntity();
        donoEntity.setIdDono(UUID.randomUUID());
        donoEntity.setUsuarioEntity(usuario);

        when(donoRepository.listaTodosDonos())
                .thenReturn(List.of(donoEntity));

        Iterable<Dono> resultado = donoGateway.listaTodosDonos();

        List<Dono> donos = StreamSupport
                .stream(resultado.spliterator(), false)
                .toList();

        assertEquals(1, donos.size());
    }

    @Test
    void listaTodosDonos_deveLancarExcecaoQuandoListaVazia() {
        when(donoRepository.listaTodosDonos())
                .thenReturn(List.of());

        BusinessException ex = assertThrows(
                BusinessException.class,
                () -> donoGateway.listaTodosDonos()
        );

        assertEquals("Nenhum dono encontrado", ex.getMessage());
    }

    @Test
    void deleteDono_deveLancarExcecaoQuandoEmailNulo() {
        BusinessException ex = assertThrows(
                BusinessException.class,
                () -> donoGateway.deleteDono(null)
        );

        assertEquals("O campo usuarioDTO não pode ser nulo", ex.getMessage());
    }

    @Test
    void deleteDono_deveLancarValidationExceptionQuandoEmailNaoExiste() {
        when(donoRepository.buscarDonoPorEmail(any(), any()))
                .thenReturn(null);

        ValidationException ex = assertThrows(
                ValidationException.class,
                () -> donoGateway.deleteDono("email@teste.com")
        );

        assertEquals("Email não encontrado", ex.getMessage());
    }
    @Test
    void deleteDono_deveExcluirComSucesso() {
        when(donoRepository.buscarDonoPorEmail("teste@email.com", "Dono"))
                .thenReturn(new DonoEntity());

        UUID idUsuario = UUID.randomUUID();
        UUID idEndereco = UUID.randomUUID();
        UUID idDono = UUID.randomUUID();

        when(usuarioRepository.buscaIdPorEmail(any(), any()))
                .thenReturn(idUsuario);
        when(usuarioRepository.buscaIdEnderecoPorEmail(any()))
                .thenReturn(idEndereco);
        when(usuarioRepository.buscaIddonoPoridUsuario(any()))
                .thenReturn(idDono);

        assertDoesNotThrow(() ->
                donoGateway.deleteDono("teste@email.com"));

        verify(enderecoRepository).deleteById(idEndereco);
        verify(donoRepository).deleteById(idDono);
        verify(usuarioRepository).deleteById(idUsuario);
    }
}