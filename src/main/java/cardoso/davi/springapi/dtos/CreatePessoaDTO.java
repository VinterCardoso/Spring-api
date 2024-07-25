package cardoso.davi.springapi.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreatePessoaDTO {
    private String nome;
    private String cpf;
    private String dataNascimento;
    private String email;
}
