package io.github.key_del_jeeinho.golabab_v2.account_server.domain.account.service;

import io.github.key_del_jeeinho.golabab_v2.account_server.domain.account.dto.AccountDto;

public interface AccountService {
    AccountDto addAccount(AccountDto account);

    AccountDto getAccount(Long id);

    AccountDto editAccount(Long id, AccountDto account);
}
