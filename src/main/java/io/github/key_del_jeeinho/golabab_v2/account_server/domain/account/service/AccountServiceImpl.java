package io.github.key_del_jeeinho.golabab_v2.account_server.domain.account.service;

import io.github.key_del_jeeinho.golabab_v2.account_server.domain.account.entity.AccountEntity;
import io.github.key_del_jeeinho.golabab_v2.account_server.domain.account.exception.DuplicateAccountException;
import io.github.key_del_jeeinho.golabab_v2.account_server.domain.account.exception.DuplicateAccountException.Reason;
import io.github.key_del_jeeinho.golabab_v2.account_server.domain.account.exception.UnknownAccountException;
import io.github.key_del_jeeinho.golabab_v2.account_server.domain.account.repository.AccountRepository;
import io.github.key_del_jeeinho.golabab_v2.account_server.domain.account.dto.AccountDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class AccountServiceImpl implements AccountService{
    private final AccountRepository accountRepository;

    @Transactional
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

    @Transactional(readOnly = true)
    @Override //DB에서 아이디를 통해 계정을 조회한다.
    public AccountDto getAccount(Long id) {
        //만약, 해당 id 를 가지고 있는 계정이 없다면 예외를 던진다.
        if(!accountRepository.existsById(id)) throw new UnknownAccountException("계정을 찾을 수 없습니다!", id);

        //계정을 조회하여, response 에 담는다.
        AccountEntity response = accountRepository.getById(id);
        //response 를 AccountDto 로 Convert 하여 반환한다.
        return response.toDto();
    }

    @Transactional
    @Override
    public AccountDto editAccount(Long id, AccountDto account) {
        //현재 계정을 제외한 기존 계정과 디스코드 아이디 또는 이메일이 겹치면 예외를 던진다.
        AccountDto currentAccount = getAccount(id); //계정을 찾지 못하면 UnknownAccountException 이 발생한다.
        if(account.discordId() != currentAccount.discordId() && //기존 계정 정보의 디스코드ID와 수정할 계정 정보의 디스코드ID가 다르고,
                accountRepository.existsByDiscordId(account.discordId())) throw new DuplicateAccountException(Reason.DUPLICATE_DISCORD_ID);
        if(!Objects.equals(account.email(), currentAccount.email()) && //기존 계정 정보의 이메일과 수정할 계정 정보의 이메일이 다르고,
                accountRepository.existsByEmail(account.email())) throw new DuplicateAccountException(Reason.DUPLICATE_EMAIL);

        //인자로 받은 AccountDto 를 AccountEntity 로 Convert 한다.
        AccountEntity data = AccountEntity.of(account);
        //Convert 한 AccountEntity(data) 를 DB에 저장한다.
        // 기존에 존재하는 id(primary key) 를 이용하여, 저장하였기에, 기존 data가 수정된다
        AccountEntity response = accountRepository.save(data);
        //response 를 AccountDto 로 Convert 하여 반환한다.
        return response.toDto();
    }
}
