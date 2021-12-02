package io.github.key_del_jeeinho.golabab_v2.account_server.domain.account.controller;

import io.github.key_del_jeeinho.golabab_v2.account_server.domain.account.service.AccountService;
import io.github.key_del_jeeinho.golabab_v2.rosetta.account.AccountDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/account-api")
public class AccountController {
    private final AccountService accountService;

    @PutMapping("/account")
    public ResponseEntity<AccountDto> addAccount(@RequestBody AccountDto account) {
        //계정을 추가한다
        AccountDto response = accountService.addAccount(account);//계정을 추가한 후 추가한 계정의 정보를 담은 Dto 를 구한다
        //추가한 계정의 정보를 반환한다.
        return ResponseEntity.ok(response);
    }
}
