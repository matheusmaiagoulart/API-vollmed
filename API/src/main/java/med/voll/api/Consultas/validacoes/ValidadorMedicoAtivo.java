package med.voll.api.Consultas.validacoes;

import med.voll.api.Consultas.DadosAgendamentoConsulta;
import med.voll.api.domain.Medico.MedicoRepository;
import med.voll.api.domain.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorMedicoAtivo implements ValidadorAgendamentoDeConsulta {

    @Autowired
    private MedicoRepository medicoRepository;

    public void validar(DadosAgendamentoConsulta dados){

        if(dados.idMedico() == null){
            return;
        }

        var medicoAtivo = medicoRepository.findAtivoById(dados.idMedico());

        if(!medicoAtivo){
            throw new ValidacaoException("Digite o iD de um médico válido!");
        }
    }
}
