package com.cadastro.contatos.cadastrarcontatos.controller;

import com.cadastro.contatos.cadastrarcontatos.model.Profissional;
import com.cadastro.contatos.cadastrarcontatos.service.ProfissionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class ProfissionalController {

    @Autowired
    private final ProfissionalService service;

    public ProfissionalController(ProfissionalService service) {
        this.service = service;
    }

    @GetMapping("profissional")
    public List<Profissional> todosProfissional() {
        return service.profissionais();
    }

    @GetMapping("profissionais")
    public ResponseEntity<Object> filtrarProfissionais(@RequestParam("q") String q, @RequestParam("fields") Optional<List<String>> fields) {
        return service.filtros(q, fields);
    }

    @GetMapping("profissional/{id}")
    public Optional<Profissional> profissional(@PathVariable String id) {
        return service.profissional(UUID.fromString(id));
    }

    @PostMapping("profissionais")
    public ResponseEntity<Object> cadastrarProfissional(@RequestBody Profissional profissional)  {
        return service.cadastrar(profissional);
    }

    @PutMapping("profissional/{id}")
    public ResponseEntity<Object> atualizarProfissional(@PathVariable String id, @RequestBody  Profissional profissional) {
        return service.atualizar(UUID.fromString(id), profissional);
    }

    @DeleteMapping("profissional/{id}")
    public ResponseEntity<Object> deletarProfissional(@PathVariable String id) {
        return service.deletar(UUID.fromString(id));
    }

}
