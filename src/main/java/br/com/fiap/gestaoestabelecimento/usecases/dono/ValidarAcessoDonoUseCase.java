package br.com.fiap.gestaoestabelecimento.usecases.dono;

import br.com.fiap.gestaoestabelecimento.entities.TipoCliente;
import br.com.fiap.gestaoestabelecimento.exceptions.ValidationException;
import br.com.fiap.gestaoestabelecimento.services.DonoService;
import org.springframework.stereotype.Component;

@Component
public class ValidarAcessoDonoUseCase {

    private final DonoService donoService;

    public ValidarAcessoDonoUseCase(DonoService donoService) {
        this.donoService = donoService;
    }

    public void executar(String login, String senha) {
        int result = donoService.validaAcesso(login, senha, TipoCliente.DONO.getCodigo());
        if (result != 1) {
            throw new ValidationException("Usuário ou senha inválidos.");
        }
    }
}
