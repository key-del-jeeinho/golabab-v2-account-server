package io.github.key_del_jeeinho.golabab_v2.account_server.domain.authorize.service;

import io.github.key_del_jeeinho.golabab_v2.account_server.domain.authorize.dto.UnauthorizedAccountDto;

public interface AuthorizeService {
    String generateAuthorizeLink(String callbackUrl, UnauthorizedAccountDto account, int limitMinute);
}
