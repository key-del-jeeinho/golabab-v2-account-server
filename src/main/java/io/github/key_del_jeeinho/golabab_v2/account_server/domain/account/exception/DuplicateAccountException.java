package io.github.key_del_jeeinho.golabab_v2.account_server.domain.account.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class DuplicateAccountException extends RuntimeException {
    private final Reason reason;

    public DuplicateAccountException(Reason reason) {
        super(reason.getMessage());
        this.reason = reason;
    }

    @RequiredArgsConstructor
    public enum Reason {
        DUPLICATE_EMAIL("이미 존재하는 이메일 입니다."),
        DUPLICATE_DISCORD_ID("이미 존재하는 디스코드 ID 입니다.");

        @Getter
        private final String message;
    }
}
