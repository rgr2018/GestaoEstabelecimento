package br.com.fiap.gestaoestabelecimento.domain;

import br.com.fiap.gestaoestabelecimento.infrastructure.entities.DonoEntity;
import br.com.fiap.gestaoestabelecimento.dtos.*;

import java.util.List;
import java.util.UUID;

public interface DonoGateway {
    Dono saveDono(IncluiDonoDTO input);
    Dono updateDono(AtualizaDonoDTO input);
    Dono getDono(UUID id);
    Iterable<Dono> listaTodosDonos();
    Dono buscarDonoPorEmail(String email);
    List<Dono> buscarDonoPorNome(String nome);
    void deleteDono(String email);

}
