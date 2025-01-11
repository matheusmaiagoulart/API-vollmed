package med.voll.api.domain.Medico;
import jakarta.persistence.*;
import lombok.*;
import med.voll.api.domain.Endereco.Endereco;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Table(name = "medicos")
@Entity(name = "Medico")
public class Medico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String email;
    private String telefone;
    private String crm;

    @Enumerated(EnumType.STRING)
    private Especialidade especialidade;

    private Boolean ativo;

    @Embedded
    private Endereco endereco;

    public Medico(CadastroMedicos dados) {
        this.ativo = true;
        this.nome = dados.nome();
        this.email = dados.email();
        this.telefone = dados.telefone();
        this.crm = dados.crm();
        this.especialidade = dados.especialidade();
        this.endereco = new Endereco(dados.endereco());
    }

        public void atualizarInformacoes (DadosUpdateMedicos dados){
            if (dados.nome() != null) {
                this.nome = dados.nome();
            }
            if (dados.email() != null) {
                this.email = dados.email();
            }
            if (dados.telefone() != null) {
                this.telefone = dados.telefone();
            }
            if (dados.endereco() != null) {
                this.endereco.atualizarInformacoesEndereco(dados.endereco());
            }
        }



    public void excluir () {
        this.ativo = false;
    }
}

//        public String teste (CadastroMedicos dados){
//            if ("teste".equals(dados.nome())) {
//                throw new IllegalArgumentException("Nome inválido!");
//            } else {
//                return "Cadastro realizado com sucesso!";
//            }
//        }
//    }



