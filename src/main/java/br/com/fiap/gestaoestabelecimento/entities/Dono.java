package br.com.fiap.gestaoestabelecimento.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString
public class Dono extends Usuario{

    public String nomeEstabelecimento;
    public TipoEstabelecimento tipoEstabelecimento;

}
