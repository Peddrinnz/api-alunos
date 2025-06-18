package br.edu.corumba.api_alunos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.corumba.api_alunos.model.Aluno;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {
    List<Aluno> findByNomeEstudanteContainingIgnoreCase(String nomeEstudante);
    List<Aluno> findByCursoContainingIgnoreCase(String curso);
    List<Aluno> findByCampusContainingIgnoreCase(String campus);
}
