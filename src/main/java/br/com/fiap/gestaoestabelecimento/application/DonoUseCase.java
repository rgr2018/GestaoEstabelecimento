package br.com.fiap.gestaoestabelecimento.application;

import br.com.fiap.gestaoestabelecimento.domain.*;
import br.com.fiap.gestaoestabelecimento.infrastructure.entities.DonoEntity;
import br.com.fiap.gestaoestabelecimento.infrastructure.entities.UsuarioEntity;
import br.com.fiap.gestaoestabelecimento.dtos.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class DonoUseCase {
    private final DonoGateway donoGateway;
    private final UsuarioGateway usuarioGateway;


    public DonoUseCase(DonoGateway donoGateway, UsuarioGateway usuarioGateway) {
        this.donoGateway = donoGateway;
        this.usuarioGateway = usuarioGateway;
    }

    // -------------------------------------------------------------------------
    //  USE CASE — INCLUIR dono    // -------------------------------------------------------------------------
    @Transactional
    public void incluirDonoUseCase(IncluiDonoDTO incluiDonoDTO) {
        donoGateway.saveDono(incluiDonoDTO);
    }


    // -------------------------------------------------------------------------
    //  USE CASE — ATUALIZAR dono    // -------------------------------------------------------------------------
    @Transactional
    public Dono atualizarDonoUserCase(AtualizaDonoDTO input) {

        return donoGateway.updateDono(input);
    }

    public Dono buscardonoIdUseCase(UUID id) {
        return donoGateway.getDono(id);
    }

    public  Iterable<Dono> buscarDonoNomeUseCase(String nome) {
        return  donoGateway.buscarDonoPorNome(nome);
    }

    public String validaAcesso(ValidaUsuarioDTO validaUsuarioDTO) {
        return usuarioGateway.validaAcessoUsuario(validaUsuarioDTO.login(),validaUsuarioDTO.senha());
    }

    public Dono buscardonoEmailUseCase(String email) {
        return  donoGateway.buscarDonoPorEmail(email);
    }

    @Transactional
    public  void atualizaLogin(AtualizaLoginSenhaDTO input) {
        String tipoCliente = "DONO";
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
    //  USE CASE — LISTAR TODOS
    // -------------------------------------------------------------------------
    public Iterable<Dono> listaTodosDonosUseCase() {
        return donoGateway.listaTodosDonos();
    }

    // -------------------------------------------------------------------------
    //  USE CASE — DELETAR POR EMAIL + TIPO (se você realmente usar isso)
    // -------------------------------------------------------------------------
    @Transactional
    public void deletarDonoPorEmailUseCase(String email) {
        donoGateway.deleteDono(email);
    }
}
