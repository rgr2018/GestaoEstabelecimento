package br.com.fiap.gestaoestabelecimento.usecases.cliente;

import br.com.fiap.gestaoestabelecimento.dtos.ValidaUsuarioDTO;
import br.com.fiap.gestaoestabelecimento.entities.TipoCliente;
import br.com.fiap.gestaoestabelecimento.services.ClienteService;
import org.springframework.stereotype.Component;

@Component
public class AtualizarLoginSenhaClienteUseCase {

    private final ClienteService clienteService;

    public AtualizarLoginSenhaClienteUseCase(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    public void executar(ValidaUsuarioDTO validaUsuarioDTO) {
        clienteService.atualizaLoginSenha(validaUsuarioDTO, TipoCliente.CLIENTE.getCodigo());
    }
}