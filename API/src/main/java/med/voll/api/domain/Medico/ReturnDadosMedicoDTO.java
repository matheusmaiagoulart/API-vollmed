package med.voll.api.domain.Medico;

import med.voll.api.domain.Endereco.Endereco;

import java.util.UUID;

public record ReturnDadosMedicoDTO(long id, String nome, String email, String telefone, String crm, Especialidade especialidade, Endereco endereco) {

    public ReturnDadosMedicoDTO(Medico medico){
        this(medico.getId(), medico.getNome(), medico.getTelefone(), medico.getCrm(), medico.getEmail(), medico.getEspecialidade(), medico.getEndereco());
    }
}
