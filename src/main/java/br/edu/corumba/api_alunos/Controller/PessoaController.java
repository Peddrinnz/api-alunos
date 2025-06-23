package br.edu.corumba.api_alunos.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
}