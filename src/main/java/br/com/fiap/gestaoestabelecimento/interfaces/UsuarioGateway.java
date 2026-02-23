package br.com.fiap.gestaoestabelecimento.interfaces;

import br.com.fiap.gestaoestabelecimento.dtos.AtualizaLoginSenhaUsuarioDTO;

public interface UsuarioGateway {
   void atualizarLoginSenha(AtualizaLoginSenhaUsuarioDTO input);
   String validaAcessoUsuario(String login, String senha);

}
