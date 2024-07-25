package cardoso.davi.springapi.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EditPessoaDTO {
    private String nome;
    private String dataNascimento;
    private String email;
}
