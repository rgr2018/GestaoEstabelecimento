package br.com.fiap.gestaoestabelecimento.infrastructure.mappers;
import br.com.fiap.gestaoestabelecimento.domain.Usuario;
import br.com.fiap.gestaoestabelecimento.dtos.AtualizaUsuarioDTO;
import br.com.fiap.gestaoestabelecimento.dtos.IncluiUsuarioDTO;
import br.com.fiap.gestaoestabelecimento.infrastructure.entities.EnderecoEntity;
import br.com.fiap.gestaoestabelecimento.infrastructure.entities.UsuarioEntity;

import java.time.LocalDateTime;

public class UsuarioMapper {

    private UsuarioMapper() {}

    public static UsuarioEntity toEntity(IncluiUsuarioDTO dto, String tipoUsuario) {

        UsuarioEntity entity = new UsuarioEntity();
        entity.setNome(dto.nome());
        entity.setEmail(dto.email());
        entity.setLogin(dto.login());
        entity.setSenha(dto.senha());
        entity.setTipoUsuario(tipoUsuario);
        entity.setDataUltimaAlteracao(String.valueOf(LocalDateTime.now()));

        return entity;
    }

    public static UsuarioEntity toEntity(UsuarioEntity usuario) {

        UsuarioEntity entity = new UsuarioEntity();
        entity.setIdUsuario((usuario.getIdUsuario()));
        entity.setNome(usuario.getNome());
        entity.setEmail(usuario.getEmail());
        entity.setLogin(usuario.getLogin());
        entity.setSenha(usuario.getSenha());
        entity.setTipoUsuario(usuario.getTipoUsuario());
        entity.setDataUltimaAlteracao(String.valueOf(LocalDateTime.now()));
        return entity;
    }



    public static UsuarioEntity toEntityAtualiza(UsuarioEntity usuarioEntity , EnderecoEntity enderecoEntity, AtualizaUsuarioDTO dto, String tipoUsuario){
        usuarioEntity.setNome(dto.nome());
        usuarioEntity.setEmail(dto.email());
        usuarioEntity.setLogin(dto.login());
        usuarioEntity.setSenha(dto.senha());
        usuarioEntity.setTipoUsuario(tipoUsuario);
        usuarioEntity.setDataUltimaAlteracao(LocalDateTime.now().toString());
        usuarioEntity.setEnderecoEntity(enderecoEntity);

        return usuarioEntity;
    }

    public static Usuario toDomain(UsuarioEntity entity) {

        EnderecoEntity endereco = entity.getEnderecoEntity();

        return new Usuario(
                entity.getIdUsuario(),
                entity.getNome(),
                entity.getEmail(),
                entity.getLogin(),
                entity.getSenha(),
                entity.getTipoUsuario(),
                entity.getDataUltimaAlteracao(),
                endereco != null
                        ? EnderecoMapper.toDomain(endereco)
                        : null
        );
    }
}