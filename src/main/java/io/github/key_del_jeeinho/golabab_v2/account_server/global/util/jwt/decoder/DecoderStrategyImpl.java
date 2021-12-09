package io.github.key_del_jeeinho.golabab_v2.account_server.global.util.jwt.decoder;

import org.springframework.beans.factory.annotation.Value;

public class DecoderStrategyImpl implements DecoderStrategy {
    @Value("${jwt.secret}")
    private static String secret = "secret";
    @Value("${jwt.issuer}")
    private static String issuer = "issuer";

    @Override
    public JwtDecoder getInstance(String token) {
        return new JwtDecoder(token, secret, issuer);
    }
}
