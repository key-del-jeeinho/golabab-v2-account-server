package io.github.key_del_jeeinho.golabab_v2.account_server.account.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.key_del_jeeinho.golabab_v2.account_server.ConsoleManager;
import io.github.key_del_jeeinho.golabab_v2.account_server.domain.account.entity.AccountEntity;
import io.github.key_del_jeeinho.golabab_v2.account_server.domain.account.repository.AccountRepository;
import io.github.key_del_jeeinho.golabab_v2.rosetta.account.AccountDto;
import io.github.key_del_jeeinho.golabab_v2.rosetta.account.Role;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
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

    @BeforeAll
    public static void setUpAll() throws InterruptedException {
        ConsoleManager.sendWarningMessage(
                "해당 테스트는 DB를 전체 초기화하는 코드를 포함하고 있습니다! 실제 DB사용환경이라면 당장 stop 버튼을 눌러주세요!",
                10);
    }

    @BeforeEach
    public void setUp() {
        accountRepository.deleteAll();
    }

    @AfterEach
    public void tearDown() {
        accountRepository.deleteAll();
    }

    @Test
    @DisplayName("계정 추가")
    public void addAccount() throws Exception {
        AccountDto account = new AccountDto(-1, "a@bc.de", Role.ADMIN, 1000L);
        String body = objectMapper.writeValueAsString(account);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/v1/account-api/account").content(body)
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

    @Test
    @DisplayName("계정 조회")
    public void getAccount() throws Exception {
        AccountDto account = new AccountDto(-1, "a@bc.de", Role.ADMIN, 1000L);
        long id = accountRepository.save(AccountEntity.of(account)).getId();

        mockMvc.perform(
                        RestDocumentationRequestBuilders.get("/api/v1/account-api/account/{id}", id)
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.email").value(account.email()))
                .andExpect(jsonPath("$.role").value(account.role().toString()))
                .andExpect(jsonPath("$.discordId").value(account.discordId()))

                .andDo(print())
                .andDo(document("/docs/{method-name}",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),

                        pathParameters(
                                parameterWithName("id").description("조회하려는 계정의 ID")
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
