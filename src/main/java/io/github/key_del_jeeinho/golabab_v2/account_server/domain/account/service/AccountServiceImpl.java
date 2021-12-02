package io.github.key_del_jeeinho.golabab_v2.account_server.domain.account.service;

import io.github.key_del_jeeinho.golabab_v2.account_server.domain.account.entity.AccountEntity;
import io.github.key_del_jeeinho.golabab_v2.account_server.domain.account.exception.DuplicateAccountException;
import io.github.key_del_jeeinho.golabab_v2.account_server.domain.account.exception.DuplicateAccountException.Reason;
import io.github.key_del_jeeinho.golabab_v2.account_server.domain.account.repository.AccountRepository;
import io.github.key_del_jeeinho.golabab_v2.rosetta.account.AccountDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService{
    private final AccountRepository accountRepository;

    @Override
    public AccountDto addAccount(AccountDto account) {
        if(accountRepository.existsByDiscordId(account.discordId())) throw new DuplicateAccountException(Reason.DUPLICATE_DISCORD_ID);
        if(accountRepository.existsByEmail(account.email())) throw new DuplicateAccountException(Reason.DUPLICATE_EMAIL);

        AccountEntity accountEntity = AccountEntity.of(account);
        AccountEntity entity = accountRepository.save(accountEntity);

        return entity.toDto();
    }
}
