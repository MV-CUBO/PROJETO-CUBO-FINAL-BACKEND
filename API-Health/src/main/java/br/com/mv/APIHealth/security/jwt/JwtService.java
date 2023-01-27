package br.com.mv.APIHealth.security.jwt;

import br.com.mv.APIHealth.domain.entity.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;

@Service
public class JwtService {
    @Value("${security.jwt.expiracao}")
    private String expiracao;
    @Value("${security.jwt.chave-assinatura}")
    private String chaveAssinatura;

    public String gerarToken(Usuario usuario){
        long expString = Long.valueOf(expiracao);
        LocalDateTime dataHoraExpiracao = LocalDateTime.now().plusMinutes(expString);
        Instant instant = dataHoraExpiracao.atZone(ZoneId.systemDefault()).toInstant();
        Date data = Date.from(instant);

        HashMap<String, Object> claims = new HashMap<>();
        claims.put("roles", "NO JWTSERVICE MUDAR AQUI" );

        return Jwts
                .builder()
                .setSubject(usuario.getLogin())
                .setExpiration(data)
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, chaveAssinatura)
                .compact();
    }

    private Claims obterClaims( String token) throws ExpiredJwtException{
        return Jwts
                .parser()
                .setSigningKey(chaveAssinatura)
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean tokenValido(String token){
        try{

            Claims claims = obterClaims(token);
            Date dataExpiration = claims.getExpiration();
            LocalDateTime data =
                    dataExpiration.toInstant()
                            .atZone(ZoneId.systemDefault()).toLocalDateTime();
            return !LocalDateTime.now().isAfter(data);
        }catch (ExpiredJwtException c){
            return false;
        }
    }

    public String obterLoginUsuario(String token) throws ExpiredJwtException{
        return (String) obterClaims(token).getSubject();
    }
}
