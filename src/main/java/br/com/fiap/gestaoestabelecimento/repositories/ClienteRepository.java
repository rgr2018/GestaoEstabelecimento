package br.com.fiap.gestaoestabelecimento.repositories;
import br.com.fiap.gestaoestabelecimento.dtos.IncluiClienteDTO;
import br.com.fiap.gestaoestabelecimento.entities.Cliente;


public interface ClienteRepository {

    Integer atualizaDetalhesCliente(Cliente cliente,  Long id);
    Integer salvarUsuarioCliente(IncluiClienteDTO cliente, Long id, String tipoUsuario);
    Integer salvarClientes(IncluiClienteDTO cliente, Long id);

}