package br.edu.corumba.api_alunos.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.edu.corumba.api_alunos.model.Aluno;
import br.edu.corumba.api_alunos.repository.AlunoRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/alunos")
public class AlunoController {

    private final AlunoRepository repository;

    public AlunoController(AlunoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Aluno> buscarTodos() {
        return repository.findAll();
    }

    @PostMapping
    public Aluno inserir(@Valid @RequestBody Aluno aluno) {
        return repository.save(aluno);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Aluno> atualizar(@PathVariable Long id, @Valid @RequestBody Aluno aluno) {
        return repository.findById(id)
            .map(existente -> {
                aluno.setId(id);
                return ResponseEntity.ok(repository.save(aluno));
            }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletar(@PathVariable Long id) {
        return repository.findById(id)
            .map(aluno -> {
                repository.delete(aluno);
                return ResponseEntity.noContent().build();
            }).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/buscar-por-nome")
    public List<Aluno> buscarPorNome(@RequestParam String nome) {
        return repository.findByNomeEstudanteContainingIgnoreCase(nome);
    }

    @GetMapping("/buscar-por-curso")
    public List<Aluno> buscarPorCurso(@RequestParam String curso) {
        return repository.findByCursoContainingIgnoreCase(curso);
    }

    @GetMapping("/buscar-por-campus")
    public List<Aluno> buscarPorCampus(@RequestParam String campus) {
        return repository.findByCampusContainingIgnoreCase(campus);
    }
}