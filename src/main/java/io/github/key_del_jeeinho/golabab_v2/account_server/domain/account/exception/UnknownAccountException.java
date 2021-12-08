package io.github.key_del_jeeinho.golabab_v2.account_server.domain.account.exception;

import lombok.Getter;

@Getter
public class UnknownAccountException extends RuntimeException {
    private final Long id;
    public UnknownAccountException(Long id) {
        this("Unknown account", id);
    }

    public UnknownAccountException(String message, Long id) {
        super(message);
        this.id = id;
    }
}
