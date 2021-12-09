package io.github.key_del_jeeinho.golabab_v2.account_server.global.util;

import io.github.key_del_jeeinho.golabab_v2.account_server.global.util.jwt.decoder.DecoderStrategy;
import io.github.key_del_jeeinho.golabab_v2.account_server.global.util.jwt.decoder.DecoderStrategyImpl;
import io.github.key_del_jeeinho.golabab_v2.account_server.global.util.jwt.decoder.JwtDecoder;
import io.github.key_del_jeeinho.golabab_v2.account_server.global.util.jwt.builder.BuilderStrategy;
import io.github.key_del_jeeinho.golabab_v2.account_server.global.util.jwt.builder.BuilderStrategyImpl;
import io.github.key_del_jeeinho.golabab_v2.account_server.global.util.jwt.builder.JwtBuilder;
import lombok.Setter;

import java.util.Date;
import java.util.function.Function;

public class JwtUtil {
    @Setter
    private static BuilderStrategy builderStrategy = new BuilderStrategyImpl(); //default strategy
    @Setter
    private static DecoderStrategy decoderStrategy = new DecoderStrategyImpl(); //default strategy

    public static JwtBuilder getInstance(Function<Date, Date> expiration) {
        return builderStrategy.getInstance(expiration);
    }

    public static JwtDecoder getDecoder(String token) {
        return decoderStrategy.getInstance(token);
    }
}
