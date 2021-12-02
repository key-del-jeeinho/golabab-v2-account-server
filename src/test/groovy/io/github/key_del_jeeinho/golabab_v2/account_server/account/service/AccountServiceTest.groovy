package io.github.key_del_jeeinho.golabab_v2.account_server.account.service

import io.github.key_del_jeeinho.golabab_v2.account_server.domain.account.entity.AccountEntity
import io.github.key_del_jeeinho.golabab_v2.account_server.domain.account.exception.DuplicateAccountException
import io.github.key_del_jeeinho.golabab_v2.account_server.domain.account.exception.DuplicateAccountException.Reason
import io.github.key_del_jeeinho.golabab_v2.account_server.domain.account.repository.AccountRepository
import io.github.key_del_jeeinho.golabab_v2.account_server.domain.account.service.AccountServiceImpl
import io.github.key_del_jeeinho.golabab_v2.rosetta.account.AccountDto
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification
import io.github.key_del_jeeinho.golabab_v2.rosetta.account.Role

@SpringBootTest
@ExtendWith(MockitoExtension)
class AccountServiceTest extends Specification {
    AccountServiceImpl accountService
    AccountRepository accountRepository

    /* AddAccount 테스트
    AccountService 의 addAccount 메서드는 인자로 받은 AccountDto 를 Database 에 저장하고, 저장한 값을 AccountDto 에 담아 반환한다.
    이때, 저장하는 값은 AccountDto 의 id, name, email, discordId, role 을 가지고 있는 table 이다.
    해당 로직이 정상작동한다면 메서드 호출 후 다음과 같은 변동사항이 발생해야한다.
    - DB 의 account 테이블에 하나의 row 가 추가된다.
    - 해당 row 는 메서드의 인자로 받은 데이터를 가지고 있으나, id 는 자동으로 생성되어 있어야 한다.
    - 메서드의 반환값은 인자로 받은 AccountDto 에서 id 를 DB에 저장한 row 의 id로 수정한 값 이어야 한다.
    - 이때, id 는 long 범위 내의 양수이다.
    - 또한, id, email, discordId 는 중복되지 않아야 한다. (ID 는 자동으로 생성되므로, Service 로직에서 검사하지 않는다.)
     */
    def "AccountService 의 addAccount 메서드에 대한 Positive Test"() {
        given:
        AccountDto 계정 = new AccountDto(계정id, 이메일, 역할, 디스코드id)

        accountRepository = Mock(AccountRepository)
        accountRepository.save(new AccountEntity(계정id, 이메일, 역할, 디스코드id))
                >> new AccountEntity(결과값_계정id, 이메일, 역할, 디스코드id)
        accountRepository.existsByDiscordId(디스코드id) >> false
        accountRepository.existsByEmail(이메일) >> false

        accountService = new AccountServiceImpl(accountRepository)

        when:
        AccountDto 결과값 = accountService.addAccount(계정)

        then:
        결과값.id() == 결과값_계정id
        결과값.email() == 계정.email()
        결과값.role() == 계정.role()
        결과값.discordId() == 계정.discordId()

        where:
        계정id | 결과값_계정id | 이메일 | 역할 | 디스코드id
        -1 | 173L | "s20072@gsm.hs.kr" | Role.DEVELOPER | 23490234L
        -1 | 308L | "golabab@gmail.com" | Role.ADMIN | 4231444L
        -1 | 252L | "@gsm.hs.kr" | Role.USER | 176390L
        -1 | 486L | "gsm.hs.kr" | Role.OPERATOR | 309542L
        -1 | 937L | "ab.c" | Role.DEVELOPER | 495072214L

    }
    def "AccountService 의 addAccount 메서드에 대한 Negative Test - 디스코드 ID가 중복되었을 경우"() {
        given:
        AccountDto 계정 = new AccountDto(계정id, 이메일, 역할, 디스코드id)

        accountRepository = Mock(AccountRepository)
        accountRepository.save(new AccountEntity(계정id, 이메일, 역할, 디스코드id))
                >> new AccountEntity(결과값_계정id, 이메일, 역할, 디스코드id)
        accountRepository.existsByDiscordId(디스코드id) >> true
        accountRepository.existsByEmail(이메일) >> false

        accountService = new AccountServiceImpl(accountRepository)

        when:
        accountService.addAccount(계정)
        then:
        def e = thrown(DuplicateAccountException.class)
        e.getReason() == Reason.DUPLICATE_DISCORD_ID
        where:
        계정id | 결과값_계정id | 이메일 | 역할 | 디스코드id
        -1 | 173L | "s20072@gsm.hs.kr" | Role.DEVELOPER | 23490234L
        -1 | 308L | "golabab@gmail.com" | Role.ADMIN | 4231444L
        -1 | 252L | "@gsm.hs.kr" | Role.USER | 176390L
        -1 | 486L | "gsm.hs.kr" | Role.OPERATOR | 309542L
        -1 | 937L | "ab.c" | Role.DEVELOPER | 495072214L

    }
    def "AccountService 의 addAccount 메서드에 대한 Negative Test - 이메일이 중복되었을 경우"() {
        given:
        AccountDto 계정 = new AccountDto(계정id, 이메일, 역할, 디스코드id)

        accountRepository = Mock(AccountRepository)
        accountRepository.save(new AccountEntity(계정id, 이메일, 역할, 디스코드id))
                >> new AccountEntity(결과값_계정id, 이메일, 역할, 디스코드id)
        accountRepository.existsByDiscordId(디스코드id) >> false
        accountRepository.existsByEmail(이메일) >> true

        accountService = new AccountServiceImpl(accountRepository)

        when:
        accountService.addAccount(계정)
        then:
        def e = thrown(DuplicateAccountException.class)
        e.getReason() == Reason.DUPLICATE_EMAIL
        where:
        계정id | 결과값_계정id | 이메일 | 역할 | 디스코드id
        -1 | 173L | "s20072@gsm.hs.kr" | Role.DEVELOPER | 23490234L
        -1 | 308L | "golabab@gmail.com" | Role.ADMIN | 4231444L
        -1 | 252L | "@gsm.hs.kr" | Role.USER | 176390L
        -1 | 486L | "gsm.hs.kr" | Role.OPERATOR | 309542L
        -1 | 937L | "ab.c" | Role.DEVELOPER | 495072214L

    }
}
