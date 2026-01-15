package br.com.fiap.gestaoestabelecimento.gateways;

import br.com.fiap.gestaoestabelecimento.adapters.gateways.UsuarioGatewayImp;
import br.com.fiap.gestaoestabelecimento.dtos.AtualizaLoginSenhaUsuarioDTO;
import br.com.fiap.gestaoestabelecimento.exceptions.BusinessException;
import br.com.fiap.gestaoestabelecimento.infrastructure.repositories.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioGatewayImpTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioGatewayImp gateway;

    // ----------------------------------------------------------------
    // emailExiste
    // ----------------------------------------------------------------
    @Test
    void emailExiste_deveRetornarTrue() {
        when(usuarioRepository.validePorEmail("teste@email.com"))
                .thenReturn(1L);

        assertTrue(gateway.emailExiste("teste@email.com"));
    }

    @Test
    void emailExiste_deveRetornarTrue_quandoMaiorQueUm() {
        when(usuarioRepository.validePorEmail("teste@email.com"))
                .thenReturn(5L);

        assertTrue(gateway.emailExiste("teste@email.com"));
    }

    @Test
    void validaAcessoUsuario_invalido_deveConterLoginNaMensagem() {
        when(usuarioRepository.validaAcessoUsuario("login", "senha"))
                .thenReturn(null);

        BusinessException ex = assertThrows(
                BusinessException.class,
                () -> gateway.validaAcessoUsuario("login", "senha")
        );

        assertEquals("login ou senha invalido: login", ex.getMessage());
        verify(usuarioRepository).validaAcessoUsuario("login", "senha");
    }
    @Test
    void emailExiste_deveRetornarFalse() {
        when(usuarioRepository.validePorEmail("teste@email.com"))
                .thenReturn(0L);

        assertFalse(gateway.emailExiste("teste@email.com"));
    }

    // ----------------------------------------------------------------
    // validaAcessoUsuario
    // ----------------------------------------------------------------
    @Test
    void validaAcessoUsuario_sucesso() {
        when(usuarioRepository.validaAcessoUsuario("login", "senha"))
                .thenReturn("CLIENTE");

        String result = gateway.validaAcessoUsuario("login", "senha");

        assertEquals("CLIENTE", result);
    }

    @Test
    void atualizarLoginSenha_deveLancarExcecao_comMensagemCorreta() {
        AtualizaLoginSenhaUsuarioDTO dto = mock(AtualizaLoginSenhaUsuarioDTO.class);

        when(usuarioRepository.atualizaLoginSenhaUsuario(
                any(), any(), any(), any()
        )).thenReturn(0);

        BusinessException ex = assertThrows(
                BusinessException.class,
                () -> gateway.atualizarLoginSenha(dto)
        );

        assertEquals(
                "Não foi possível atualizar os dados do usuário",
                ex.getMessage()
        );
    }

    @Test
    void validaAcessoUsuario_invalido_deveLancarExcecao() {
        when(usuarioRepository.validaAcessoUsuario("login", "senha"))
                .thenReturn(null);

        BusinessException ex = assertThrows(
                BusinessException.class,
                () -> gateway.validaAcessoUsuario("login", "senha")
        );

        assertTrue(ex.getMessage().contains("login ou senha invalido"));
    }

    // ----------------------------------------------------------------
    // atualizarLoginSenha
    // ----------------------------------------------------------------
    @Test
    void atualizarLoginSenha_sucesso() {
        AtualizaLoginSenhaUsuarioDTO dto = mock(AtualizaLoginSenhaUsuarioDTO.class);

        when(dto.email()).thenReturn("email@email.com");
        when(dto.login()).thenReturn("login");
        when(dto.senha()).thenReturn("senha");
        when(dto.tipoUsuario()).thenReturn("CLIENTE");

        when(usuarioRepository.atualizaLoginSenhaUsuario(
                any(), any(), any(), any()
        )).thenReturn(1);

        assertDoesNotThrow(() -> gateway.atualizarLoginSenha(dto));
    }


    @Test
    void atualizarLoginSenha_semLinhasAfetadas_deveLancarExcecao() {
        AtualizaLoginSenhaUsuarioDTO dto = mock(AtualizaLoginSenhaUsuarioDTO.class);

        when(usuarioRepository.atualizaLoginSenhaUsuario(
                any(), any(), any(), any()
        )).thenReturn(0);

        assertThrows(
                BusinessException.class,
                () -> gateway.atualizarLoginSenha(dto)
        );
    }
    @Test
    void atualizarLoginSenha_deveChamarRepositoryComParametrosCorretos() {
        AtualizaLoginSenhaUsuarioDTO dto = mock(AtualizaLoginSenhaUsuarioDTO.class);

        when(dto.email()).thenReturn("email@email.com");
        when(dto.login()).thenReturn("login");
        when(dto.senha()).thenReturn("senha");
        when(dto.tipoUsuario()).thenReturn("CLIENTE");

        when(usuarioRepository.atualizaLoginSenhaUsuario(
                any(), any(), any(), any()
        )).thenReturn(1);

        gateway.atualizarLoginSenha(dto);

        verify(usuarioRepository).atualizaLoginSenhaUsuario(
                "email@email.com",
                "login",
                "senha",
                "CLIENTE"
        );
    }
}
