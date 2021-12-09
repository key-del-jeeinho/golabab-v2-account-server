package io.github.key_del_jeeinho.golabab_v2.account_server.global.util;

import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static io.github.key_del_jeeinho.golabab_v2.account_server.global.util.DateUtil.localToDate;
import static io.github.key_del_jeeinho.golabab_v2.account_server.global.util.DateUtil.now;

public class JwtUtil {
    @Setter
    private static BuilderStrategy builderStrategy = new BuilderStrategyImpl(); //default strategy
    public static JwtBuilder getInstance(Function<Date, Date> expiration) {
        return builderStrategy.getInstance(expiration);
    }

    public interface BuilderStrategy {
        JwtBuilder getInstance(Function<Date, Date> expiration);
    }

    public static class BuilderStrategyImpl implements BuilderStrategy {
        @Value("${jwt.secret}")
        private static String secret = "secret";
        @Value("${jwt.issuer}")
        private static String issuer = "issuer";

        @Override
        public JwtBuilder getInstance(Function<Date, Date> expiration) {
            return new JwtBuilder(expiration, secret, issuer);
        }
    }

    public static class JwtBuilder {
        private final Date expiration;
        private final Date now;
        private Map<String, Object> claims;
        private final String secret;
        private final String issuer;

        private JwtBuilder(Function<Date, Date> expiration, String secret, String issuer) {
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
}
