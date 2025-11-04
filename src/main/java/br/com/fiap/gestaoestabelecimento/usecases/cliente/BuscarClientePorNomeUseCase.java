package br.com.fiap.gestaoestabelecimento.usecases.cliente;

import br.com.fiap.gestaoestabelecimento.dtos.UsuarioNameDTO;
import br.com.fiap.gestaoestabelecimento.entities.TipoCliente;
import br.com.fiap.gestaoestabelecimento.exceptions.ResourceNotFoundException;
import br.com.fiap.gestaoestabelecimento.services.ClienteService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BuscarClientePorNomeUseCase {

    private final ClienteService clienteService;

    public BuscarClientePorNomeUseCase(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    public List<UsuarioNameDTO> executar(String nome) {
        List<UsuarioNameDTO> usuarios = clienteService.findNomeUsuario(nome, TipoCliente.CLIENTE.getCodigo());

        if (usuarios.isEmpty()) {
            throw new ResourceNotFoundException("Nenhum cliente encontrado com o nome informado.");
        }

        return usuarios;
    }
}