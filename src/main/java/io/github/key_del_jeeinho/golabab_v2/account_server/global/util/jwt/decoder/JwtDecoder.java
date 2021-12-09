package io.github.key_del_jeeinho.golabab_v2.account_server.global.util.jwt.decoder;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class JwtDecoder {
    private final String secret;
    private final String issuer;
    private final Claims claims;

    public JwtDecoder(String token, String secret, String issuer) {
        this.secret = secret;
        this.issuer = issuer;
        claims = parse(token);
    }

    private Claims parse(String token) {
        if(token == null) throw new IllegalArgumentException("token is null");

        Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();

        if(!claims.getIssuer().equals(issuer)) throw new IllegalArgumentException("invalid issuer");

        return claims;
    }

    public <T> T get(String key, Class<T> clazz) {
        return claims.get(key, clazz);
    }
}
