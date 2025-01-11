package med.voll.api.Consultas.validacoes;

import med.voll.api.Consultas.ConsultaRepository;
import med.voll.api.Consultas.DadosAgendamentoConsulta;
import med.voll.api.domain.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class ValidadorMedicoComOutraConsultaNoMesmoHorario implements ValidadorAgendamentoDeConsulta{

    @Autowired
    private ConsultaRepository consultaRepository;

    public void validar(DadosAgendamentoConsulta dados){

        var consultaMesmoHorario = consultaRepository.existsByMedicoIdAndData(dados.idMedico(), dados.data());

        if(!consultaMesmoHorario){
            throw new ValidacaoException("O médico já tem uma consulta neste mesmo horário!" +
                    "Por favor, escolha outro horário!");
        }

    }
}
