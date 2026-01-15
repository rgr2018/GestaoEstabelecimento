package br.com.fiap.gestaoestabelecimento.domain;

import br.com.fiap.gestaoestabelecimento.dtos.*;
import br.com.fiap.gestaoestabelecimento.infrastructure.entities.ClienteEntity;

import java.util.List;
import java.util.UUID;

public interface ClienteGateway {
    Cliente saveCliente(IncluiClienteDTO input);
    Cliente updateCliente(AtualizaClienteDTO input);
    Cliente getCliente(UUID id);
    Iterable<Cliente> listaTodosClientes();
    List<Cliente> buscarClientePorNome(String nome);
    Cliente buscarClientePorEmail (String email);
    void deleteCliente(String email);


}
