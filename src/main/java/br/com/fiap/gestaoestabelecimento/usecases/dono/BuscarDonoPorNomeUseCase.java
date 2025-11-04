package br.com.fiap.gestaoestabelecimento.usecases.dono;

import br.com.fiap.gestaoestabelecimento.dtos.UsuarioNameDTO;
import br.com.fiap.gestaoestabelecimento.entities.TipoCliente;
import br.com.fiap.gestaoestabelecimento.exceptions.ResourceNotFoundException;
import br.com.fiap.gestaoestabelecimento.services.DonoService;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BuscarDonoPorNomeUseCase {

    private final DonoService donoService;

    public BuscarDonoPorNomeUseCase(DonoService donoService) {
        this.donoService = donoService;
    }

    public List<UsuarioNameDTO> executar(String nome) {
        List<UsuarioNameDTO> usuarios = donoService.findNomeUsuario(nome, TipoCliente.DONO.getCodigo());

        if (usuarios.isEmpty()) {
            throw new ResourceNotFoundException("Nenhum cliente encontrado com o nome informado.");
        }

        return usuarios;
    }
}