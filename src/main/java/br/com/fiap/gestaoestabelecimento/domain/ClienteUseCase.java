package br.com.fiap.gestaoestabelecimento.domain;

import br.com.fiap.gestaoestabelecimento.dtos.*;
import br.com.fiap.gestaoestabelecimento.interfaces.ClienteGateway;
import br.com.fiap.gestaoestabelecimento.interfaces.UsuarioGateway;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ClienteUseCase {

    private final ClienteGateway clienteGateway;
    private final UsuarioGateway usuarioGateway;


    public ClienteUseCase(ClienteGateway clienteGateway, UsuarioGateway usuarioGateway) {
        this.clienteGateway = clienteGateway;
        this.usuarioGateway = usuarioGateway;
    }

    // -------------------------------------------------------------------------
    //  USE CASE — INCLUIR CLIENTE
    // -------------------------------------------------------------------------
    @Transactional
    public void incluirClienteUseCase(IncluiClienteDTO incluiClienteDTO) {
        clienteGateway.saveCliente(incluiClienteDTO);
    }

    // -------------------------------------------------------------------------
    //  USE CASE — ATUALIZAR CLIENTE
    // -------------------------------------------------------------------------
    @Transactional
    public  void atualizaLogin(AtualizaLoginSenhaDTO input) {
        String tipoCliente = "CLIENTE";
        AtualizaLoginSenhaUsuarioDTO dto =
                new AtualizaLoginSenhaUsuarioDTO(
                        input.email(),
                        input.login(),
                        input.senha(),
                        tipoCliente
                );

        usuarioGateway.atualizarLoginSenha(dto);
    }


    // -------------------------------------------------------------------------
    //  USE CASE — ATUALIZAR CLIENTE
    // -------------------------------------------------------------------------
    @Transactional
    public Cliente atualizarClienteUserCase(AtualizaClienteDTO input) {
        return clienteGateway.updateCliente(input);
    }

    public Cliente buscarClienteIdUseCase(UUID id) {
        return clienteGateway.getCliente(id);
    }

    public String validaAcesso(ValidaUsuarioDTO validaUsuarioDTO) {

        return usuarioGateway.validaAcessoUsuario(validaUsuarioDTO.login(),validaUsuarioDTO.senha());
    }

    public Cliente buscarClienteEmailUseCase(String email) {

        return  clienteGateway.buscarClientePorEmail(email);
    }

    public  Iterable<Cliente> buscarClienteNomeUseCase(String nome) {

        return clienteGateway.buscarClientePorNome(nome);
    }

    // -------------------------------------------------------------------------
    //  USE CASE — LISTAR TODOS
    // -------------------------------------------------------------------------
    public Iterable<Cliente> listarTodosClientesUseCase() {
        return clienteGateway.listaTodosClientes();
    }

     // -------------------------------------------------------------------------
    //  USE CASE — DELETAR POR EMAIL + TIPO (se você realmente usar isso)
    // -------------------------------------------------------------------------
    @Transactional
    public void deletarClientePorEmailUseCase(String email) {
        clienteGateway.deleteCliente(email);

    }
}
