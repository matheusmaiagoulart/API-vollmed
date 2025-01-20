package med.voll.api.Controller;


import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import med.voll.api.domain.Medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@SecurityRequirement(name = "bearer-key")
@RequestMapping("/medicos")
public class MedicoController {

    @Autowired
    private MedicoRepository repository;

    @Autowired
    private AutenticacaoController autenticacao;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid CadastroMedicos dados, UriComponentsBuilder uriBuilder){//Recebe os dados do JSON
        var medico = new Medico(dados);
//        if ("teste".equals(dados.nome())) {
//            throw new IllegalArgumentException("Nome inválido!");
//        }
        repository.save(medico);//Instancia esses dados criando um objeto new Medico, construt. que tratou os dados
        var uri = uriBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosListagemMedico(medico));
    }

    @GetMapping
    @Transactional(readOnly = true) //garante que a leitura nao fara nenhuma modificacao no DB, sendo mais segura do que só @Transactional
    public ResponseEntity<Page<DadosListagemMedico>> listar(@PageableDefault(sort = {"nome"}, size = 11) Pageable paginacao){//vai trazer ordenado e 10 registros por paginas
        var page =  repository.findAllByAtivoTrue(paginacao).map(DadosListagemMedico::new);
        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity update(@RequestBody @Valid DadosUpdateMedicos dados){
        var medico = repository.getReferenceById(dados.id()); //getreference é apenas para achar a referenciado objeto na memoria, se realemnte existe, ja o getById pega o objeto completo, carrega todos seus dados
        medico.atualizarInformacoes(dados);
        return ResponseEntity.ok(new ReturnDadosMedicoDTO(medico));

    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity delete(@PathVariable Long id){
        var medico = repository.getReferenceById(id); //pega o objeto
        medico.excluir();
        //repository.deleteById(id); codigo para deletar do banco de dados
        return ResponseEntity.ok().build();

    }

    @GetMapping("/{id}")
    @Transactional
    public ResponseEntity procurar(@PathVariable Long id){
        var medico = repository.getReferenceById(id); //pega o objeto
        return ResponseEntity.ok(new DadosListagemMedico(medico));

    }


}
