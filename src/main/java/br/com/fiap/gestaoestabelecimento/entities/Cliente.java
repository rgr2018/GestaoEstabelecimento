package br.com.fiap.gestaoestabelecimento.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public class Cliente extends Usuario{

    public String dataAniversario;
    public String dataCadastro;
    public String classificacao;
}
