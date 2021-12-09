package io.github.key_del_jeeinho.golabab_v2.account_server.domain.authorize.controller;

import io.github.key_del_jeeinho.golabab_v2.account_server.domain.authorize.dto.AuthorizeResultDto;
import io.github.key_del_jeeinho.golabab_v2.account_server.domain.authorize.service.AuthorizeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
@RequestMapping("/authorize")
public class AuthorizeController {
    private final AuthorizeService authorizeService;

    @GetMapping
    public String authorize(@RequestParam String token) {
        AuthorizeResultDto result = authorizeService.authorize(token);

        return String.format("redirect:%s?token=%s", result.callbackUrl(), result.token());
    }
}
