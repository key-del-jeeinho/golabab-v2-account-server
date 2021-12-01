package io.github.key_del_jeeinho.golabab_v2.account_server.domain.account.controller;

import io.github.key_del_jeeinho.golabab_v2.rosetta.account.AccountDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/account-api")
public class AccountController {
    @PutMapping("/account")
    public ResponseEntity<AccountDto> addAccount(@RequestBody AccountDto account) {
        //계정을 추가한다
        //DoSomething
        long accountId = 1;//추가한 게정의 id 를 구한다

        //추가한 계정의 정보를 반환한다.
        AccountDto response = new AccountDto(accountId, account.email(), account.role(), account.discordId());
        return ResponseEntity.ok(response);
    }
}
