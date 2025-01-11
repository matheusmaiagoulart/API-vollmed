package med.voll.api.Controller;

import jakarta.validation.Valid;
import med.voll.api.domain.Paciente.*;
import med.voll.api.domain.Paciente.DadosListagemPacientes;
import med.voll.api.domain.Paciente.Paciente;
import med.voll.api.domain.Paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    private PacienteRepository pacienteRepository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar (@RequestBody @Valid CadastroPaciente dados, UriComponentsBuilder uriBuilder){
        var paciente = new Paciente(dados);
        pacienteRepository.save(paciente);
        var uri = uriBuilder.path("/medicos/{id}").buildAndExpand(paciente.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosListagemPacientes(paciente));
    }

    @GetMapping
    @Transactional(readOnly = true)
    public ResponseEntity<Page<DadosListagemPacientes>> listar(@PageableDefault(sort = {"nome"}, size = 10) Pageable paginacao){
        var page = pacienteRepository.findAllByAtivoTrue(paginacao).map(DadosListagemPacientes::new);
        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity update(@RequestBody @Valid DadosUpdatePacienteDTO dadosUpdate){
        var paciente = pacienteRepository.getReferenceById(dadosUpdate.id());//pega o objeto
        paciente.atualizar(dadosUpdate);
        return ResponseEntity.ok(new ReturnDadosPacienteDTO(paciente));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity delete(@PathVariable Long id){
       var paciente = pacienteRepository.getReferenceById(id);
        paciente.excluir();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    @Transactional
    public ResponseEntity procurar(@PathVariable Long id){
        var paciente = pacienteRepository.getReferenceById(id);
        return ResponseEntity.ok(new DadosListagemPacientes(paciente));
    }
}
