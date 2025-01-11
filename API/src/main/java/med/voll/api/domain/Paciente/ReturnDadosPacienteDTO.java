package med.voll.api.domain.Paciente;


import med.voll.api.domain.Endereco.Endereco;

public record ReturnDadosPacienteDTO(Long id, String nome, String email, String telefone, Endereco endereco) {

    public ReturnDadosPacienteDTO(Paciente paciente){
        this(paciente.getId(), paciente.getNome(), paciente.getTelefone(), paciente.getEmail(), paciente.getEndereco());
    }
}
