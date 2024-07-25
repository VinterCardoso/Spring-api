package cardoso.davi.springapi.services;

import cardoso.davi.springapi.dtos.CreatePessoaDTO;
import cardoso.davi.springapi.dtos.EditPessoaDTO;
import cardoso.davi.springapi.models.Pessoa;
import cardoso.davi.springapi.repositories.PessoaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLOutput;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class PessoaService {
    @Autowired
    private PessoaRepository pessoaRepository;

    private Boolean validateCPF(String cpf) {
        cpf = cpf.replaceAll("\\D", "");
        if (cpf.length() != 11 || cpf.matches("(\\d)\\1{10}")) return false;

        for (int j = 9; j < 11; j++) {
            int sum = 0;
            for (int i = 0; i < j; i++) sum += (cpf.charAt(i) - '0') * ((j + 1) - i);
            int verifier = (sum * 10) % 11 % 10;
            if (verifier != (cpf.charAt(j) - '0')) return false;
        }
        return true;
    }

    private LocalDate parseDate(String date) {
        try {
            return LocalDate.parse(date);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    public Pessoa registerPessoa(CreatePessoaDTO createPessoaDTO) throws Exception {
        if (!this.validateCPF(createPessoaDTO.getCpf())) {
            throw new Exception("CPF invalido");
        }

        Optional<Pessoa> pessoaEmailExists = pessoaRepository.findByEmail(createPessoaDTO.getEmail());
        if (pessoaEmailExists.isPresent()) throw new Exception("E-mail já cadastrado");

        LocalDate dataNascimentoParsed = this.parseDate(createPessoaDTO.getDataNascimento());

        if (dataNascimentoParsed == null) {
            throw new Exception("Data inválida, utilize o padrão yyyy-MM-dd");
        }

        Pessoa pessoa = new Pessoa().builder()
                .nome(createPessoaDTO.getNome())
                .cpf(createPessoaDTO.getCpf())
                .dataNascimento(dataNascimentoParsed)
                .email(createPessoaDTO.getEmail())
                .build();

            pessoaRepository.save(pessoa);
        return pessoa;
    }

    public List<Pessoa> getAll() {
        return this.pessoaRepository.findAll();
    }

    public Pessoa editPessoa(Long id, EditPessoaDTO editPessoaDTO) throws Exception {
        if (id == null) {
            throw new IllegalArgumentException("Id inválido");
        }

        Pessoa pessoa = this.pessoaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pessoa não encontrada"));

        if (editPessoaDTO.getNome() != null && !editPessoaDTO.getNome().isEmpty()) {
            pessoa.setNome(editPessoaDTO.getNome());
        }
        if (editPessoaDTO.getEmail() != null && !editPessoaDTO.getEmail().isEmpty()) {
            Optional<Pessoa> pessoaEmailExists = pessoaRepository.findByEmail(editPessoaDTO.getEmail());
            if (pessoaEmailExists.isPresent()) throw new Exception("E-mail já cadastrado");
            pessoa.setEmail(editPessoaDTO.getEmail());
        }
        if (editPessoaDTO.getDataNascimento() != null) {
            LocalDate dataNascimentoParsed = this.parseDate(editPessoaDTO.getDataNascimento());

            if (dataNascimentoParsed == null) {
                throw new Exception("Data inválida, utilize o padrão yyyy-MM-dd");
            }
            pessoa.setDataNascimento(dataNascimentoParsed);
        }

        return this.pessoaRepository.save(pessoa);
    }

    public void deletePessoa(Long id) throws Exception {
        Optional<Pessoa> pessoa = this.pessoaRepository.findById(id);
        if (!pessoa.isPresent()) {
            throw new Exception("Pessoa não encontrada");
        }
        this.pessoaRepository.delete(pessoa.get());
    }

    public List<Pessoa> getByNome(String nome) throws Exception {
        return this.pessoaRepository.findByNomeContainingIgnoreCase(nome);
    }
}
