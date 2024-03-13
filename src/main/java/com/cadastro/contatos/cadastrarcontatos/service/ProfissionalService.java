package com.cadastro.contatos.cadastrarcontatos.service;

import com.cadastro.contatos.cadastrarcontatos.model.Profissional;
import com.cadastro.contatos.cadastrarcontatos.repository.ProfissionalRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class ProfissionalService {

    private final ProfissionalRepository profissionalRepository;
    Logger logger = LoggerFactory.getLogger(this.getClass());

    public ProfissionalService(ProfissionalRepository profissionalRepository) {
        this.profissionalRepository = profissionalRepository;
    }

    public List<Profissional> profissionais() {
        List<Profissional> status = profissionalRepository.findAll();
        if (status.isEmpty()) {
            logger.info("Lista vazia");
            return (List<Profissional>) ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lista vazia");
        }
        return status;
    }

    public Optional<Profissional> profissional(UUID id) {
        return profissionalRepository.findById(id);
    }

    public ResponseEntity<Object> deletar(UUID id) {
        profissionalRepository.deleteById(id);
        logger.info("Sucesso profissional ID: " + id + " deletado");
        return ResponseEntity.status(HttpStatus.OK).body("Sucesso profissional ID: " + id + " deletado");
    }

    public ResponseEntity<Object> atualizar(UUID id, Profissional dados) {
        Optional<Profissional> profissionalOptional = profissionalRepository.findById(id);
        if (!profissionalOptional.isPresent()) {
            logger.info("Profissional ID: " + id + " não encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Profissional ID: " + id + " não encontrado");
        }

        Profissional profissional = profissionalOptional.get();

        if (dados.getNome() != null) {
            profissional.setNome(dados.getNome());
        }
        if (dados.getCargo() != null) {
            profissional.setCargo(dados.getCargo());
        }
        if (dados.getNascimento() != null) {
            profissional.setNascimento(dados.getNascimento());
        }

        profissionalRepository.save(profissional);
        logger.info("Profissional ID: " + id + "atualizado");
        return ResponseEntity.status(HttpStatus.OK).body("Profissional ID: " + id + " atualizado");
    }

    public ResponseEntity<Object> cadastrar(Profissional profissional) {

        if (Objects.isNull(profissional.getCargo())  || !isValidCargo(profissional.getCargo())) {
            logger.info("Cargo inválido. Os valores aceitos são: desenvolvedor, designer, suporte, tester");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cargo inválido. Os valores aceitos são: desenvolvedor, designer, suporte, tester");
        }

        if (Objects.isNull(profissional.getNome()) || profissional.getNome().isEmpty() ) {
            logger.info("Nome está vazio");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Nome está vazio");
        }

        if (Objects.isNull(profissional.getNascimento()) || profissional.getNascimento().isEmpty()) {
            logger.info("Nascimento está vazio");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("nascimento está vazio");
        }

        profissional.setCreated_date(LocalDateTime.now());
        profissional.setCargo(profissional.getCargo().toLowerCase());

        profissionalRepository.save(profissional);
        logger.info("Sucesso profissional ID: " + profissional.getId() + " cadastrado");
        return ResponseEntity.status(HttpStatus.OK).body("Sucesso profissional ID: " + profissional.getId() + " cadastrado");
    }

    public ResponseEntity<Object> filtros(String q, Optional<List<String>> fields) {

        if (q == null || q.isEmpty()) {
            return ResponseEntity.badRequest().body("q vazio");
        }

        List<Profissional> profissionais = profissionais();

        List<Profissional> resultados = new ArrayList<>();
        for (Profissional profissional : profissionais) {
            if (profissionalMatchesQuery(profissional, q)) {
                resultados.add(profissional);
            }
        }

        if (resultados.isEmpty()) {
            return ResponseEntity.ok("Nada encontrado");
        }

        if (fields.isPresent() && !fields.get().isEmpty()) {
            resultados = filterFields(resultados, fields.get());
        }

        return ResponseEntity.ok(resultados);
    }

    private boolean profissionalMatchesQuery(Profissional profissional, String q) {
        return Objects.equals(profissional.getNome(), q) ||
                Objects.equals(profissional.getCargo(), q) ||
                Objects.equals(profissional.getNascimento(), q);
    }

    private List<Profissional> filterFields(List<Profissional> profissionais, List<String> campos) {
        List<Profissional> resultados = new ArrayList<>();
        for (Profissional profissional : profissionais) {
            Profissional profissionalFiltrado = new Profissional();
            boolean hasNonNullField = false;
            for (String campo : campos) {
                switch (campo) {
                    case "id":
                        if (profissional.getId() != null) {
                            profissionalFiltrado.setId(profissional.getId());
                            hasNonNullField = true;
                        }
                        break;
                    case "nome":
                        if (profissional.getNome() != null) {
                            profissionalFiltrado.setNome(profissional.getNome());
                            hasNonNullField = true;
                        }
                        break;
                    case "cargo":
                        if (profissional.getCargo() != null) {
                            profissionalFiltrado.setCargo(profissional.getCargo());
                            hasNonNullField = true;
                        }
                        break;
                    case "nascimento":
                        if (profissional.getNascimento() != null) {
                            profissionalFiltrado.setNascimento(profissional.getNascimento());
                            hasNonNullField = true;
                        }
                        break;
                }
            }
            if (hasNonNullField) {
                resultados.add(profissionalFiltrado);
            }
        }
        return resultados;
    }

    private boolean isValidCargo(String cargo) {
        String cargoUpCase = cargo.toLowerCase();
        return Objects.equals(cargoUpCase, "desenvolvedor") || Objects.equals(cargoUpCase, "designer") || Objects.equals(cargoUpCase, "suporte") || Objects.equals(cargoUpCase, "tester");
    }

}