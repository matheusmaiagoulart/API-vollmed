package med.voll.api.Controller;

import jakarta.validation.Valid;
import med.voll.api.Consultas.AgendaDeConsultas;
import med.voll.api.Consultas.DadosAgendamentoConsulta;
import med.voll.api.Consultas.DadosCancelamentoConsulta;
import med.voll.api.Consultas.DadosDetalhamentoConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/consultas")
public class ConsultasController {

    @Autowired
    private AgendaDeConsultas agenda; //objeto da service que contem as regras de negocio

    @PostMapping
    @Transactional
    public ResponseEntity agendarConsulta(@RequestBody @Valid DadosAgendamentoConsulta dados){
        agenda.agendarConsulta(dados); //metodo da nossa classe service que faz as validacoes das regras de negocio
        return ResponseEntity.ok(new DadosDetalhamentoConsulta( null,dados.idMedico(), dados.idPaciente(), dados.data()));
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity cancelarConsulta(@RequestBody @Valid DadosCancelamentoConsulta dados){
        agenda.cancelarConsulta(dados);
        return ResponseEntity.ok().build();
    }
}
