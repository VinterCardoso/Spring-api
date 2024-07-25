package cardoso.davi.springapi.repositories;

import cardoso.davi.springapi.models.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

    Optional<Pessoa> findByEmail(String email);
    List<Pessoa> findByNomeContainingIgnoreCase(String nome);
}
