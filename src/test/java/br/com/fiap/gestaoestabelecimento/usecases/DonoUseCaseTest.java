package br.com.fiap.gestaoestabelecimento.usecases;

import br.com.fiap.gestaoestabelecimento.domain.DonoUseCase;
import br.com.fiap.gestaoestabelecimento.domain.*;
import br.com.fiap.gestaoestabelecimento.dtos.*;
import br.com.fiap.gestaoestabelecimento.interfaces.DonoGateway;
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
class DonoUseCaseTest {

    @Mock
    private DonoGateway donoGateway;

    @Mock
    private UsuarioGateway usuarioGateway;

    @InjectMocks
    private DonoUseCase donoUseCase;

    // -------------------------------------------------------------------------
    // incluirDonoUseCase
    // -------------------------------------------------------------------------
    @Test
    void incluirDonoUseCase_deveSalvarDono() {
        IncluiDonoDTO dto = mock(IncluiDonoDTO.class);

        Dono dono = mock(Dono.class);
        when(donoGateway.saveDono(dto)).thenReturn(dono);;

        assertDoesNotThrow(() -> donoUseCase.incluirDonoUseCase(dto));

        verify(donoGateway, times(1)).saveDono(dto);
    }

    // -------------------------------------------------------------------------
    // atualizarDonoUserCase
    // -------------------------------------------------------------------------
    @Test
    void atualizarDonoUserCase_deveAtualizarERetornarDono() {
        AtualizaDonoDTO dto = mock(AtualizaDonoDTO.class);
        Dono dono = mock(Dono.class);

        when(donoGateway.updateDono(dto)).thenReturn(dono);

        Dono resultado = donoUseCase.atualizarDonoUserCase(dto);

        assertNotNull(resultado);
        assertEquals(dono, resultado);
        verify(donoGateway, times(1)).updateDono(dto);
    }

    // -------------------------------------------------------------------------
    // buscardonoIdUseCase
    // -------------------------------------------------------------------------
    @Test
    void buscardonoIdUseCase_deveBuscarPorId() {
        UUID id = UUID.randomUUID();
        Dono dono = mock(Dono.class);

        when(donoGateway.getDono(id)).thenReturn(dono);

        Dono resultado = donoUseCase.buscardonoIdUseCase(id);

        assertNotNull(resultado);
        assertEquals(dono, resultado);
        verify(donoGateway, times(1)).getDono(id);
    }

    // -------------------------------------------------------------------------
    // buscarDonoNomeUseCase
    // -------------------------------------------------------------------------
    @Test
    void buscarDonoNomeUseCase_deveBuscarPorNome() {
        String nome = "Carlos";
        List<Dono> donos = List.of(mock(Dono.class));

        when(donoGateway.buscarDonoPorNome(nome)).thenReturn(donos);

        Iterable<Dono> resultado = donoUseCase.buscarDonoNomeUseCase(nome);

        assertNotNull(resultado);
        assertEquals(donos, resultado);
        verify(donoGateway, times(1)).buscarDonoPorNome(nome);
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
                .thenReturn("DONO");

        String resultado = donoUseCase.validaAcesso(dto);

        assertEquals("DONO", resultado);
        verify(usuarioGateway, times(1))
                .validaAcessoUsuario("login", "senha");
    }

    // -------------------------------------------------------------------------
    // buscardonoEmailUseCase
    // -------------------------------------------------------------------------
    @Test
    void buscardonoEmailUseCase_deveBuscarPorEmail() {
        String email = "dono@email.com";
        Dono dono = mock(Dono.class);

        when(donoGateway.buscarDonoPorEmail(email)).thenReturn(dono);

        Dono resultado = donoUseCase.buscardonoEmailUseCase(email);

        assertNotNull(resultado);
        assertEquals(dono, resultado);
        verify(donoGateway, times(1)).buscarDonoPorEmail(email);
    }

    // -------------------------------------------------------------------------
    // atualizaLogin
    // -------------------------------------------------------------------------
    @Test
    void atualizaLogin_deveCriarDTOComTipoDONO() {
        AtualizaLoginSenhaDTO input = mock(AtualizaLoginSenhaDTO.class);

        when(input.email()).thenReturn("dono@email.com");
        when(input.login()).thenReturn("loginNovo");
        when(input.senha()).thenReturn("senhaNova");

        ArgumentCaptor<AtualizaLoginSenhaUsuarioDTO> captor =
                ArgumentCaptor.forClass(AtualizaLoginSenhaUsuarioDTO.class);

        doNothing().when(usuarioGateway).atualizarLoginSenha(any());

        assertDoesNotThrow(() -> donoUseCase.atualizaLogin(input));

        verify(usuarioGateway, times(1)).atualizarLoginSenha(captor.capture());

        AtualizaLoginSenhaUsuarioDTO dtoGerado = captor.getValue();

        assertEquals("dono@email.com", dtoGerado.email());
        assertEquals("loginNovo", dtoGerado.login());
        assertEquals("senhaNova", dtoGerado.senha());
        assertEquals("DONO", dtoGerado.tipoUsuario());
    }

    // -------------------------------------------------------------------------
    // listaTodosDonosUseCase
    // -------------------------------------------------------------------------
    @Test
    void listaTodosDonosUseCase_deveRetornarLista() {
        List<Dono> donos = List.of(
                mock(Dono.class),
                mock(Dono.class)
        );

        when(donoGateway.listaTodosDonos()).thenReturn(donos);

        Iterable<Dono> resultado = donoUseCase.listaTodosDonosUseCase();

        assertNotNull(resultado);
        assertEquals(donos, resultado);
        verify(donoGateway, times(1)).listaTodosDonos();
    }

    // -------------------------------------------------------------------------
    // deletarDonoPorEmailUseCase
    // -------------------------------------------------------------------------
    @Test
    void deletarDonoPorEmailUseCase_deveExcluirDono() {
        String email = "dono@email.com";

        doNothing().when(donoGateway).deleteDono(email);

        assertDoesNotThrow(() -> donoUseCase.deletarDonoPorEmailUseCase(email));

        verify(donoGateway, times(1)).deleteDono(email);
    }
}