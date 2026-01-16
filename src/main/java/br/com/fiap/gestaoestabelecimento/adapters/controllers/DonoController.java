package br.com.fiap.gestaoestabelecimento.adapters.controllers;

import br.com.fiap.gestaoestabelecimento.domain.Dono;
import br.com.fiap.gestaoestabelecimento.domain.DonoUseCase;
import br.com.fiap.gestaoestabelecimento.dtos.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/donos/v1/")
public class DonoController {

    private final DonoUseCase donoUseCase;

    public DonoController(
            DonoUseCase donoUseCase) {
        this.donoUseCase = donoUseCase;
    }


    @GetMapping("/buscar-por-nome")
    public ResponseEntity<List> buscarPorNome(@RequestParam String nome) {
        Iterable<Dono> dono = donoUseCase.buscarDonoNomeUseCase(nome);
        return ResponseEntity.ok(Collections.singletonList(dono));
    }

    @GetMapping("/buscar-por-email")
    public ResponseEntity<Dono> buscarPorEmail(@RequestParam String email) {
        Dono dono = donoUseCase.buscardonoEmailUseCase(email);
        return ResponseEntity.ok(dono);
    }

    @GetMapping("/buscar-por-id")
    public ResponseEntity<Dono> buscarPorId(@RequestParam UUID Iddono) {
        Dono dono = donoUseCase.buscardonoIdUseCase(Iddono);
        return ResponseEntity.ok(dono);
    }

    @GetMapping("/lista")
    public ResponseEntity<List> lista() {
        Iterable<Dono> donos = donoUseCase.listaTodosDonosUseCase();
        return ResponseEntity.ok(Collections.singletonList(donos));
    }

    @GetMapping("/validar-acesso")
    public ResponseEntity<String> validarAcesso(@RequestBody ValidaUsuarioDTO validaUsuarioDTO) {
        donoUseCase.validaAcesso(validaUsuarioDTO);
        return ResponseEntity.ok("Acesso válido");
    }


    @PutMapping("/atualizar-login-senha")
    public ResponseEntity<String> atualizarLoginSenha(@RequestBody AtualizaLoginSenhaDTO atualizaLoginSenhaDTO) {
        donoUseCase.atualizaLogin(atualizaLoginSenhaDTO);
        return ResponseEntity.ok("Login e senha atualizados com sucesso");
    }

    @PutMapping("/atualizar")
    public ResponseEntity<String> atualizar(@RequestBody AtualizaDonoDTO atualizaDonoDTO) {
        donoUseCase.atualizarDonoUserCase(atualizaDonoDTO);
        return ResponseEntity.ok("Dados de dono atualizados com sucesso");
    }

    @PostMapping("/incluir")
    public ResponseEntity<String> incluir(@RequestBody IncluiDonoDTO incluiDonoDTO) {
        donoUseCase.incluirDonoUseCase(incluiDonoDTO);
        return ResponseEntity.ok("dono incluído com sucesso");
    }

    @DeleteMapping("/excluir")
    public ResponseEntity<String> excluir(@RequestParam String email) {
        donoUseCase.deletarDonoPorEmailUseCase(email);
        return ResponseEntity.ok("dono incluído com sucesso");
    }
}