package br.com.fiap.gestaoestabelecimento.adapters.controllers;
import br.com.fiap.gestaoestabelecimento.domain.Cardapio;
import br.com.fiap.gestaoestabelecimento.application.CardapioUseCase;
import br.com.fiap.gestaoestabelecimento.dtos.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/cardapios/v1/")
public class CardapioController {

    private final CardapioUseCase cardapioUseCase;

    public CardapioController(
            CardapioUseCase cardapioUseCase) {
        this.cardapioUseCase = cardapioUseCase;
    }


    @GetMapping("/buscar-por-id")
    public ResponseEntity<Cardapio> buscarPorId(@RequestParam UUID IdCardapio) {
        Cardapio Cardapio = cardapioUseCase.buscarCardapioIdUseCase(IdCardapio);
        return ResponseEntity.ok(Cardapio);
    }

    @GetMapping("/lista")
    public ResponseEntity<List>lista() {
        Iterable<Cardapio> Cardapios = cardapioUseCase.listarCardapioUseCase();
        return ResponseEntity.ok(Collections.singletonList(Cardapios));
    }

    @PutMapping("/atualizar")
    public ResponseEntity<String> atualizar(@RequestBody AtualizaCardapioDTO atualizaCardapioDTO) {
        cardapioUseCase.updateCardapioUseCase(atualizaCardapioDTO);
        return ResponseEntity.ok("Item de cardapio atualizado com sucesso");
    }

    @PostMapping("/incluir")
    public ResponseEntity<String> incluir(@RequestBody IncluiCardapioDTO incluiCardapioDTO) {
        cardapioUseCase.incluirCardapioUseCase(incluiCardapioDTO);
        return ResponseEntity.ok("Cardapio incluído com sucesso");
    }

    @DeleteMapping("/excluir")
    public ResponseEntity<String> excluir(@RequestParam UUID idCardapio) {
        cardapioUseCase.deleteCardapioUseCase(idCardapio);
        return ResponseEntity.ok("Cardapio excluido com sucesso");
    }
}
