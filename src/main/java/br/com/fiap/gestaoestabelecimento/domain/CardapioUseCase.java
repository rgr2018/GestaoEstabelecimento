package br.com.fiap.gestaoestabelecimento.domain;

import br.com.fiap.gestaoestabelecimento.adapters.gateways.CardapioGatewayImp;
import br.com.fiap.gestaoestabelecimento.dtos.AtualizaCardapioDTO;
import br.com.fiap.gestaoestabelecimento.dtos.IncluiCardapioDTO;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CardapioUseCase {

    private final CardapioGatewayImp cardapioGatewayImp;

    public CardapioUseCase(CardapioGatewayImp cardapioGatewayImp) {
        this.cardapioGatewayImp = cardapioGatewayImp;
    }

    public Cardapio incluirCardapioUseCase(IncluiCardapioDTO dto) {return cardapioGatewayImp.saveCardapio(dto);}

    public void updateCardapioUseCase(AtualizaCardapioDTO dto) {
        cardapioGatewayImp.updateCardapio(dto);
    }

    public Cardapio buscarCardapioIdUseCase(UUID id) {
        return cardapioGatewayImp.getCardapio(id);
    }

    public Iterable<Cardapio> listarCardapioUseCase() {
        return cardapioGatewayImp.getAllCardapio();
    }

    public void deleteCardapioUseCase(UUID idCardapio) {
        cardapioGatewayImp.deleteCardapio(idCardapio);
    }
}
