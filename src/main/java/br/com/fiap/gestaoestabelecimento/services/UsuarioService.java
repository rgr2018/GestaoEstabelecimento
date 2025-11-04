package br.com.fiap.gestaoestabelecimento.services;

import br.com.fiap.gestaoestabelecimento.dtos.*;
import br.com.fiap.gestaoestabelecimento.entities.Usuario;
import br.com.fiap.gestaoestabelecimento.exceptions.ResourceNotFoundException;
import br.com.fiap.gestaoestabelecimento.repositories.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public abstract class UsuarioService<T extends Usuario> {

    protected final UsuarioRepository<T> usuarioRepository;

    public UsuarioService(UsuarioRepository<T> usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<UsuarioNameDTO> findNomeUsuario(String nome, String tipoUsuario) {
        List<UsuarioNameDTO> usuarios = usuarioRepository.findByNome(nome, tipoUsuario);

        if (usuarios.isEmpty()) {
            throw new ResourceNotFoundException("Nenhum usuário encontrado com o nome informado.");
        }

        return usuarios;
    }

    public Long buscaUltimoId() {
        Long result = usuarioRepository.ultimoId();
        if (result == null) {
            // Caso nenhum usuário exista ainda
            return 0L;
        }
        return result;
    }

    public int validaAcesso(String login, String senha, String tipoUsuario) {
        return usuarioRepository.validaAcesso(login, senha, tipoUsuario);
    }

    public int validaExisteEmail(String email) {
        return usuarioRepository.validaExisteEmail(email);
    }

    public void atualizaLoginSenha(ValidaUsuarioDTO validaUsuarioDTO, String tipoUsuario) {
        int result = usuarioRepository.atualizaLoginSenha(validaUsuarioDTO, tipoUsuario);
        if (result == 0) {
            throw new ResourceNotFoundException("Usuário não encontrado para atualização de senha: " + validaUsuarioDTO.getEmail());
        }
    }

    public void atualizaDetalhesUsuario(AtualizaDetalhesClienteDTO usuario, String email, String tipoUsuario) {
        int result = usuarioRepository.atualizaDetalhesUsuario(usuario, email, tipoUsuario);
        if (result == 0) {
            throw new ResourceNotFoundException("Usuário não encontrado para atualização dos detalhes: " + email);
        }
    }


    public void delete(String email, String tipoUsuario) {
        int del = usuarioRepository.delete(email, tipoUsuario);
        if (del == 0) {
            throw new ResourceNotFoundException("Usuário não encontrado para exclusão: " + email);
        }
    }
}