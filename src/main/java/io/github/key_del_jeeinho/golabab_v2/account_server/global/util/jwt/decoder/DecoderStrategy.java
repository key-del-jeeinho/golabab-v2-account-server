package io.github.key_del_jeeinho.golabab_v2.account_server.global.util.jwt.decoder;

public interface DecoderStrategy {
    JwtDecoder getInstance(String token);
}
