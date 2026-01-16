package br.com.fiap.gestaoestabelecimento.interfaces;

import br.com.fiap.gestaoestabelecimento.domain.Cliente;
import br.com.fiap.gestaoestabelecimento.dtos.*;

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
