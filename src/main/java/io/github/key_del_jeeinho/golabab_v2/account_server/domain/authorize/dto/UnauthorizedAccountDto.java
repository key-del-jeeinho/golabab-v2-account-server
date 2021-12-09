package io.github.key_del_jeeinho.golabab_v2.account_server.domain.authorize.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 인증과정을 거치고 있는 사용자 (인증 요청자) 들의 계정 정보를 담고있는 DTO 입니다.
 * 회원 인증 및 가입시 주로 사용됩니다.
 *
 * @author 지인호
 *
 * @param expectedEmail 인증 요청자가 회원가입 및 인증 절차에서 입력한 이메일 입니다.
 * @param discordId 인증 요청자의 디스코드 아이디 입니다.
 */
public record UnauthorizedAccountDto (
        @JsonProperty("expectedEmail") String expectedEmail,
        @JsonProperty("discordId") long discordId
) {}
