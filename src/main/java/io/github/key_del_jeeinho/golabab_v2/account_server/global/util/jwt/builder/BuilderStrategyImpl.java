package io.github.key_del_jeeinho.golabab_v2.account_server.global.util.jwt.builder;

import io.github.key_del_jeeinho.golabab_v2.account_server.global.property.JwtProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.function.Function;

@Component //static 변수들을 spring boot property 로 초기화 하기 위해 사용한다.
public class BuilderStrategyImpl implements BuilderStrategy {
    private static String secret;
    private static String issuer;

    @Autowired
    private JwtProperty jwtProperty;

    @PostConstruct //jwt property 를 통해 secret, issuer 를 초기화한다.
    private void init() {
        secret = jwtProperty.getSecret();
        issuer = jwtProperty.getIssuer();
    }

    @Override
    public JwtBuilder getInstance(Function<Date, Date> expiration) {
        return new JwtBuilder(expiration, secret, issuer);
    }
}