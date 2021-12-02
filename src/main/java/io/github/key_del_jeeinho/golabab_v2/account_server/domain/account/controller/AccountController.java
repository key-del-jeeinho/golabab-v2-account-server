package io.github.key_del_jeeinho.golabab_v2.account_server.domain.account.controller;

import io.github.key_del_jeeinho.golabab_v2.account_server.domain.account.service.AccountService;
import io.github.key_del_jeeinho.golabab_v2.rosetta.account.AccountDto;
import io.github.key_del_jeeinho.golabab_v2.rosetta.account.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/account-api")
public class AccountController {
    private final AccountService accountService;

    //throws DuplicateAccountException
    @PutMapping("/account")
    public ResponseEntity<AccountDto> addAccount(@RequestBody AccountDto account) {
        //계정을 추가한다
        //계정을 추가한 후 추가한 계정의 정보를 담은 Dto 를 구한다
        AccountDto body = accountService.addAccount(account);
        //추가한 계정의 정보를 반환한다.
        return ResponseEntity.ok(body);
    }

    //throws UnknownAccountException
    @GetMapping("/account/{id}")
    public ResponseEntity<AccountDto> getAccount(@PathVariable Long id) {
        //인자로 받은 id를 통해 계정을 조회한다.
        AccountDto body = accountService.getAccount(id);
        //조회한 계정의 정보를 반환한다.
        return ResponseEntity.ok(body);
    }

    @PatchMapping("/account/{id}")
    public ResponseEntity<AccountDto> editAccount(@PathVariable Long id, @RequestBody AccountDto account) {
        //인자로 받은 id와 AccountDto 를 통해 계정을 수정한다.
        AccountDto body = accountService.editAccount(id, account);
        //수정한 계정의 정보를 반환한다.
        return ResponseEntity.ok(body);
    }
}
