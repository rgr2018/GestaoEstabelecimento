package br.com.fiap.gestaoestabelecimento.usecases.cliente;

import br.com.fiap.gestaoestabelecimento.entities.TipoCliente;
import br.com.fiap.gestaoestabelecimento.exceptions.ValidationException;
import br.com.fiap.gestaoestabelecimento.services.ClienteService;
import org.springframework.stereotype.Component;

@Component
public class ValidarAcessoClienteUseCase {

    private final ClienteService clienteService;

    public ValidarAcessoClienteUseCase(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    public void executar(String login, String senha) {
        int result = clienteService.validaAcesso(login, senha, TipoCliente.CLIENTE.getCodigo());
        if (result != 1) {
            throw new ValidationException("Usuário ou senha inválidos.");
        }
    }
}