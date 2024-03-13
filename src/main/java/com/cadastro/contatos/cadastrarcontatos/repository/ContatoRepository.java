package com.cadastro.contatos.cadastrarcontatos.repository;

import com.cadastro.contatos.cadastrarcontatos.model.Contato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ContatoRepository extends JpaRepository<Contato, UUID> {
}
