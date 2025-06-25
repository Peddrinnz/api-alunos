package br.edu.corumba.api_alunos.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
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

    @GetMapping("/search/by-name")
    public List<AlunoModel> searchByName(@RequestParam String nome) {
        return studentRepository.findByPessoa_NomeEstudanteContainingIgnoreCase(nome);
    }

    @GetMapping("/search/by-curso")
    public List<AlunoModel> searchByCurso(@RequestParam String curso) {
        return studentRepository.findByCursoContainingIgnoreCase(curso);
    }

    @GetMapping("/search/by-campus")
    public List<AlunoModel> searchByCampus(@RequestParam String campus) {
        return studentRepository.findByCampusContainingIgnoreCase(campus);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlunoModel> updateStudent(@PathVariable Long id, @RequestBody @Valid AlunoModel student) {
        return (ResponseEntity<AlunoModel>) studentRepository.findById(id)
                .map(existingStudent -> {
                    existingStudent.setCampus(student.getCampus());
                    existingStudent.setPolo(student.getPolo());
                    existingStudent.setEmailInstitucional(student.getEmailInstitucional());
                    existingStudent.setCoordenacao(student.getCoordenacao());
                    existingStudent.setCurso(student.getCurso());
                    existingStudent.setSituacao(student.getSituacao());
                    existingStudent.setPeriodoEntrada(student.getPeriodoEntrada());

                    if (student.getPessoa() != null && student.getPessoa().getIdPessoa() != null) {
                        var existingPersonOpt = personRepository.findById(student.getPessoa().getIdPessoa());
                        if (existingPersonOpt.isEmpty()) {
                            return ResponseEntity.badRequest().build();
                        }
                        var existingPerson = existingPersonOpt.get();
                        existingPerson.setNomeEstudante(student.getPessoa().getNomeEstudante());
                        existingPerson.setIdade(student.getPessoa().getIdade());
                        existingPerson.setSexo(student.getPessoa().getSexo());
                        personRepository.save(existingPerson);
                        existingStudent.setPessoa(existingPerson);
                    }

                    AlunoModel updated = studentRepository.save(existingStudent);
                    return ResponseEntity.ok(updated);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteStudent(@PathVariable Long id) {
        return studentRepository.findById(id)
                .map(student -> {
                    studentRepository.delete(student);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}