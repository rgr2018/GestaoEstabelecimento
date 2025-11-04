package br.com.fiap.gestaoestabelecimento.services;

import br.com.fiap.gestaoestabelecimento.dtos.IncluiClienteDTO;
import br.com.fiap.gestaoestabelecimento.entities.Cliente;
import br.com.fiap.gestaoestabelecimento.exceptions.BusinessException;
import br.com.fiap.gestaoestabelecimento.repositories.ClienteRepository;
import br.com.fiap.gestaoestabelecimento.repositories.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class ClienteService extends UsuarioService<Cliente> {

    public ClienteService(ClienteRepository clienteRepositoryrepository) {
        super((UsuarioRepository<Cliente>) clienteRepositoryrepository);


        this.clienteRepository= clienteRepositoryrepository;
    }
    private final ClienteRepository clienteRepository;

    public int salvarCliente(IncluiClienteDTO  cliente, Long id){
        int result = this.clienteRepository.salvarClientes(cliente, id);
        if (result == 0) throw new RuntimeException("Erro ao Salvar cliente");
        return result;
    }

    public void atualizaCliente(Cliente cliente, Long id) {
        int result = this.clienteRepository.atualizaDetalhesCliente(cliente, id);
        if (result == 0) throw new RuntimeException("Usuário não encontrado para atualização");
    }

    public void salvaUsuarioCliente(IncluiClienteDTO incluiClienteDTO, Long id, String tipoUsuario) {
        int save = clienteRepository.salvarUsuarioCliente(incluiClienteDTO, id, tipoUsuario);
        if (save != 1) {
            throw new BusinessException("Erro ao salvar usuário: " + incluiClienteDTO.getEmail());
        }
    }


}