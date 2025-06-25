package br.edu.corumba.api_alunos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.corumba.api_alunos.model.AlunoModel;

@Repository
public interface AlunoRepository extends JpaRepository<AlunoModel, Long> {
    List<AlunoModel> findByPessoa_NomeEstudanteContainingIgnoreCase(String nome);

    List<AlunoModel> findByCursoContainingIgnoreCase(String curso);

    List<AlunoModel> findByCampusContainingIgnoreCase(String campus);
}