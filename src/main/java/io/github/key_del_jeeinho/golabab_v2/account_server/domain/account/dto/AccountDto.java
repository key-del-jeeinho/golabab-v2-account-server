package io.github.key_del_jeeinho.golabab_v2.account_server.domain.account.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.key_del_jeeinho.golabab_v2.rosetta.account.Role;

/**
 * 해당서비스를 이용하는 사용자들의 계정 정보를 담고있는 DTO 입니다.
 * 주로 투표 참여 및 관리시 접근 권한을 확인하기위해 사용됩니다.
 *
 * @author 지인호
 *
 * @param id 계정의 ID입니다.
 * @param email 계정의 이메일 입니다.
 * @param role 계정의 직책입니다.
 * @param discordId 계정의 디스코드 아이디 입니다.
 */
public record AccountDto (
        @JsonProperty("id") long id,
        @JsonProperty("email") String email,
        @JsonProperty("role") Role role,
        @JsonProperty("discordId") long discordId
){}