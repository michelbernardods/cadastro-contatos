package com.cadastro.contatos.cadastrarcontatos.repository;

import com.cadastro.contatos.cadastrarcontatos.model.Profissional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface ProfissionalRepository extends JpaRepository<Profissional, UUID> {

   List<Profissional> findByNome(String nome);

   List<Profissional> findByCargo(String cargo);

   List<Profissional> findByNascimento(String nascimento);
}
