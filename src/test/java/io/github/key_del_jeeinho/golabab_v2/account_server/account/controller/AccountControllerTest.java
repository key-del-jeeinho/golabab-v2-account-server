package io.github.key_del_jeeinho.golabab_v2.account_server.account.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.key_del_jeeinho.golabab_v2.account_server.domain.account.repository.AccountRepository;
import io.github.key_del_jeeinho.golabab_v2.rosetta.account.AccountDto;
import io.github.key_del_jeeinho.golabab_v2.rosetta.account.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest
@AutoConfigureRestDocs
@AutoConfigureMockMvc
public class AccountControllerTest {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        accountRepository.deleteAll();
    }

    @Test
    @DisplayName("계정 추가")
    public void addAccount() throws Exception {
        AccountDto account = new AccountDto(-1, "a@bc.de", Role.ADMIN, 1000L);
        String body = objectMapper.writeValueAsString(account);

        mockMvc.perform(
                put("/api/v1/account-api/account").content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(account.email()))
                .andExpect(jsonPath("$.role").value(account.role().toString()))
                .andExpect(jsonPath("$.discordId").value(account.discordId()))

                .andDo(print())
                .andDo(document("/docs/{method-name}",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("id").description("-").type(JsonFieldType.NUMBER),
                                fieldWithPath("email").description("추가하려는 계정의 이메일").type(JsonFieldType.STRING),
                                fieldWithPath("role").description("추가하려는 계정의 역할").type(JsonFieldType.STRING),
                                fieldWithPath("discordId").description("추가하려는 계정의 디스코드 ID").type(JsonFieldType.NUMBER)
                        ),
                        responseFields(
                                fieldWithPath("id").description("추가한 계정의 ID").type(JsonFieldType.NUMBER),
                                fieldWithPath("email").description("추가한 계정의 이메일").type(JsonFieldType.STRING),
                                fieldWithPath("role").description("추가한 계정의 역할").type(JsonFieldType.STRING),
                                fieldWithPath("discordId").description("추가한 계정의 디스코드 ID").type(JsonFieldType.NUMBER)
                        )
                ));
    }
}
