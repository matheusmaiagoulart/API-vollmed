package med.voll.api.domain.Paciente;

import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.Endereco.Endereco;

public record DadosUpdatePacienteDTO(@NotNull Long id, String nome, String telefone, Endereco endereco){

}
