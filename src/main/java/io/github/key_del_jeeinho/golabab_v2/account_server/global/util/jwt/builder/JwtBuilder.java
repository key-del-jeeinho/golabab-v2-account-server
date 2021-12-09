package io.github.key_del_jeeinho.golabab_v2.account_server.global.util.jwt.builder;

import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static io.github.key_del_jeeinho.golabab_v2.account_server.global.util.DateUtil.localToDate;
import static io.github.key_del_jeeinho.golabab_v2.account_server.global.util.DateUtil.now;

public class JwtBuilder {
    private final Date expiration;
    private final Date now;
    private Map<String, Object> claims;
    private final String secret;
    private final String issuer;

    public JwtBuilder(Function<Date, Date> expiration, String secret, String issuer) {
        this.now = localToDate(now());
        this.expiration = expiration.apply(now);
        this.claims = new HashMap<>();

        this.secret = secret;
        this.issuer = issuer;
    }

    public JwtBuilder addClaim(String key, Object value) {
        claims.put(key, value);
        return this;
    }

    public String build() {
        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuer(issuer)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + expiration.getTime()))
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }
}
