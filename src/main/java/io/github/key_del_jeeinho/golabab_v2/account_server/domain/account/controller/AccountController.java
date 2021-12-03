package io.github.key_del_jeeinho.golabab_v2.account_server.domain.account.controller;

import io.github.key_del_jeeinho.golabab_v2.account_server.domain.account.exception.DuplicateAccountException;
import io.github.key_del_jeeinho.golabab_v2.account_server.domain.account.exception.UnknownAccountException;
import io.github.key_del_jeeinho.golabab_v2.account_server.domain.account.service.AccountService;
import io.github.key_del_jeeinho.golabab_v2.rosetta.account.AccountDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/account-api")
public class AccountController {
    private final AccountService accountService;

    //throws DuplicateAccountException
    @PostMapping("/account")
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

    @ExceptionHandler(DuplicateAccountException.class)
    public ResponseEntity<String> handleDuplicateAccountException(DuplicateAccountException e) {
        //중복된 계정이 존재할 경우 에러를 반환한다.
        System.out.println(e.getMessage());
        return ResponseEntity.badRequest()
                .header(HttpHeaders.CONTENT_TYPE, "application/json;charset=UTF-8")
                .body(e.getMessage());
    }

    @ExceptionHandler(UnknownAccountException.class)
    public ResponseEntity<String> handleUnknownAccountException(UnknownAccountException e) {
        //존재하지 않는 계정을 조회할 경우 에러를 반환한다.
        return ResponseEntity.badRequest()
                .header(HttpHeaders.CONTENT_TYPE, "application/json;charset=UTF-8")
                .body(e.getMessage());
    }
}
