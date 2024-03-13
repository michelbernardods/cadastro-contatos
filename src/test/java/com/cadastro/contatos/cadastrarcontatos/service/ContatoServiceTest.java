package com.cadastro.contatos.cadastrarcontatos.service;

import com.cadastro.contatos.cadastrarcontatos.model.Contato;
import com.cadastro.contatos.cadastrarcontatos.model.Profissional;
import com.cadastro.contatos.cadastrarcontatos.repository.ContatoRepository;
import com.cadastro.contatos.cadastrarcontatos.repository.ProfissionalRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContatoServiceTest {

    @Mock
    private ContatoRepository contatoRepository;

    @Mock
    private ProfissionalRepository profissionalRepository;

    @InjectMocks
    private ContatoService contatoService;

    @Test
    void cadastrar_WithEmptyName_ReturnsBadRequest() {
        Contato contato = new Contato();
        contato.setNome("");
        contato.setContato("123456789");

        ResponseEntity<Object> result = contatoService.cadastrar(contato, UUID.randomUUID());

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals("Nome está vazio", result.getBody());
        verify(contatoRepository, never()).save(any());
    }

    @Test
    void cadastrar_WithEmptyContact_ReturnsBadRequest() {
        Contato contato = new Contato();
        contato.setNome("Teste");
        contato.setContato("");

        ResponseEntity<Object> result = contatoService.cadastrar(contato, UUID.randomUUID());

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals("Contato está vazio", result.getBody());
        verify(contatoRepository, never()).save(any());
    }

    @Test
    void cadastrar_WithValidData_ReturnsOk() {
        Contato contato = new Contato();
        contato.setNome("Teste");
        contato.setContato("123456789");

        when(profissionalRepository.findById(any())).thenReturn(Optional.of(new Profissional()));

        ResponseEntity<Object> result = contatoService.cadastrar(contato, UUID.randomUUID());

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertTrue(result.getBody().toString().startsWith("Sucesso contato ID:"));
        verify(contatoRepository, times(1)).save(any());
    }

    @Test
    void cadastrar_WithNonExistentProfissional_ReturnsNotFound() {
        UUID idProfissional = UUID.randomUUID();
        Contato contato = new Contato();
        contato.setNome("Teste");
        contato.setContato("123456789");

        when(profissionalRepository.findById(idProfissional)).thenReturn(Optional.empty());

        ResponseEntity<Object> result = contatoService.cadastrar(contato, idProfissional);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertEquals("Profissional não encontrado", result.getBody());
        verify(contatoRepository, never()).save(any());
    }

}
