package io.github.key_del_jeeinho.golabab_v2.account_server.domain.authorize.service;

import io.github.key_del_jeeinho.golabab_v2.account_server.domain.authorize.dto.AuthorizeResultDto;
import io.github.key_del_jeeinho.golabab_v2.account_server.domain.authorize.dto.UnauthorizedAccountDto;
import io.github.key_del_jeeinho.golabab_v2.account_server.global.util.JwtUtil;
import io.github.key_del_jeeinho.golabab_v2.account_server.global.util.jwt.decoder.JwtDecoder;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthorizeServiceImpl implements AuthorizeService {
    private final ServerProperties serverProperties;

    @Override
    public String generateAuthorizeLink(String callbackUrl, UnauthorizedAccountDto account, int limitMinute) {
        String address = serverProperties.getAddress().toString();
        Integer port = serverProperties.getPort();
        String token = JwtUtil.getBuilder(
                now -> new Date(now.getTime() + Duration.ofMinutes(limitMinute).toMillis()) //현재시각의 limitMinute 분 후
        ).addClaim("callbackUrl", callbackUrl)
                .addClaim("account.discordId", account.discordId())
                .addClaim("account.email", account.expectedEmail())
        .build();
        return String.format("%s:%d/authorize?token=%s", address, port, token);
    }

    @Override
    public AuthorizeResultDto authorize(String authorizeToken) {
        //Decoding 로직
        JwtDecoder decoder = JwtUtil.getDecoder(authorizeToken);
        String callbackUrl = decoder.get("callbackUrl", String.class);
        UnauthorizedAccountDto account = new UnauthorizedAccountDto(
                decoder.get("account.email", String.class),
                decoder.get("account.discordId", Long.class)
        );
        //Encoding 로직
        String resultToken = JwtUtil.getBuilder(
                now -> new Date(now.getTime() + Duration.ofMinutes(1).toMillis()) //현재시각의 1분 후
        ).addClaim("discordId", account.discordId())
        .addClaim("email", account.expectedEmail())
        .build();
        //결과 반환
        return new AuthorizeResultDto(callbackUrl, resultToken);
    }
}
