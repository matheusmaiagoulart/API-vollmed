package med.voll.api.Consultas;


import med.voll.api.Consultas.validacoes.ValidadorAgendamentoDeConsulta;
import med.voll.api.domain.Medico.Medico;
import med.voll.api.domain.Medico.MedicoRepository;
import med.voll.api.domain.Paciente.PacienteRepository;
import med.voll.api.domain.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendaDeConsultas {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;


    private List<ValidadorAgendamentoDeConsulta> validadores; //Spring identifica todas as classes que utilizam essa interface e vai instancialas automatico

    public void agendarConsulta(DadosAgendamentoConsulta dados){

        if(!pacienteRepository.existsById(dados.idPaciente())){
            throw new ValidacaoException("Esse paciente não existe! Por favor, passe um iD válido!");
        }

        if(dados.idMedico() != null && !medicoRepository.existsById(dados.idMedico())){
            throw new ValidacaoException("Esse médico não existe! Por favor, passe um iD válido!");
        }
        validadores.forEach(v -> v.validar(dados)); //vai fazer a validacao passando por todas as classes que tem a interface

        var paciente = pacienteRepository.getReferenceById(dados.idPaciente());
        var medico = escolherMedico(dados);
        if(medico == null) {
            throw new ValidacaoException("Não existe médicos disponíveis nessa data");
        }
        var consulta = new Consulta(null, medico, paciente, dados.data(), null);
        System.out.println(consulta);

        consultaRepository.save(consulta);


    }

    private Medico escolherMedico(DadosAgendamentoConsulta dados) {

        if(dados.idMedico() != null){
            return medicoRepository.getReferenceById(dados.idMedico());
        }
        if(dados.especialidade() == null){
            throw new ValidacaoException("Especialidade é obrigatória quando o médico não for preenchido!");
        }

       return medicoRepository.escolherMedicoAleatorioLivreNaData(dados.especialidade(), dados.data());

    }


    public void cancelarConsulta(DadosCancelamentoConsulta dados) {
        if(!consultaRepository.existsById(dados.idConsulta())){ //se for igual a false
            throw new ValidacaoException("O iD da consulta não existe!");
        }

        var consulta = consultaRepository.getReferenceById(dados.idConsulta());
        consulta.cancelar(dados.motivo());

    }
}
