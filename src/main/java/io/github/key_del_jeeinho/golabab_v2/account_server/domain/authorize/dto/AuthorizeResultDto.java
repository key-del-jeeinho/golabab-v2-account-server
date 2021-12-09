package io.github.key_del_jeeinho.golabab_v2.account_server.domain.authorize.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * 완료된 인증요청에 대한 정보를 담고있는 DTO 입니다.
 * 인증결과를 콜백URL 에 전송할때 사용됩니다.
 *
 * @author 지인호
 *
 * @param callbackUrl 인증결과을 전송할 콜백URL입니다.
 * @param token 토큰화 된 인증결과입니다.
 */
public record AuthorizeResultDto (
        @JsonProperty("callbackUrl") String callbackUrl,
        @JsonProperty("token") String token
){}
