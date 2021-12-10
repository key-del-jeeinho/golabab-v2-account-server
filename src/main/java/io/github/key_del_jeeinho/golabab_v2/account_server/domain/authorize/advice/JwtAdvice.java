package io.github.key_del_jeeinho.golabab_v2.account_server.domain.authorize.advice;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(basePackages = "io.github.key_del_jeeinho.golabab_v2.account_server.domain.authorize")
public class JwtAdvice {
    @ExceptionHandler(UnsupportedJwtException.class)
    public String handleUnsupportedJwtException() {
        return "error/unsupported-jwt";
    }

    @ExceptionHandler(MalformedJwtException.class)
    public String handleMalformedJwtException() {
        return "error/malformed-jwt";
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public String handleExpiredJwtException() {
        return "error/expired-jwt";
    }

    @ExceptionHandler(SignatureException.class)
    public String handleSignatureException() {
        return "error/wrong-signature";
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgumentException() {
        return "error/token-not-found";
    }
}
