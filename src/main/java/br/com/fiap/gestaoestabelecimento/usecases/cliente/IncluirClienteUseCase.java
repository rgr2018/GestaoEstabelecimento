package br.com.fiap.gestaoestabelecimento.usecases.cliente;

import br.com.fiap.gestaoestabelecimento.dtos.IncluiClienteDTO;
import br.com.fiap.gestaoestabelecimento.entities.TipoCliente;
import br.com.fiap.gestaoestabelecimento.exceptions.ValidationException;
import br.com.fiap.gestaoestabelecimento.services.ClienteService;
import org.springframework.stereotype.Component;

@Component
public class IncluirClienteUseCase {

    private final ClienteService clienteService;

    public IncluirClienteUseCase(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    public void executar(IncluiClienteDTO incluiClienteDTO) {
        int existe = clienteService.validaExisteEmail(incluiClienteDTO.getEmail());
        if (existe == 1) {
            throw new ValidationException("E-mail já cadastrado. Cadastrar outro e-mail.");
        }

        Long ultimoId = clienteService.buscaUltimoId();
        clienteService.salvaUsuarioCliente(incluiClienteDTO, ultimoId + 1, TipoCliente.CLIENTE.getCodigo());
        clienteService.salvarCliente(incluiClienteDTO, ultimoId + 1);
    }
}