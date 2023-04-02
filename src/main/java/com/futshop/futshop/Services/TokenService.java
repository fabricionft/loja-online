package com.futshop.futshop.Services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.futshop.futshop.Model.UsuarioModel;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    public String gerarToken(UsuarioModel usuario){
        return JWT.create()
                .withIssuer("Produtos")
                .withSubject(usuario.getEmail())
                .withExpiresAt(LocalDateTime.now()
                        .plusHours(1)
                        //.plusSeconds(20)
                        .toInstant(ZoneOffset.of("-03:00"))
                ).sign(Algorithm.HMAC256("pretinha"));
    }

    public String getSubject(String token) {
        return JWT.require(Algorithm.HMAC256("pretinha"))
               .withIssuer("Produtos")
               .build().verify(token).getSubject();
    }
}
