package io.github.key_del_jeeinho.golabab_v2.account_server.domain.account.controller;

import io.github.key_del_jeeinho.golabab_v2.account_server.domain.account.exception.DuplicateAccountException;
import io.github.key_del_jeeinho.golabab_v2.account_server.domain.account.exception.UnknownAccountException;
import io.github.key_del_jeeinho.golabab_v2.account_server.domain.account.service.AccountService;
import io.github.key_del_jeeinho.golabab_v2.rosetta.account.request.AddAccountRequest;
import io.github.key_del_jeeinho.golabab_v2.rosetta.account.request.EditAccountRequest;
import io.github.key_del_jeeinho.golabab_v2.rosetta.account.response.AddAccountResponse;
import io.github.key_del_jeeinho.golabab_v2.rosetta.account.response.EditAccountResponse;
import io.github.key_del_jeeinho.golabab_v2.rosetta.account.response.GetAccountResponse;
import io.github.key_del_jeeinho.golabab_v2.account_server.domain.account.dto.AccountDto;
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
    public ResponseEntity<AddAccountResponse> addAccount(@RequestBody AddAccountRequest request) {
        //계정을 추가한다
        //계정을 추가한 후 추가한 계정의 정보를 담은 Dto 를 구한다
        AccountDto data = new AccountDto(-1, request.email(), request.role(), request.discordId());
        AccountDto result = accountService.addAccount(data);
        AddAccountResponse response = new AddAccountResponse(result.id(), result.email(), result.role(), result.discordId());
        //추가한 계정의 정보를 반환한다.
        return ResponseEntity.ok(response);
    }

    //throws UnknownAccountException
    @GetMapping("/account/{id}")
    public ResponseEntity<GetAccountResponse> getAccount(@PathVariable Long id) {
        //인자로 받은 id를 통해 계정을 조회한다.
        AccountDto result = accountService.getAccount(id);
        GetAccountResponse response = new GetAccountResponse(result.id(), result.email(), result.role(), result.discordId());
        //조회한 계정의 정보를 반환한다.
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/account/{id}")
    public ResponseEntity<EditAccountResponse> editAccount(@PathVariable Long id, @RequestBody EditAccountRequest account) {
        //인자로 받은 id와 AccountDto 를 통해 계정을 수정한다.
        AccountDto data = new AccountDto(id, account.email(), account.role(), account.discordId());
        AccountDto result = accountService.editAccount(id, data);
        EditAccountResponse response = new EditAccountResponse(result.id(), result.email(), result.role(), result.discordId());
        //수정한 계정의 정보를 반환한다.
        return ResponseEntity.ok(response);
    }

    @ExceptionHandler(DuplicateAccountException.class)
    public ResponseEntity<String> handleDuplicateAccountException(DuplicateAccountException e) {
        //중복된 계정이 존재할 경우 에러를 반환한다.
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
