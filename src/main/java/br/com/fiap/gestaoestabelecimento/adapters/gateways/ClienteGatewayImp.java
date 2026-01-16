package br.com.fiap.gestaoestabelecimento.adapters.gateways;

import br.com.fiap.gestaoestabelecimento.domain.Cliente;
import br.com.fiap.gestaoestabelecimento.interfaces.ClienteGateway;
import br.com.fiap.gestaoestabelecimento.dtos.*;
import br.com.fiap.gestaoestabelecimento.exceptions.BusinessException;
import br.com.fiap.gestaoestabelecimento.exceptions.ValidationException;
import br.com.fiap.gestaoestabelecimento.infrastructure.entities.*;
import br.com.fiap.gestaoestabelecimento.infrastructure.mappers.ClienteMapper;
import br.com.fiap.gestaoestabelecimento.infrastructure.mappers.EnderecoMapper;
import br.com.fiap.gestaoestabelecimento.infrastructure.mappers.UsuarioMapper;
import br.com.fiap.gestaoestabelecimento.infrastructure.repositories.ClienteRepository;
import br.com.fiap.gestaoestabelecimento.infrastructure.repositories.EnderecoRepository;
import br.com.fiap.gestaoestabelecimento.infrastructure.repositories.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ClienteGatewayImp implements ClienteGateway {


    private final ClienteRepository clienteRepository;
    private final UsuarioRepository usuarioRepository;
    private final EnderecoRepository enderecoRepository;
    public ClienteGatewayImp(
            ClienteRepository clienteRepository,
            UsuarioRepository usuarioRepository,
            EnderecoRepository enderecoRepository
    ) {
        this.clienteRepository = clienteRepository;
        this.usuarioRepository = usuarioRepository;
        this.enderecoRepository = enderecoRepository;
    }

    public List<Cliente> buscarClientePorNome(String nome) {

        List<ClienteEntity> clientes = clienteRepository.buscarClientesPorNome(nome, "CLIENTE");

        if (clientes.isEmpty()) {
            throw new BusinessException("Nenhum cliente encontrado com nome " + nome);
        }

        return clientes.stream()
                .map(ClienteMapper::toDomain)
                .toList();

    }


    public Cliente buscarClientePorEmail(String email) {

        ClienteEntity cliente =  clienteRepository.buscarClientePorEmail(email, "CLIENTE");

        if (cliente == null) {
            throw new BusinessException("Nenhum cliente encontrado com nome " + email);
        }


       return ClienteMapper.toDomain(cliente);


    }

    @Transactional
    public Cliente saveCliente(IncluiClienteDTO input) {

        long validaEmail = usuarioRepository.validePorEmail(input.usuarioDTO().email());

        if (validaEmail >  0 ) {
            throw new ValidationException("E-mail já cadastrado. Cadastrar outro e-mail.");
        }

        EnderecoEntity enderecoEntity = EnderecoMapper.toEntity(input.usuarioDTO().endereco());
        enderecoRepository.save(enderecoEntity);

        UsuarioEntity usuarioEntity = UsuarioMapper.toEntity(input.usuarioDTO(), "CLIENTE");
        usuarioEntity.setEnderecoEntity(enderecoEntity);
        usuarioEntity.setDataUltimaAlteracao(String.valueOf(LocalDateTime.now()));
        usuarioRepository.save(usuarioEntity);


        ClienteEntity clienteEntity = ClienteMapper.toEntity(input);
        clienteEntity.setUsuarioEntity(usuarioEntity);
        clienteEntity.setDataHoraCadastro(LocalDateTime.now());
        clienteRepository.save(clienteEntity);

        Cliente cliente = ClienteMapper.toDomain(clienteEntity);
        return cliente;
    }

    @Transactional
    public Cliente updateCliente(AtualizaClienteDTO input) {

        if (input.usuarioDTO() == null) {
            throw new BusinessException("O campo usuarioDTO não pode ser nulo");
        }

        if (input.usuarioDTO().endereco() == null) {
            throw new BusinessException("O campo endereco dentro de usuarioDTO não pode ser nulo");
        }

        ClienteEntity clienteEntity = clienteRepository.findById(input.idCliente())
                .orElseThrow(() -> new BusinessException("Cliente não encontrado"));

        UsuarioEntity usuarioEntity = usuarioRepository.findById(input.usuarioDTO().idUsuario())
                .orElseThrow(() -> new BusinessException("Usuário não encontrado"));

        EnderecoEntity enderecoEntity= enderecoRepository.findById(input.usuarioDTO().endereco().idEndereco())
                .orElseThrow(() -> new BusinessException("Endereço não encontrado"));

        EnderecoEntity endereco = EnderecoMapper.toEntityAtualiza(enderecoEntity, input.usuarioDTO().endereco());
        UsuarioEntity usuario = UsuarioMapper.toEntityAtualiza(usuarioEntity,endereco, input.usuarioDTO(), "CLIENTE");
        ClienteMapper.toEntityAtualiza(clienteEntity, usuario, input);
        return ClienteMapper.toDomain(clienteEntity);
    }

    public Cliente getCliente(UUID idCliente) {

        if (idCliente == null) {
            throw new BusinessException("O campo idCliente está nulo");
        }

        ClienteEntity clienteEntity = clienteRepository.buscaClientePorId(idCliente);
        if (clienteEntity == null) {
            throw new BusinessException("Nenhum cliente encontrado com nome " + idCliente);
        }
        return ClienteMapper.toDomain(clienteEntity);
    }

    public Iterable<Cliente> listaTodosClientes() {

        List<ClienteEntity> clientes = clienteRepository.listaTodosClientes();

        if (clientes.isEmpty()) {
            throw new BusinessException("Nenhum cliente encontrado");
        }

        return clientes.stream()
                .map(ClienteMapper::toDomain)
                .toList();
    }

    @Transactional
    public void deleteCliente(String email) {

        if (email== null) {
            throw new BusinessException("O campo usuarioDTO não pode ser nulo");
        }

        long validaEmail = usuarioRepository.validePorEmail(email);

        if (validaEmail ==  0 ) {
            throw new ValidationException("Email não encontrado");
        }

       UUID id_usuario = usuarioRepository.buscaIdPorEmail(email,"CLIENTE");
       UUID id_endereco = usuarioRepository.buscaIdEnderecoPorEmail(email);
       UUID id_cliente = usuarioRepository.buscaIdclientePoridUsuario(id_usuario);

       enderecoRepository.deleteById(id_endereco);
       clienteRepository.deleteById(id_cliente);
       usuarioRepository.deleteById(id_usuario);

    }
}