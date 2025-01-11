package med.voll.api.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import med.voll.api.domain.Usuario.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Service
public class TokenService {

    @Value("{api.security.token.secret}") //define que ele vai ler essa linha la no application.properties
    private String secret;

    public String gerarToken(Usuario usuario) {
        try{

            var algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withIssuer("API Voll.med")//quem esta gerando esse token
                    .withSubject(usuario.getLogin())//identificar o usuario a qual o token pertence
                    .withExpiresAt(dataExpiracao())//tempo de expiracao do token
                    .sign(algorithm);
                return token;
        }catch (JWTCreationException exception){
    throw new RuntimeException("Erro ao gerar token JWT", exception);
        }

    }

    public String getSubject(String tokenJWT){ //verficar se o token está valido e retornar o usuário
        try {
            var algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("API Voll.med")
                    .build()
                    .verify(tokenJWT)
                    .getSubject();
        }catch(JWTVerificationException exception){
            throw new RuntimeException("Token JWT inválido ou expirado");
        }

    }

    private Instant dataExpiracao() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));//2 horas para expierar e colocando o -3 hrs de acordo com o fuso horario do brasil
    }
}


