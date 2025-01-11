package med.voll.api.domain.Medico;

import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.Endereco.Endereco;

public record DadosUpdateMedicos(@NotNull Long id, String nome,String email, String telefone, Endereco endereco) {


}
