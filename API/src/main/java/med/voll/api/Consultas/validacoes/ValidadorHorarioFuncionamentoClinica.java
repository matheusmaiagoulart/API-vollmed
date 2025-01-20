package med.voll.api.Consultas.validacoes;

import med.voll.api.Consultas.DadosAgendamentoConsulta;
import med.voll.api.domain.ValidacaoException;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

@Component
public class ValidadorHorarioFuncionamentoClinica implements ValidadorAgendamentoDeConsulta {

    public void validar(DadosAgendamentoConsulta dados){
        var data = dados.data();
        var domingo = data.getDayOfWeek().equals(DayOfWeek.SUNDAY); //true se for domingo, false se nao for
        var antesAbertura = data.getHour() < 7;
        var fechamentoClinica = data.getHour() > 18;

        if(domingo|| antesAbertura || fechamentoClinica){
            throw new ValidacaoException("Consulta fora do horário de funcionamento!");
        }


    }
}
