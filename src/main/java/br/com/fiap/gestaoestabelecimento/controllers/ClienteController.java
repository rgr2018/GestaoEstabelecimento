package br.com.fiap.gestaoestabelecimento.controllers;

import br.com.fiap.gestaoestabelecimento.dtos.*;

import br.com.fiap.gestaoestabelecimento.usecases.cliente.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes/v1/")
public class ClienteController {

    private final BuscarClientePorNomeUseCase buscarClientePorNomeUseCase;
    private final ValidarAcessoClienteUseCase validarAcessoClienteUseCase;
    private final AtualizarLoginSenhaClienteUseCase atualizarLoginSenhaClienteUseCase;
    private final IncluirClienteUseCase incluirClienteUseCase;
    private final ExcluirClienteUseCase excluirClienteUseCase;

    public ClienteController(
            BuscarClientePorNomeUseCase buscarClientePorNomeUseCase,
            ValidarAcessoClienteUseCase validarAcessoClienteUseCase,
            AtualizarLoginSenhaClienteUseCase atualizarLoginSenhaClienteUseCase,
            IncluirClienteUseCase incluirClienteUseCase,
            ExcluirClienteUseCase excluirClienteUseCase
    ) {
        this.buscarClientePorNomeUseCase = buscarClientePorNomeUseCase;
        this.validarAcessoClienteUseCase = validarAcessoClienteUseCase;
        this.atualizarLoginSenhaClienteUseCase = atualizarLoginSenhaClienteUseCase;
        this.incluirClienteUseCase = incluirClienteUseCase;
        this.excluirClienteUseCase = excluirClienteUseCase;
    }

    @GetMapping("/buscar-por-nome")
    public ResponseEntity<List<UsuarioNameDTO>> buscarPorNome(@RequestParam String nome) {
        List<UsuarioNameDTO> clientes = buscarClientePorNomeUseCase.executar(nome);
        return ResponseEntity.ok(clientes);
    }

    @PostMapping("/validar-acesso")
    public ResponseEntity<String> validarAcesso(@RequestBody ValidaUsuarioDTO dto) {
        validarAcessoClienteUseCase.executar(dto.getLogin(), dto.getSenha());
        return ResponseEntity.ok("Acesso válido");
    }

    @PutMapping("/atualizar-login-senha")
    public ResponseEntity<String> atualizarLoginSenha(@RequestBody ValidaUsuarioDTO dto) {
        atualizarLoginSenhaClienteUseCase.executar(dto);
        return ResponseEntity.ok("Login e senha atualizados com sucesso");
    }

    @PostMapping("/incluir")
    public ResponseEntity<String> incluir(@RequestBody IncluiClienteDTO dto) {
        incluirClienteUseCase.executar(dto);
        return ResponseEntity.ok("Cliente incluído com sucesso");
    }

    @DeleteMapping("/excluir")
    public ResponseEntity<String> excluir(@RequestParam String email) {
        excluirClienteUseCase.executar(email);
        return ResponseEntity.ok("Cliente excluído com sucesso");
    }
}
