package io.github.key_del_jeeinho.golabab_v2.account_server.domain.authorize.service

import io.github.key_del_jeeinho.golabab_v2.account_server.domain.authorize.dto.UnauthorizedAccountDto
import io.github.key_del_jeeinho.golabab_v2.account_server.global.util.JwtUtil
import io.github.key_del_jeeinho.golabab_v2.account_server.global.util.jwt.builder.JwtBuilder
import org.springframework.boot.autoconfigure.web.ServerProperties
import spock.lang.Specification


class AuthorizeServiceTest extends Specification {
    AuthorizeServiceImpl authorizeService

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
        JwtBuilder JWT빌더 = Mock(JwtBuilder)
        InetAddress 서버주소_Inet = Mock(InetAddress)

        서버주소_Inet.toString() >> 서버주소
        JWT빌더.addClaim(_ as String, _ as Object) >> JWT빌더
        1 * JWT빌더.build() >> 토큰

        ServerProperties 서버설정 = Mock(ServerProperties)
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