package br.com.fiap.gestaoestabelecimento.usecases.dono;


import br.com.fiap.gestaoestabelecimento.dtos.ValidaUsuarioDTO;
import br.com.fiap.gestaoestabelecimento.entities.TipoCliente;
import br.com.fiap.gestaoestabelecimento.services.DonoService;
import org.springframework.stereotype.Component;

@Component
public class AtualizarLoginSenhaDonoUseCase {

    private final DonoService donoService;

    public AtualizarLoginSenhaDonoUseCase(DonoService donoService) {
        this.donoService = donoService;
    }

    public void executar(ValidaUsuarioDTO dto) {
        donoService.atualizaLoginSenha(dto, TipoCliente.DONO.getCodigo());
    }
}