package com.cadastro.contatos.cadastrarcontatos.service;

import com.cadastro.contatos.cadastrarcontatos.model.Contato;
import com.cadastro.contatos.cadastrarcontatos.model.Profissional;
import com.cadastro.contatos.cadastrarcontatos.repository.ContatoRepository;
import com.cadastro.contatos.cadastrarcontatos.repository.ProfissionalRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class ContatoService {

    @Autowired
    private final ContatoRepository repository;
    @Autowired
    private final ProfissionalRepository profissionalRepository;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    public ContatoService(ContatoRepository contatoRepository, ProfissionalRepository profissionalRepository) {
        this.repository = contatoRepository;
        this.profissionalRepository = profissionalRepository;
    }

    public List<Contato> contatos() {
        List<Contato> status = repository.findAll();
        if (status.isEmpty()) {
            logger.info("Lista vazia");
            return (List<Contato>) ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Lista vazia");
        }
        return status;
    }

    public Optional<Contato> contato(UUID id) {
        return repository.findById(id);
    }

    public ResponseEntity<Object> deletar(UUID id) {
        repository.deleteById(id);
        logger.info("Sucesso contato ID: " + id + " deletado");
        return ResponseEntity.status(HttpStatus.OK).body("Sucesso contato ID: " + id + " deletado");
    }

    public ResponseEntity<Object> atualizar(UUID id, Contato dados) {
        Optional<Contato> contatoOptional = repository.findById(id);
        if (!contatoOptional.isPresent()) {
            logger.info("Contato ID: " + id + " não encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Contato ID: " + id + " não encontrado");
        }

        Contato contato = contatoOptional.get();

        if (dados.getNome() != null) {
            contato.setNome(dados.getNome());
        }
        if (dados.getContato() != null) {
            contato.setContato(dados.getContato());
        }
        if (dados.getContato_descricao() != null) {
            contato.setContato_descricao(dados.getContato_descricao());
        }

        repository.save(contato);
        logger.info("Contato ID: " + id + " atualizado");
        return ResponseEntity.status(HttpStatus.OK).body("Contato ID: " + id + " atualizado");
    }

    public ResponseEntity<Object> cadastrar(Contato contato, UUID idProfissional) {
        if (Objects.isNull(contato.getNome()) || contato.getNome().isEmpty()) {
            logger.info("Nome está vazio");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Nome está vazio");
        }

        if (Objects.isNull(contato.getContato()) || contato.getContato().isEmpty()) {
            logger.info("Contato está vazio");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Contato está vazio");
        }

        Optional<Profissional> profissionalOptional = profissionalRepository.findById(idProfissional);
        if (!profissionalOptional.isPresent()) {
            logger.info("Profissional não encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Profissional não encontrado");
        }

        contato.setProfissional(profissionalOptional.get());
        contato.setCreated_date(LocalDateTime.now());

        repository.save(contato);
        logger.info("Sucesso contato ID: " + contato.getId() + " cadastrado");
        return ResponseEntity.status(HttpStatus.OK).body("Sucesso contato ID: " + contato.getId() + " cadastrado");
    }


    public ResponseEntity<Object> filtros(String q, Optional<List<String>> fields) {

        if (q == null || q.isEmpty()) {
            logger.info("q vazio");
            return ResponseEntity.badRequest().body("q vazio");
        }

        List<Contato> profissionais = contatos();

        List<Contato> resultados = new ArrayList<>();
        for (Contato contato : profissionais) {
            if (contatoMatchesQuery(contato, q)) {
                resultados.add(contato);
            }
        }

        if (resultados.isEmpty()) {
            logger.info("Nada encontrado");
            return ResponseEntity.ok("Nada encontrado");
        }

        if (fields.isPresent() && !fields.get().isEmpty()) {
            resultados = filterFields(resultados, fields.get());
        }

        return ResponseEntity.ok(resultados);
    }

    private boolean contatoMatchesQuery(Contato contato, String q) {
        return Objects.equals(contato.getNome(), q) ||
                Objects.equals(contato.getContato(), q) ||
                Objects.equals(contato.getContato_descricao(), q);
    }


    private List<Contato> filterFields(List<Contato> profissionais, List<String> campos) {
        List<Contato> resultados = new ArrayList<>();
        for (Contato contato : profissionais) {
            Contato contatoFiltrado = new Contato();
            boolean hasNonNullField = false;
            for (String campo : campos) {
                switch (campo) {
                    case "id":
                        if (contato.getId() != null) {
                            contatoFiltrado.setId(contato.getId());
                            hasNonNullField = true;
                        }
                        break;
                    case "nome":
                        if (contato.getNome() != null) {
                            contatoFiltrado.setNome(contato.getNome());
                            hasNonNullField = true;
                        }
                        break;
                    case "cargo":
                        if (contato.getContato() != null) {
                            contatoFiltrado.setContato(contato.getContato());
                            hasNonNullField = true;
                        }
                        break;
                    case "nascimento":
                        if (contato.getContato_descricao() != null) {
                            contatoFiltrado.setContato_descricao(contato.getContato_descricao());
                            hasNonNullField = true;
                        }
                        break;
                }
            }
            if (hasNonNullField) {
                resultados.add(contatoFiltrado);
            }
        }
        return resultados;
    }

}
