package br.com.fiap.monitor_tree_api.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import br.com.fiap.monitor_tree_api.model.Token;
import br.com.fiap.monitor_tree_api.model.UserRole;
import br.com.fiap.monitor_tree_api.model.Usuario;


@Service
public class TokenService {

    private Instant experiesAt = LocalDateTime.now().plusDays(1).toInstant(ZoneOffset.ofHours(-3));
    private Algorithm algorithm = Algorithm.HMAC256("secret-muito-secreto-que-ninguem-pode-saber");

    public Token createToken(Usuario user){
        var jwt = JWT.create()
            .withSubject(user.getIdPessoa().toString())
            .withClaim("email", user.getEmail())
            .withClaim("role", user.getRole().toString())
            .withExpiresAt(experiesAt)
            .sign(algorithm);

        return new Token(jwt, "Bearer", user.getEmail());
    }

    public Usuario getUserFromToken(String jwt) {
        var jwtVerified = JWT.require(algorithm).build().verify(jwt);
        return Usuario.builder()
                .idPessoa(Long.valueOf(jwtVerified.getSubject()))
                .email(jwtVerified.getClaim("email").toString())
                .role(UserRole.USER)
                .build();
        
    }
    
}
