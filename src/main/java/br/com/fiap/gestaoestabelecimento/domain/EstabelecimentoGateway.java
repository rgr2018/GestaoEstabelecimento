package br.com.fiap.gestaoestabelecimento.domain;

import br.com.fiap.gestaoestabelecimento.infrastructure.entities.EstabelecimentoEntity;
import br.com.fiap.gestaoestabelecimento.dtos.AtualizaEstabelecimentoDTO;
import br.com.fiap.gestaoestabelecimento.dtos.IncluiEstabelecimentoDTO;

import java.util.*;

public interface EstabelecimentoGateway {
    Estabelecimento saveEstabelecimento(IncluiEstabelecimentoDTO input);
    void updateEstabelecimento(AtualizaEstabelecimentoDTO input);
    void deleteEstabelecimento(UUID id);
    Estabelecimento getEstabelecimento(UUID id);
    List<Estabelecimento> getAllEstabelecimento();
    List<Estabelecimento> buscaPorNome(String nome);
}
