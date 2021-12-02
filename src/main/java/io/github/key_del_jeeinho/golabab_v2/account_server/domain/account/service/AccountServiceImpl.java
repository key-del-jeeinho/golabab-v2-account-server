package io.github.key_del_jeeinho.golabab_v2.account_server.domain.account.service;

import io.github.key_del_jeeinho.golabab_v2.account_server.domain.account.entity.AccountEntity;
import io.github.key_del_jeeinho.golabab_v2.account_server.domain.account.exception.DuplicateAccountException;
import io.github.key_del_jeeinho.golabab_v2.account_server.domain.account.exception.DuplicateAccountException.Reason;
import io.github.key_del_jeeinho.golabab_v2.account_server.domain.account.exception.UnknownAccountException;
import io.github.key_del_jeeinho.golabab_v2.account_server.domain.account.repository.AccountRepository;
import io.github.key_del_jeeinho.golabab_v2.rosetta.account.AccountDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService{
    private final AccountRepository accountRepository;

    @Override //DB 에 계정을 추가한다.
    public AccountDto addAccount(AccountDto account) {
        //디스코드 아이디 또는 이메일이 겹치면 예외를 던진다.
        if(accountRepository.existsByDiscordId(account.discordId())) throw new DuplicateAccountException(Reason.DUPLICATE_DISCORD_ID);
        if(accountRepository.existsByEmail(account.email())) throw new DuplicateAccountException(Reason.DUPLICATE_EMAIL);

        //인자로 받은 AccountDto 를 AccountEntity 로 Convert 한다.
        AccountEntity data = AccountEntity.of(account);
        //Convert 한 AccountEntity(data) 를 DB에 저장한다.
        // 이후, 저장한 Entity 를 response 에 담는다.
        AccountEntity response = accountRepository.save(data);
        //response 를 AccountDto 로 Convert 하여 반환한다.
        return response.toDto();
    }

    @Override //DB에서 아이디를 통해 계정을 조회한다.
    public AccountDto getAccount(Long id) {
        //만약, 해당 id 를 가지고 있는 계정이 없다면 예외를 던진다.
        if(!accountRepository.existsById(id)) throw new UnknownAccountException("계정을 찾을 수 없습니다!", id);

        //계정을 조회하여, response 에 담는다.
        AccountEntity response = accountRepository.getById(id);
        //response 를 AccountDto 로 Convert 하여 반환한다.
        return response.toDto();
    }
}
