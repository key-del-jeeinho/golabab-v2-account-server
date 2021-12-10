package io.github.key_del_jeeinho.golabab_v2.account_server.domain.authorize.controller;

import io.github.key_del_jeeinho.golabab_v2.account_server.domain.authorize.dto.UnauthorizedAccountDto;
import io.github.key_del_jeeinho.golabab_v2.account_server.domain.authorize.service.AuthorizeService;
import io.github.key_del_jeeinho.golabab_v2.rosetta.authorize.request.GetAuthorizeLinkRequest;
import io.github.key_del_jeeinho.golabab_v2.rosetta.authorize.response.GetAuthorizeLinkResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/authorize-api")
public class AuthorizeLinkController {
    private final AuthorizeService authorizeService;

    @PostMapping("/authorize-link")
    public ResponseEntity<GetAuthorizeLinkResponse> getAuthorizeLink(@Valid  @RequestBody GetAuthorizeLinkRequest request) {
        UnauthorizedAccountDto account = new UnauthorizedAccountDto(request.email(), request.discordId());
        String link = authorizeService.generateAuthorizeLink(request.callbackUrl(), account, request.limitMinute());

        GetAuthorizeLinkResponse response = new GetAuthorizeLinkResponse(link);
        return ResponseEntity.ok(response);
    }
}
