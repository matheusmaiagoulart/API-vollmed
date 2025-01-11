package med.voll.api.Consultas.validacoes;

import med.voll.api.Consultas.DadosAgendamentoConsulta;
import med.voll.api.domain.ValidacaoException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;


@Component
public class ValidadorHorarioAntecedencia implements ValidadorAgendamentoDeConsulta{
    public void validar(DadosAgendamentoConsulta dados){

        var horaConsulta = dados.data();
        var agora = LocalDateTime.now();
        var diferencaEmMinutos = Duration.between(agora, horaConsulta).toMinutes();

        if(diferencaEmMinutos < 30){
        throw new ValidacaoException("A consulta precisa ser marcada com no mínimo 30 minutos de antecedência");
        }
    }
}
