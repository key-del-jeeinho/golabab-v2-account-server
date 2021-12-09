package io.github.key_del_jeeinho.golabab_v2.account_server.domain.account.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.key_del_jeeinho.golabab_v2.account_server.domain.authorize.dto.UnauthorizedAccountDto;
import io.github.key_del_jeeinho.golabab_v2.account_server.domain.authorize.service.AuthorizeService;
import io.github.key_del_jeeinho.golabab_v2.rosetta.authorize.request.GetAuthorizeLinkRequest;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest
@AutoConfigureRestDocs
@AutoConfigureMockMvc
public class AuthorizeLinkControllerTest {
    @Autowired
    private AuthorizeService authorizeService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("인증링크 요청")
    public void getAuthorizeLink() throws Exception {
        GetAuthorizeLinkRequest request = new GetAuthorizeLinkRequest(
                "www.naver.com",
                "a@b.c",
                1000,
                10
        );
        String expectedLink = authorizeService.generateAuthorizeLink(
                request.callbackUrl(),
                new UnauthorizedAccountDto(request.email(), request.discordId()),
                request.limitMinute()
        );
        expectedLink = expectedLink.split("\\?")[0];

        String body = objectMapper.writeValueAsString(request);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/authorize-api/authorize-link").content(body)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
        .andExpect(jsonPath("$.link").value(Matchers.containsString(expectedLink)))

        .andDo(print())
        .andDo(document("/docs/{method-name}",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                        fieldWithPath("callbackUrl").description("유저가 인증링크에 접속시, 유저 정보를 전달할 콜백 URL"),
                        fieldWithPath("email").description("인증 받을유저의 이메일"),
                        fieldWithPath("discordId").description("인증 받을 유저의 디스코드 아이디"),
                        fieldWithPath("limitMinute").description("인증링크 유효시간(분)")
                ),
                responseFields(
                        fieldWithPath("link").description("발급한 인증링크")
                )
        ));
    }
}
