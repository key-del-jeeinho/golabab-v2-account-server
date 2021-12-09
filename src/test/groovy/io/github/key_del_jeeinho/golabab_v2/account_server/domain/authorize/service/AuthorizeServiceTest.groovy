package io.github.key_del_jeeinho.golabab_v2.account_server.domain.authorize.service

import io.github.key_del_jeeinho.golabab_v2.account_server.domain.authorize.dto.AuthorizeResultDto
import io.github.key_del_jeeinho.golabab_v2.account_server.domain.authorize.dto.UnauthorizedAccountDto
import io.github.key_del_jeeinho.golabab_v2.account_server.global.util.JwtUtil
import io.github.key_del_jeeinho.golabab_v2.account_server.global.util.jwt.builder.JwtBuilder
import org.springframework.boot.autoconfigure.web.ServerProperties
import spock.lang.Specification

import java.time.Duration


class AuthorizeServiceTest extends Specification {
    AuthorizeServiceImpl authorizeService

    /*
    Authorize 테스트
    AuthorizeService 의 authorize 메서드는 getAuthorizeLink 에서 발급한 인증링크 내의 authorizeToken 을 파라미터로 받아,
    토큰을 해석하여, 다음과 같은 과정을 수행한다
    - 토큰 내의 callbackUrl 을 구한다
    - 토큰 내의 account.email 과 account.discordId 를 구한다.
    - 이전 작업에서 구한 email 과 discordId 를 통해 JWT 토큰을 새로 생성한다
    - 이전 작업에서 구한 callbackUrl 과 새로 생성한 JWT 토큰을 AuthorizeResultDto 에 담아서 반환한다

    기타사항
    - Mock 적용이 마땅치 않아, 인자로 넣을 토큰을 생성하고, 반환값과 내부 정보를 비교하는 방식으로 지냏ㅇ
     */
    def "AuthorizeService 의 authorize 메서드에 대한 Positive Test"() {
        given:
        InetAddress 서버주소_Inet = Mock InetAddress
        서버주소_Inet.toString() >> 서버주소

        ServerProperties 서버설정 = Mock ServerProperties
        서버설정.getAddress() >> 서버주소_Inet
        서버설정.getPort() >> 포트

        authorizeService = new AuthorizeServiceImpl(서버설정)

        UnauthorizedAccountDto 인증중인계정 = new UnauthorizedAccountDto(이메일 as String, 디스코드ID as Long)

        String authorizeToken = JwtUtil
                .getBuilder(now -> new Date(Long.MAX_VALUE))
                .addClaim("callbackUrl", 콜백URL)
                .addClaim("account.email", 인증중인계정.expectedEmail())
                .addClaim("account.discordId", 인증중인계정.discordId())
                .addClaim("iss", "issuer") //토큰 발급자 확인이 불가능하므로
                .build()

        String resultToken = JwtUtil
                .getBuilder(
                now -> new Date(now.getTime() + Duration.ofMinutes(1).toMillis()) //현재시각의 1분 후
                ).addClaim("discordId", 디스코드ID)
                .addClaim("email", 이메일)
                .build()

        when:
        AuthorizeResultDto dto = authorizeService.authorize(authorizeToken)

        then:
        dto.callbackUrl() == 콜백URL
        dto.token() == resultToken
        println("콜백 URL : " + dto.callbackUrl())
        println("토큰 : " + dto.token())

        where:
        서버주소|포트|이메일|디스코드ID|콜백URL
        "www.golabab.com"|8080|"a@b.c"|1000|"https://www.google.com"
    }

    /*
    GetAuthorizeLink 테스트
    AuthorizeService 의 getAuthorizeLink 메서드는 인자로 받은 callbackURL, unauthorizedAccount, limitMinute 을 이용해 인증 URL 을 생성한다.
   생성 규칙은 다음과 같다.
   - 인증 URL 은 동일 API 의 /authorize Feature 을 호출하는 URL 이다.
   - 인증 URL 은 쿼리스트링으로 인증정보JWT 토큰을 넘겨준다.
   - 인증정보JWT 토큰에는 callbackURL, unauthorizeAccount 정보가 담긴다.
   - 인증정보JWT 토큰의 expired date 은 링크 발급 시간을 기준으로 limitMinute 만큼 지난 시간으로 설정한다.
     */
    def "AuthorizeService 의 getAuthorizeLink 메서드에 대한 Positive Test"() {
        given:
        JwtBuilder JWT빌더 = Mock JwtBuilder
        InetAddress 서버주소_Inet = Mock InetAddress

        서버주소_Inet.toString() >> 서버주소
        JWT빌더.addClaim _ as String, _ as Object >> JWT빌더
        1 * JWT빌더.build() >> 토큰

        ServerProperties 서버설정 = Mock ServerProperties
        서버설정.getAddress() >> 서버주소_Inet
        서버설정.getPort() >> 포트

        JwtUtil.setBuilderStrategy(expiration -> JWT빌더)
        authorizeService = new AuthorizeServiceImpl(서버설정)
        when:
        String 인증링크 = authorizeService.generateAuthorizeLink(콜백URL, 인증되지않은계정, 인증제한시간_분)
        then:
        인증링크 == 서버주소 + ":" + 포트 + "/api/v1/authorize-api/authorize?token=" + 토큰
        print("인증링크" : 인증링크)
        where:
        서버주소|포트|토큰|콜백URL|인증되지않은계정|인증제한시간_분
        "www.golabab.com"|8080|"this+is+token"|"http://localhost:8080/api/authorize"|new UnauthorizedAccountDto("a@b.c", 1000)|10
    }
}