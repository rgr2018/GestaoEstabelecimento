package br.com.fiap.gestaoestabelecimento.domain;

import br.com.fiap.gestaoestabelecimento.dtos.AtualizaCardapioDTO;
import br.com.fiap.gestaoestabelecimento.dtos.IncluiCardapioDTO;

import java.util.List;
import java.util.UUID;

public interface CardapioGateway {
    Cardapio saveCardapio(IncluiCardapioDTO input);

    void updateCardapio(AtualizaCardapioDTO input);

    void deleteCardapio(UUID idCardapio);

    Cardapio getCardapio(UUID idCardapio);

    List<Cardapio> getAllCardapio();
}