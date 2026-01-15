package br.com.fiap.gestaoestabelecimento.infrastructure.repositories;

import br.com.fiap.gestaoestabelecimento.infrastructure.entities.EnderecoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EnderecoRepository  extends JpaRepository<EnderecoEntity, UUID> {
}
