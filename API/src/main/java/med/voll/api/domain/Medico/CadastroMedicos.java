package med.voll.api.domain.Medico;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import med.voll.api.domain.Endereco.DadosEndereco;

public record CadastroMedicos(
                              @NotBlank
                              String nome,

                              @NotBlank
                              @Email
                              String email,

                              @NotBlank
                              @Pattern(regexp = "\\d{4,6}")//Digitos entre 4 e 6 digitos
                              String crm,

                              @NotBlank
                              String telefone,

                              @NotNull
                              Especialidade especialidade,

                              @NotNull
                              @Valid
                              DadosEndereco endereco) {
}
