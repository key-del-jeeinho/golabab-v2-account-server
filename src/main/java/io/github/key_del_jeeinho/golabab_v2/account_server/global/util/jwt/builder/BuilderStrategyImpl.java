package io.github.key_del_jeeinho.golabab_v2.account_server.global.util.jwt.builder;

import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
import java.util.function.Function;

public class BuilderStrategyImpl implements BuilderStrategy {
    @Value("${jwt.secret}")
    private static String secret = "secret";
    @Value("${jwt.issuer}")
    private static String issuer = "issuer";

    @Override
    public JwtBuilder getInstance(Function<Date, Date> expiration) {
        return new JwtBuilder(expiration, secret, issuer);
    }
}