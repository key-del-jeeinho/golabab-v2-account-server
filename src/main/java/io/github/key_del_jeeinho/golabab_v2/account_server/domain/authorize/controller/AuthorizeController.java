package io.github.key_del_jeeinho.golabab_v2.account_server.domain.authorize.controller;

import io.github.key_del_jeeinho.golabab_v2.account_server.domain.authorize.dto.UnauthorizedAccountDto;
import io.github.key_del_jeeinho.golabab_v2.account_server.domain.authorize.service.AuthorizeService;
import io.github.key_del_jeeinho.golabab_v2.rosetta.authorize.request.GetAuthorizeLinkRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/authorize-api")
public class AuthorizeController {
    private AuthorizeService authorizeService;

    @GetMapping("/authorize-link")
    public ResponseEntity<String> getAuthorizeLink(@RequestBody GetAuthorizeLinkRequest request) {
        UnauthorizedAccountDto account = new UnauthorizedAccountDto(request.email(), request.discordId());
        String link = authorizeService.generateAuthorizeLink(request.callbackUrl(), account, request.limitMinute());

        return ResponseEntity.ok(link);
    }
}
