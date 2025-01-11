package med.voll.api.Consultas.validacoes;

import med.voll.api.Consultas.DadosAgendamentoConsulta;
import med.voll.api.domain.Paciente.PacienteRepository;
import med.voll.api.domain.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class ValidadorPacienteAtivo implements ValidadorAgendamentoDeConsulta{

    @Autowired
    private PacienteRepository repository;

    public void validar(DadosAgendamentoConsulta dados){
        var pacienteAtivo = repository.findAtivoById(dados.idPaciente());

        if (!pacienteAtivo){
            throw new ValidacaoException("COnsulta não pode ser agendada para um paciente inexistente!");
        }

    }
}
