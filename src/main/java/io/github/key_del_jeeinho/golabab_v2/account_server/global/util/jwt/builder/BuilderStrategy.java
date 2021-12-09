package io.github.key_del_jeeinho.golabab_v2.account_server.global.util.jwt.builder;


import java.util.Date;
import java.util.function.Function;

public interface BuilderStrategy {
    JwtBuilder getInstance(Function<Date, Date> expiration);
}