package br.edu.corumba.api_alunos.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.corumba.api_alunos.model.AlunoModel;
import br.edu.corumba.api_alunos.repository.AlunoRepository;
import br.edu.corumba.api_alunos.repository.PessoaRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/students")
public class AlunoController {

    private final AlunoRepository studentRepository;
    private final PessoaRepository personRepository;

    public AlunoController(AlunoRepository studentRepository, PessoaRepository personRepository) {
        this.studentRepository = studentRepository;
        this.personRepository = personRepository;
    }

    @PostMapping
    public ResponseEntity<AlunoModel> createStudent(@RequestBody @Valid AlunoModel student) {
        if (student.getPessoa() != null && student.getPessoa().getIdPessoa() != null) {
            var existingPerson = personRepository.findById(student.getPessoa().getIdPessoa());
            if (existingPerson.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            student.setPessoa(existingPerson.get());
        }
        AlunoModel savedStudent = studentRepository.save(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedStudent);
    }

    @GetMapping
    public List<AlunoModel> getAllStudents() {
        return studentRepository.findAll();
    }
}