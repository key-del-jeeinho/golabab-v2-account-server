package io.github.key_del_jeeinho.golabab_v2.account_server.account.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.key_del_jeeinho.golabab_v2.account_server.domain.account.controller.AccountController;
import io.github.key_del_jeeinho.golabab_v2.rosetta.account.AccountDto;
import io.github.key_del_jeeinho.golabab_v2.rosetta.account.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@WebMvcTest(AccountController.class)
@AutoConfigureRestDocs
public class AccountControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("계정 추가")
    public void addAccount() throws Exception {
        AccountDto account = new AccountDto(-1, "a@bc.de", Role.ADMIN, 1000L);
        String body = objectMapper.writeValueAsString(account);

        mockMvc.perform(
                put("/api/v1/account-api/account").content(body)
                        .contentType(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(account.email()))
                .andExpect(jsonPath("$.role").value(account.role().toString()))
                .andExpect(jsonPath("$.discordId").value(account.discordId()))

                .andDo(print())
                .andDo(document("/docs/{method-name}",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ));
    }
}
