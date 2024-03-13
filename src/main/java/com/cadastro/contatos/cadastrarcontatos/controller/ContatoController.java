package com.cadastro.contatos.cadastrarcontatos.controller;

import com.cadastro.contatos.cadastrarcontatos.model.Contato;
import com.cadastro.contatos.cadastrarcontatos.model.Profissional;
import com.cadastro.contatos.cadastrarcontatos.service.ContatoService;
import com.cadastro.contatos.cadastrarcontatos.service.ProfissionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class ContatoController {
    @Autowired
    private final ContatoService service;
    public ContatoController(ContatoService service) {
        this.service = service;
    }

    @GetMapping("contatos")
    public List<Contato> contatos() {
        return service.contatos();
    }

    @GetMapping("contato")
    public ResponseEntity<Object> filtrarContatos(@RequestParam("q") String q, @RequestParam("fields") Optional<List<String>> fields) {
        return service.filtros(q, fields);
    }

    @GetMapping("contato/{id}")
    public Optional<Contato> contato(@PathVariable String id) {
        return service.contato(UUID.fromString(id));
    }

    @PostMapping("contatos/{idProfissional}")
    public ResponseEntity<Object> cadastrarContato(@RequestBody Contato contato, @PathVariable String idProfissional) {
        return service.cadastrar(contato, UUID.fromString(idProfissional));
    }

    @PutMapping("contato/{id}")
    public ResponseEntity<Object> atualizarContato(@PathVariable String id, @RequestBody  Contato contato) {
        return service.atualizar(UUID.fromString(id), contato);
    }

    @DeleteMapping("contato/{id}")
    public ResponseEntity<Object> deletarContato(@PathVariable String id) {
        return service.deletar(UUID.fromString(id));
    }


}
