package io.github.key_del_jeeinho.golabab_v2.account_server.domain.authorize.service;

import io.github.key_del_jeeinho.golabab_v2.account_server.domain.authorize.dto.AuthorizeResultDto;
import io.github.key_del_jeeinho.golabab_v2.account_server.domain.authorize.dto.UnauthorizedAccountDto;
import io.github.key_del_jeeinho.golabab_v2.account_server.global.util.JwtUtil;
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
        String token = JwtUtil.getInstance(
                now -> new Date(now.getTime() + Duration.ofMinutes(limitMinute).toMillis()) //현재시각의 limitMinute 분 후
        )
                .addClaim("callbackUrl", callbackUrl)
                .addClaim("account", account)
        .build();
        return String.format("%s:%d/api/v1/authorize-api/authorize?token=%s", address, port, token);
    }

    @Override
    public AuthorizeResultDto authorize(String token) {
        return null;
    }
}
