package io.github.key_del_jeeinho.golabab_v2.account_server.global.util.jwt.decoder;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class JwtDecoder {
    private final String secret;
    private final Claims claims;

    public JwtDecoder(String token, String secret) {
        this.secret = secret;
        claims = parse(token);
    }

    private Claims parse(String token) {
        if(token == null) throw new IllegalArgumentException("token is null");

        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    public <T> T get(String key, Class<T> clazz) {
        return claims.get(key, clazz);
    }
}
