package med.voll.api.Consultas;


import jakarta.validation.constraints.NotNull;

public record DadosCancelamentoConsulta (

        @NotNull
         Long idConsulta,

        @NotNull
        MotivoCancelamento motivo
){

}
