package br.com.fiap.gestaoestabelecimento.adapters.gateways;

import br.com.fiap.gestaoestabelecimento.domain.Cardapio;
import br.com.fiap.gestaoestabelecimento.interfaces.CardapioGateway;
import br.com.fiap.gestaoestabelecimento.infrastructure.entities.EstabelecimentoEntity;
import br.com.fiap.gestaoestabelecimento.dtos.AtualizaCardapioDTO;
import br.com.fiap.gestaoestabelecimento.dtos.IncluiCardapioDTO;
import br.com.fiap.gestaoestabelecimento.infrastructure.entities.CardapioEntity;
import br.com.fiap.gestaoestabelecimento.exceptions.BusinessException;
import br.com.fiap.gestaoestabelecimento.infrastructure.mappers.*;
import br.com.fiap.gestaoestabelecimento.infrastructure.repositories.CardapioRepository;
import br.com.fiap.gestaoestabelecimento.infrastructure.repositories.EstabelecimentoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class CardapioGatewayImp implements CardapioGateway {

    private final CardapioRepository cardapioRepository;
    private final EstabelecimentoRepository estabelecimentoRepository;

    public CardapioGatewayImp(CardapioRepository cardapioRepository,
                              EstabelecimentoRepository estabelecimentoRepository) {
        this.cardapioRepository = cardapioRepository;
        this.estabelecimentoRepository = estabelecimentoRepository;
    }

    @Transactional
    public Cardapio saveCardapio(IncluiCardapioDTO input) {

        EstabelecimentoEntity estabelecimento = estabelecimentoRepository
                .findById(input.idEstabelecimento())
                .orElseThrow(() -> new BusinessException("Estabelecimento não encontrado"));


        // Mapper domínio → entity
        CardapioEntity entity = CardapioMapper.toEntity(input);
        entity.setDataHoraCadastro(LocalDateTime.now());
        entity.getEstabelecimentoEntities().add(estabelecimento);

        CardapioEntity salvo = cardapioRepository.save(entity);

        // Retorna domínio
        return CardapioMapper.toDomain(salvo);
    }

    @Transactional
    public void updateCardapio(AtualizaCardapioDTO input) {

        CardapioEntity cardapio =   cardapioRepository.findById(input.idCardapio())
                .orElseThrow(() -> new BusinessException("Cardápio não encontrado"));

        EstabelecimentoEntity estabelecimento =  estabelecimentoRepository
                .findById(input.idEstabelecimento())
                .orElseThrow(() -> new BusinessException("Estabelecimento não encontrado"));


        CardapioMapper.toEntityAtualiza(input, cardapio ,estabelecimento);
    }

    @Override
    public void deleteCardapio(UUID idCardapio) {
        CardapioEntity entity = cardapioRepository.findById(idCardapio)
                .orElseThrow(() -> new BusinessException("Cardápio não encontrado"));

        cardapioRepository.delete(entity);
    }

    @Override
    public Cardapio getCardapio(UUID idCardapio) {

        CardapioEntity entity = cardapioRepository.findById(idCardapio)
                .orElseThrow(() -> new BusinessException("Cardápio não encontrado"));

        return CardapioMapper.toDomain(entity);
    }

    @Override
    public List<Cardapio> getAllCardapio() {
        return cardapioRepository.findAll()
                .stream()
                .map(CardapioMapper::toDomain)
                .toList();
    }
}