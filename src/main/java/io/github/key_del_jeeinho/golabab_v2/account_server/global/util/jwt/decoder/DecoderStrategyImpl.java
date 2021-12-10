package io.github.key_del_jeeinho.golabab_v2.account_server.global.util.jwt.decoder;

import io.github.key_del_jeeinho.golabab_v2.account_server.global.property.JwtProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component //static 변수들을 spring boot property 로 초기화 하기 위해 사용한다.
public class DecoderStrategyImpl implements DecoderStrategy {
    private static String secret;

    @Autowired
    private JwtProperty jwtProperty;

    @PostConstruct //jwt property 를 통해 secret, issuer 를 초기화한다.
    private void init() {
        secret = jwtProperty.getSecret();
    }

    @Override
    public JwtDecoder getInstance(String token) {
        return new JwtDecoder(token, secret);
    }
}
