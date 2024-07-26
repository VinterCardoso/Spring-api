package cardoso.davi.springapi.controllers;

import cardoso.davi.springapi.dtos.CreatePessoaDTO;
import cardoso.davi.springapi.dtos.EditPessoaDTO;
import cardoso.davi.springapi.dtos.response.ErrorResponse;
import cardoso.davi.springapi.models.Pessoa;
import cardoso.davi.springapi.services.PessoaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/pessoa")
@Slf4j
public class PessoaController {
    private final PessoaService pessoaService;

    @Autowired
    public PessoaController(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Pessoa>> getAllPessoas() {
        List<Pessoa> response = this.pessoaService.getAll();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<Object> getAllPessoasByNome(@RequestParam(value = "nome") String nome) {
        try {
            List<Pessoa> response = this.pessoaService.getByNome(nome);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage()));
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<Object> getPessoaById(@PathVariable("id") Long id) {
        try {
            Pessoa response = this.pessoaService.getById(id);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage()));
        }
    }

    @PostMapping()
    public ResponseEntity<Object> save(@RequestBody CreatePessoaDTO createPessoaDTO) {
        try {
            Pessoa response = pessoaService.registerPessoa(createPessoaDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage()));
        }
    }

    @PatchMapping("{id}")
    public ResponseEntity<Object> edit(@PathVariable("id") Long id, @RequestBody EditPessoaDTO editPessoaDTO) {
        try {
            Pessoa response = pessoaService.editPessoa(id, editPessoaDTO);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage()));
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
        try {
            pessoaService.deletePessoa(id);
        return ResponseEntity.status(HttpStatus.OK).body("Deletado com sucesso");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage()));
        }
    }
}
