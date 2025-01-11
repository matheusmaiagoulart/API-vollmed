package med.voll.api.domain.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AutenticacaoService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { //Método que vai usar quando o usuário fazer login
        return usuarioRepository.findByLogin(username); //vai procurar pelo usuario que está tentando acessar.
    }

    @Autowired
    private PasswordEncoder passwordEncoder;


    public void salvarUsuario(String login, String senha) {
        String senhaCriptografada = passwordEncoder.encode(senha);
        Usuario usuario = new Usuario(login, senha);
        usuarioRepository.save(usuario);
    }
}

