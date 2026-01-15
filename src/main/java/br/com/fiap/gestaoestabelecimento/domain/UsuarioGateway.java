package br.com.fiap.gestaoestabelecimento.domain;

import br.com.fiap.gestaoestabelecimento.infrastructure.entities.UsuarioEntity;
import br.com.fiap.gestaoestabelecimento.dtos.AtualizaLoginSenhaUsuarioDTO;

import java.util.List;

public interface UsuarioGateway {
   void atualizarLoginSenha(AtualizaLoginSenhaUsuarioDTO input);
   String validaAcessoUsuario(String login, String senha);

}
