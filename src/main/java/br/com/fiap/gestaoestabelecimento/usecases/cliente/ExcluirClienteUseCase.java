package br.com.fiap.gestaoestabelecimento.usecases.cliente;

import br.com.fiap.gestaoestabelecimento.entities.TipoCliente;
import br.com.fiap.gestaoestabelecimento.services.ClienteService;
import org.springframework.stereotype.Component;

@Component
public class ExcluirClienteUseCase {

    private final ClienteService clienteService;

    public ExcluirClienteUseCase(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    public void executar(String email) {
        clienteService.delete(email, TipoCliente.CLIENTE.getCodigo());
    }
}