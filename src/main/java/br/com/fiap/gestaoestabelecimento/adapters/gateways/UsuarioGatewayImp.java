package br.com.fiap.gestaoestabelecimento.adapters.gateways;
import br.com.fiap.gestaoestabelecimento.interfaces.UsuarioGateway;
import br.com.fiap.gestaoestabelecimento.dtos.AtualizaLoginSenhaUsuarioDTO;
import br.com.fiap.gestaoestabelecimento.exceptions.BusinessException;
import br.com.fiap.gestaoestabelecimento.infrastructure.repositories.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class UsuarioGatewayImp implements UsuarioGateway {

    private final UsuarioRepository usuarioRepository;

    public UsuarioGatewayImp(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public boolean emailExiste(String email) {
        return usuarioRepository.validePorEmail(email) > 0;
    }

    public String validaAcessoUsuario(String login, String senha){


        String valido =  usuarioRepository.validaAcessoUsuario(login,senha);
            if (valido == null){
                throw new  BusinessException("login ou senha invalido: " + login);
            }
        return valido;
    }

    @Transactional
    public void atualizarLoginSenha(AtualizaLoginSenhaUsuarioDTO input) {


        int linhasAfetadas = usuarioRepository.atualizaLoginSenhaUsuario(
                input.email(),
                input.login(),
                input.senha(),
                input.tipoUsuario()
        );

        if (linhasAfetadas == 0) {
            throw new BusinessException("Não foi possível atualizar os dados do usuário");
        }
    }


}