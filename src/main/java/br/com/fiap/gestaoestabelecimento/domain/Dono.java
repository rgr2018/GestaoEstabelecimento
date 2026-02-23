package br.com.fiap.gestaoestabelecimento.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import java.util.UUID;


@Getter
@EqualsAndHashCode(callSuper = true)
@ToString
public class Dono extends Usuario {

    private final UUID idDono;

    public Dono(
            UUID idDono,
            Usuario usuario
    ) {
        super(usuario.getIdUsuario(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getLogin(),
                usuario.getSenha(),
                usuario.getTipoUsuario(),
                usuario.getDataUltimaAlteracao(),
                usuario.getEndereco());;
        this.idDono = idDono;
    }

    public UUID getIdDono() {
        return idDono;
    }
}
