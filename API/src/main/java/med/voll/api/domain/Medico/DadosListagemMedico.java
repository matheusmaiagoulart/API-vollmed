package med.voll.api.domain.Medico;

import java.util.UUID;

public record DadosListagemMedico(long id, String nome, String crm, String email, Especialidade especialidade) {

   public DadosListagemMedico(Medico medico){
       this(medico.getId(),medico.getNome(), medico.getCrm(), medico.getEmail(), medico.getEspecialidade());
   }
}
