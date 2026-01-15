package br.com.fiap.gestaoestabelecimento.infrastructure.repositories;

import br.com.fiap.gestaoestabelecimento.domain.Cardapio;
import br.com.fiap.gestaoestabelecimento.infrastructure.entities.CardapioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface CardapioRepository extends JpaRepository<CardapioEntity, UUID> {


}
