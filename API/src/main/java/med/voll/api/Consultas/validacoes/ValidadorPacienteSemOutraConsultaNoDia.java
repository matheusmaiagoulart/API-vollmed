package med.voll.api.Consultas.validacoes;

import med.voll.api.Consultas.ConsultaRepository;
import med.voll.api.Consultas.DadosAgendamentoConsulta;
import med.voll.api.domain.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class ValidadorPacienteSemOutraConsultaNoDia implements ValidadorAgendamentoDeConsulta{

    @Autowired
    private ConsultaRepository consultaRepository;

    public void validar(DadosAgendamentoConsulta dados) {

        var primeiroHorario = dados.data().withHour(7);
        var ultimoHorario = dados.data().withHour(18);

        var pacienteSemOutraConsulta = consultaRepository.existsByPacienteIdAndDataBetween(dados.idPaciente(),primeiroHorario, ultimoHorario);

        if(pacienteSemOutraConsulta){
            throw new ValidacaoException("O paciente já tem outra consulta marcada para a mesma data!");
        }
    }

}
