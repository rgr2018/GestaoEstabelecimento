package br.com.fiap.gestaoestabelecimento.adapters.controllers;

import br.com.fiap.gestaoestabelecimento.domain.Estabelecimento;
import br.com.fiap.gestaoestabelecimento.domain.EstabelecimentoUseCase;
import br.com.fiap.gestaoestabelecimento.dtos.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/estabelecimentos/v1/")
public class EstabelecimentoController {

    private final EstabelecimentoUseCase estabelecimentoUseCase;

    public EstabelecimentoController(EstabelecimentoUseCase estabelecimentoUseCase) {
        this.estabelecimentoUseCase = estabelecimentoUseCase;
    }


    @GetMapping("/buscar-por-id")
    public ResponseEntity<Estabelecimento> buscarPorId(@RequestParam UUID IdEstabelecimento) {
        Estabelecimento estabelecimento = estabelecimentoUseCase.buscarEstabelecimentoIdUseCase(IdEstabelecimento);
        return ResponseEntity.ok(estabelecimento);
    }

    @GetMapping("/buscar-por-nome")
    public ResponseEntity<List<Estabelecimento>> buscarPorNome(@RequestParam String nome) {
        List<Estabelecimento> estabelecimentos =
                estabelecimentoUseCase.buscarEstabelecimentoNomeUseCase(nome);

        return ResponseEntity.ok(estabelecimentos);
    }


    @GetMapping("/lista")
    public ResponseEntity<List>lista() {
        Iterable<Estabelecimento> estabelecimentos = estabelecimentoUseCase.listarEstabelecimentoUseCase();
        return ResponseEntity.ok(Collections.singletonList(estabelecimentos));
    }

    @PutMapping("/atualizar")
    public ResponseEntity<String> atualizar(@RequestBody AtualizaEstabelecimentoDTO atualizaEstabelecimentoDTO) {
        estabelecimentoUseCase.updateEstabelecimentoUseCase(atualizaEstabelecimentoDTO);
        return ResponseEntity.ok("Estabelecimento atualizado com sucesso");
    }

    @PostMapping("/incluir")
    public ResponseEntity<String> incluir(@RequestBody IncluiEstabelecimentoDTO incluiEstabelecimentoDTO) {
        estabelecimentoUseCase.incluirEstabelecimentoUseCase(incluiEstabelecimentoDTO);
        return ResponseEntity.ok("Estabelecimento incluído com sucesso");
    }

    @DeleteMapping("/excluir")
    public ResponseEntity<String> excluir(@RequestParam UUID idEstabelecimento) {
        estabelecimentoUseCase.deleteEstabelecimentoUseCase(idEstabelecimento);
        return ResponseEntity.ok("Estabelecimento excluido com sucesso");
    }
}