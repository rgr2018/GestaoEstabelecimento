package br.com.fiap.gestaoestabelecimento.repositories;


import br.com.fiap.gestaoestabelecimento.dtos.IncluiDonoDTO;
import br.com.fiap.gestaoestabelecimento.entities.Dono;

public interface DonoRepository {

    Integer atualizaDetalhesDono(Dono dono, Long id);
    Integer salvaDono(IncluiDonoDTO dono, Long id);
    Integer salvarDonoUsuario(IncluiDonoDTO dono, Long id, String tipoUsuario);

}
