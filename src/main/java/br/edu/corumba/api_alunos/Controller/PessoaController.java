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
import org.springframework.web.bind.annotation.RestController;

import br.edu.corumba.api_alunos.model.PessoaModel;
import br.edu.corumba.api_alunos.repository.PessoaRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/persons")
public class PessoaController {

    private final PessoaRepository repository;

    public PessoaController(PessoaRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<PessoaModel> getAllPersons() {
        return repository.findAll();
    }

    @PostMapping
    public PessoaModel createPerson(@RequestBody @Valid PessoaModel newPerson) {
        return repository.save(newPerson);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PessoaModel> updatePerson(@PathVariable Long id, @RequestBody @Valid PessoaModel person) {
        return repository.findById(id)
                .map(existingPerson -> {
                    existingPerson.setNomeEstudante(person.getNomeEstudante());
                    existingPerson.setIdade(person.getIdade());
                    existingPerson.setSexo(person.getSexo());
                    PessoaModel updated = repository.save(existingPerson);
                    return ResponseEntity.ok(updated);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletePerson(@PathVariable Long id) {
        return repository.findById(id)
                .map(person -> {
                    repository.delete(person);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}