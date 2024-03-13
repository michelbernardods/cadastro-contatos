package com.cadastro.contatos.cadastrarcontatos.service;

import static org.junit.jupiter.api.Assertions.*;

import com.cadastro.contatos.cadastrarcontatos.model.Profissional;
import com.cadastro.contatos.cadastrarcontatos.repository.ProfissionalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.*;
import static org.mockito.Mockito.*;

public class ProfissionalServiceTest {
    private ProfissionalRepository repository;
    private ProfissionalService service;

    @BeforeEach
    public void setUp() {
        repository = mock(ProfissionalRepository.class);
        service = new ProfissionalService(repository);
    }

    @Test
    public void testBuscarTodosProfissional() {
        List<Profissional> profissionais = Arrays.asList(new Profissional(), new Profissional());
        when(repository.findAll()).thenReturn(profissionais);

        List<Profissional> result = service.profissionais();
        assertEquals(2, result.size());
    }

    @Test
    public void testBuscarProfissional() {
        UUID id = UUID.randomUUID();
        Profissional profissional = new Profissional();
        when(repository.findById(id)).thenReturn(Optional.of(profissional));

        Optional<Profissional> result = service.profissional(id);
        assertEquals(profissional, result.get());
    }

    @Test
    public void testDeletarProfissional() {
        UUID id = UUID.randomUUID();

        ResponseEntity<Object> result = service.deletar(id);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("Sucesso profissional ID: " + id + " deletado", result.getBody());
        verify(repository, times(1)).deleteById(id);
    }

    @Test
    public void testAtualizarProfissional() {
        UUID id = UUID.randomUUID();
        Profissional dados = new Profissional();
        dados.setNome("Novo Nome");

        Profissional profissional = new Profissional();
        profissional.setId(id);
        profissional.setNome("Nome Antigo");
        when(repository.findById(id)).thenReturn(Optional.of(profissional));

        ResponseEntity<Object> result = service.atualizar(id, dados);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("Profissional ID: " + id + " atualizado", result.getBody());
        assertEquals("Novo Nome", profissional.getNome());
    }

    @Test
    public void testCadastrarProfissional() {
        Profissional profissional = new Profissional();
        profissional.setNome("Nome");
        profissional.setCargo("desenvolvedor");
        profissional.setNascimento("01/01/1990");

        ResponseEntity<Object> result = service.cadastrar(profissional);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("Sucesso profissional ID: " + profissional.getId() + " cadastrado", result.getBody());
        assertEquals(LocalDateTime.now().getDayOfMonth(), profissional.getCreated_date().getDayOfMonth());
        verify(repository, times(1)).save(profissional);
    }

}
